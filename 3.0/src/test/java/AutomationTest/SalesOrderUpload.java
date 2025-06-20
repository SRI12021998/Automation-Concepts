package AutomationTest;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SalesOrderUpload 
{
    // This class is intended for uploading sales orders
    WebDriver driver;
    Actions action;
    JavascriptExecutor je;
    TakesScreenshot tk;
    WebDriverWait wait;
    Robot robot;

    SalesOrderUpload() throws AWTException 
    {
        // Constructor for SalesOrderUpload
        driver=new ChromeDriver();
        je=(JavascriptExecutor)driver;
        action=new Actions(driver);
        tk=(TakesScreenshot)driver;
        wait= new WebDriverWait(driver, Duration.ofSeconds(30));
        robot=new Robot();
    }

    @Test
    public void uploadSalesOrder() throws InterruptedException 
    {
        // Method to upload sales orders
        driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS/Welcome#");
        driver.manage().window().maximize();

        //login as l1 user
		driver.findElement(By.id("UserName")).sendKeys("L1_user");
		driver.findElement(By.name("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();

        //wait for page to load
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        //open sales management menu
        driver.findElement(By.xpath("//a[@title='Upload Utility']")).click();
        
        //open sales order upload page
        action.moveToElement(driver.findElement(By.xpath("//a[@title='Sales Order Route Upload']"))).click().perform();

        //wait and switch to i content
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@src='/web/UploadUtility/UploadUtils/Index/Sales_Order_Route']")));

        //set the file path to upload
        StringSelection filePath = new StringSelection("D:\\WORKSPACE\\xls\\UU\\SO-CO.xlsx");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);

        //check for existing upload
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try
        {
            WebElement uploadExist = driver.findElement(By.partialLinkText("Click here to upload new file"));
            if (uploadExist.isDisplayed()) 
                {
                    uploadExist.click();
                    Alert alert = driver.switchTo().alert();
                    alert.accept(); // Accept the alert to confirm deletion of old upload
                }
        } 
        catch (Exception e) 
        {
            // No existing upload found, continue with the upload process
            System.err.println("No existing upload found, proceeding with new upload.");
        }


        // Wait for dialog by checking screen pixels
        Color currentScreenColor =robot.getPixelColor(100, 100);
        System.out.println(currentScreenColor);

        //click on upload file button
        action.moveToElement(driver.findElement(By.xpath("//span[.='Upload']"))).click().perform();

        // Wait for the file upload dialog to appear
        int attempts = 0;
        while (attempts < 10) 
        {
        // Capture screen pixel where dialog should appear
        Color pixelColor = robot.getPixelColor(100, 100);
            if (pixelColor.equals(currentScreenColor)) 
            {
                Thread.sleep(500); // Wait for a half second before checking again
                attempts++;
            }
            else 
            {
                System.out.println(pixelColor);
                break; // Dialog appeared, exit loop
            }
        }

        //robot actions to paste the file path
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V); // Paste the file path
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER); // Press Enter to confirm the file selection
        robot.keyRelease(KeyEvent.VK_ENTER);

        //verify file is selected
        System.out.println(driver.findElement(By.xpath("//input[@id='uploadFile']")).getAttribute("value"));
        wait.until(driver-> String.valueOf(driver.findElement(By.xpath("//input[@id='uploadFile']")).getAttribute("value")).contains("xlsx"));
        System.out.println(driver.findElement(By.xpath("//input[@id='uploadFile']")).getAttribute("value"));

        //click on continue button
        action.moveToElement(driver.findElement(By.xpath("//input[@name='btnSubmit']"))).click().perform();

        //wait for validation to complete and click on status
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Status']")));
        driver.findElement(By.xpath("//a[.='Status']")).click();

        //wait for the status page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(.,'Error Records')]/span")));

        //check if there are any error records
        if(driver.findElement(By.xpath("//label[contains(.,'Error Records')]/span")).getText().contains("[0]"))
        {
            //if no error records, click on upload button
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@id='lbl-upload']")));
            action.moveToElement(driver.findElement(By.xpath("//td[@id='td-upload']"))).click().perform();

            // Accept the alert to confirm upload
            Alert alert = driver.switchTo().alert();
            alert.accept(); 

            //wait for upload to complete and capture the process message
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("divShowProcess"), "Data uploaded successfully."));

            //take a screenshot of the process message
            File src=tk.getScreenshotAs(OutputType.FILE);
            try 
            {
                FileHandler.copy(src,new File("D:/VS Local repo/evidence/"+System.currentTimeMillis()+".png"));
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }

            System.out.println(driver.findElement(By.id("divShowProcess")).getText());

        }
        else
        {
            //if error records exist show the error message
            List <WebElement> error=driver.findElements(By.xpath("//div[@id='errorTbl']/div"));
            Iterator<WebElement> itr=error.iterator();
            while (itr.hasNext()) 
            {
                WebElement errorRecord = itr.next().findElement(By.xpath(".//label"));
                StringBuffer errorText= new StringBuffer();
                wait.until(driver-> 
                {
                    String errorTextInner = errorRecord.getText(); 
                    if (!errorTextInner.isEmpty() && errorTextInner != null)
                    {
                        errorText.append(errorTextInner);
                        return true; // Return true if error text is present
                    } 
                    else 
                    {
                        return false; // Return false if error text is not present  
                    }
                });
                System.out.println("Error Record: " + errorText);
            }
        }

        //sign out from the application
        driver.switchTo().defaultContent(); // Switch back to the default content
        action.moveToElement(driver.findElement(By.id("prof-anchor"))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Logout']")));
        action.moveToElement(driver.findElement(By.xpath("//a[@title='Logout']"))).click().perform();

        //wait for logout to complete
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='/web/Content/ClientLogo/IvyDistributor-Logo.png']")));

        //close the browser
        driver.quit();
    }
}
