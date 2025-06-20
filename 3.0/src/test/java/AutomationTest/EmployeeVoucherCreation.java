package AutomationTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.Dimension;

public class EmployeeVoucherCreation 
{
    JavascriptExecutor je;
    Actions action;
    Robot robot;
    ThreadLocal<WebDriver>drive= new ThreadLocal<>();

    public WebDriver getDriver() 
    {
        return drive.get();
    }

    @BeforeMethod
    public void setup() throws AWTException {
        drive.set(new ChromeDriver());
        getDriver().get("https://bimbo03-mx-qa.ivycpg.com/web/DMS/");
        je = (JavascriptExecutor)getDriver();
        action = new Actions(getDriver());
        robot = new Robot();
        // other initializations
    }

    @AfterMethod
    public void teardown() {
        if(getDriver() != null) {
            getDriver().quit();
            drive.remove(); 
        }
    }
//parallel = true
    @DataProvider()
    public String[][] getData() 
    {
        String[][] data = new String[2][2];
        data[0][0] = "BIEP_ADMIN";
        data[0][1] = "1";
        data[1][0] = "Anu_Admin";
        data[1][1] = "1";
        return data;
    }
//invocationCount = 2, threadPoolSize = 2,
    @Test( dataProvider = "getData")
    public void EVC(String login, String password)
    {
        // getDriver()=new ChromeDriver();
        // je=(JavascriptExecutor)getDriver();
        // getDriver().get("https://bimbo03-mx-qa.ivycpg.com/web/DMS/");
        getDriver().manage().window().maximize();
        // Point position = new Point(0, 0);
        // getDriver().manage().window().setPosition(position);
        // getDriver().manage().window().setSize(new Dimension(680, 800));
        
        //zoom out the screen
        // getDriver().switchTo().activeElement();
        // for (int a=0; a<5;a++)
        // {
        //     robot.keyPress(KeyEvent.VK_CONTROL);
        //     robot.keyPress(KeyEvent.VK_MINUS);
        //     robot.keyRelease(KeyEvent.VK_MINUS);
        //     robot.keyRelease(KeyEvent.VK_CONTROL);
        // }
        
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));


        //login as dispatch user
		getDriver().findElement(By.id("UserName")).sendKeys(login);
		getDriver().findElement(By.name("Password")).sendKeys(password);
		getDriver().findElement(By.id("Login")).click();//click
		
		//wait for page to load
		getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        //open employee voucher management menu
		getDriver().findElement(By.xpath("//a[@title='Employee Voucher Management']")).click();
		
		
        int noOfVouchers=1;
        int amount=230;
        int voucherConcept=2;
        int user=1;
        for(int i=0;i<noOfVouchers;i++)
        {
            //wait for screen to load and switch to frame
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[.='Employee Voucher']")));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(getDriver().findElement(By.xpath("//iframe[contains(@src,'/web/EmployeeVoucher')]"))));
           
            //wait and click on add button
            wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[.='Add']"))));
            getDriver().findElement(By.xpath("//a[.='Add']")).click();

            //wait for page to load
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            //select the user
            wait.until(ExpectedConditions.elementToBeClickable(By.id("UserName")));
            waitAndClick(getDriver().findElement(By.id("UserName")));
            getDriver().findElement(By.id("UserName")).sendKeys(Keys.ARROW_DOWN);
            try
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//ul/li/a[contains(.,'BIEP')])["+user+"]")));
            }
            catch(Exception e)
            {
                getDriver().findElement(By.id("UserName")).sendKeys(Keys.ARROW_DOWN);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//ul/li/a[contains(.,'BIEP')])["+user+"]")));
                System.out.println("user selection tried with exception");
            }
            getDriver().findElement(By.xpath("(//ul/li/a[contains(.,'BIEP')])["+user+"]")).click();

            //select voucher type
            getDriver().findElement(By.xpath("//li/span[.='Cash Voucher']/ancestor::ul/preceding-sibling::input")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li/span[.='Cash Voucher']")));
            waitAndClick(getDriver().findElement(By.xpath("//li/span[.='Cash Voucher']")));
            
            
            //enter voucher amount
            getDriver().findElement(By.cssSelector("input#AEV_Amount")).sendKeys(String.valueOf(amount));

            //select voucher concept
            getDriver().findElement(By.xpath("//label[@for='AEV_Voucher_Concept_Id']/parent::div/descendant::input")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='AEV_Voucher_Concept_Id']/parent::div/descendant::input/following-sibling::ul/li["+voucherConcept+"]")));
            waitAndClick(getDriver().findElement(By.xpath("//label[@for='AEV_Voucher_Concept_Id']/parent::div/descendant::input/following-sibling::ul/li["+voucherConcept+"]")));

            //click on save
            getDriver().findElement(By.id("btnSave")).click(); 
            amount=amount+10;
            voucherConcept++;
            user++;

            if(voucherConcept>6)
            {
                voucherConcept=2;
            }

            if(user>13)
            {
                user=1;
            }


            //close the notification
            getDriver().switchTo().defaultContent();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
		    // je.executeScript("arguments[0].click();", getDriver().findElement(By.xpath("//div[@id='card-alert']/button")));
        }
    }
    public void waitAndClick(WebElement element)
	{
		Wait <WebDriver> fluentWait=new FluentWait<WebDriver>(getDriver())
		.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofMillis(300))
		.ignoring(org.openqa.selenium.ElementClickInterceptedException.class)
		.ignoring(org.openqa.selenium.StaleElementReferenceException.class)
		.ignoring(org.openqa.selenium.ElementNotInteractableException.class)
		.ignoring(TimeoutException.class);
		fluentWait.until(webDriver->
		{element.click();
		return true;});
	}

}
