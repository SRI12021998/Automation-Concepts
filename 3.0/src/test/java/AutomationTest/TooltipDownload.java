package AutomationTest;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class TooltipDownload 
{
    public static void main(String[] args) throws InterruptedException 
    {
        RemoteWebDriver driver = new ChromeDriver();
        driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS/Welcome#");
        driver.manage().window().maximize();

        // Initialize JavascriptExecutor, Actions, and FluentWait
        JavascriptExecutor je= (JavascriptExecutor) driver;
        Actions action = new Actions(driver);
        FluentWait<RemoteWebDriver> wait = new FluentWait<>(driver)
        .withTimeout(Duration.ofSeconds(15))
        .pollingEvery(Duration.ofSeconds(1))
        .ignoring(Exception.class);


        //login as l1 user
		driver.findElement(By.id("UserName")).sendKeys("L1_user");
		driver.findElement(By.name("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		
		//wait for page to load
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
		//open sales management menu
        action.moveToElement(driver.findElement(By.xpath("//a[@title='Dump Extract']"))).click().perform();

        //wait and switch to i content
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.cssSelector("iframe[src='/web/DumpExtract/Index']"))));


		//select dump type
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='DumpType']/parent::div/input")));
        driver.findElement(By.xpath("//select[@id='DumpType']/parent::div/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li/span[.='DUMP_Retailer_Data_Colombia']")));
        driver.findElement(By.xpath("//li/span[.='DUMP_Retailer_Data_Colombia']")).click();

        //select sales center
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='BranchId']/parent::div/input")));
        driver.findElement(By.xpath("//select[@id='BranchId']/parent::div/input")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li/span[.='cali preventa']")));
        driver.findElement(By.xpath("//li/span[.='cali preventa']")).click();

        //click generate button
        action.moveToElement(driver.findElement(By.xpath("//label[.='Generate']"))).click().perform();

        // Switch back to the main content for capturing the message
        driver.switchTo().defaultContent(); 
        wait.until(webDriver->je.executeScript("return document.readyState").equals("complete"));
        
        //wait and capture the message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
		String message=driver.findElement(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")).getText();
		System.out.println(message);
        
        //close the notification
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@id='card-alert']/button")));
        driver.switchTo().frame(0);

        //table element
        WebElement dumpGridNewTable=driver.findElement(By.xpath("//div[@id='DumpLogGridNew']/descendant::table[2]"));
        WebElement dumpGridNewTableBody=dumpGridNewTable.findElement(By.xpath("./tbody"));
        WebElement tdElement=dumpGridNewTableBody.findElement(By.xpath("./tr[2]/td[3]"));

        
        // Wait for the tooltip to be present
        action.moveToElement(tdElement).perform();
        // String toolTip = (String) ((JavascriptExecutor) driver).executeScript(
        // "return arguments[0].getAttribute('title');", tdElement);
        
        String toolTip= tdElement.getAttribute("title");
        System.out.println("Tooltip text: " + toolTip);
        
        WebElement downloadButton =dumpGridNewTableBody.findElement(By.xpath("./tr[2]/td[4]"));
        action.moveToElement(downloadButton).click().perform(); 

        // Wait and verify the download
        File path=new File("C:\\Users\\sriram.b\\Downloads\\"+toolTip);
        wait.until(webDriver -> path.exists() && path.isFile() && path.length() > 0);
        System.out.println("Download completed successfully: " + path.getAbsolutePath());

        driver.quit();
    }
}
