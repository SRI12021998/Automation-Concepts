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
import org.testng.annotations.Test;

public class EmployeeVoucherCreation 
{
    WebDriver driver;
    JavascriptExecutor je;
    Actions action;
    Robot robot;
    @Test
    public void EVC() throws AWTException 
    {
        driver=new ChromeDriver();
        je=(JavascriptExecutor)driver;
        robot = new Robot();
        driver.get("https://bimbo03-mx-qa.ivycpg.com/web/DMS/");
        Point position = new Point(0, 0);
        driver.manage().window().setPosition(position);
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(680, 800));
        
        //zoom out the screen
        driver.switchTo().activeElement();
        for (int a=0; a<5;a++)
        {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_MINUS);
            robot.keyRelease(KeyEvent.VK_CONTROL);
        }
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));


        //login as dispatch user
		driver.findElement(By.id("UserName")).sendKeys("BIEP_ADMIN");
		driver.findElement(By.name("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();//click
		
		//wait for page to load
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        //open employee voucher management menu
		driver.findElement(By.xpath("//a[@title='Employee Voucher Management']")).click();
		
		
        int noOfVouchers=3;
        int amount=230;
        int voucherConcept=2;
        int user=1;
        for(int i=0;i<noOfVouchers;i++)
        {
            //wait for screen to load and switch to frame
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[.='Employee Voucher']")));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.xpath("//iframe[contains(@src,'/web/EmployeeVoucher')]"))));
           
            //wait and click on add button
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[.='Add']"))));
            driver.findElement(By.xpath("//a[.='Add']")).click();

            //wait for page to load
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            //select the user
            wait.until(ExpectedConditions.elementToBeClickable(By.id("UserName")));
            waitAndClick(driver.findElement(By.id("UserName")));
            driver.findElement(By.id("UserName")).sendKeys(Keys.ARROW_DOWN);
            try
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//ul/li/a[contains(.,'BIEP')])["+user+"]")));
            }
            catch(Exception e)
            {
                driver.findElement(By.id("UserName")).sendKeys(Keys.ARROW_DOWN);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//ul/li/a[contains(.,'BIEP')])["+user+"]")));
                System.out.println("user selection tried with exception");
            }
            driver.findElement(By.xpath("(//ul/li/a[contains(.,'BIEP')])["+user+"]")).click();

            //select voucher type
            driver.findElement(By.xpath("//li/span[.='Cash Voucher']/ancestor::ul/preceding-sibling::input")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li/span[.='Cash Voucher']")));
            waitAndClick(driver.findElement(By.xpath("//li/span[.='Cash Voucher']")));
            
            
            //enter voucher amount
            driver.findElement(By.cssSelector("input#AEV_Amount")).sendKeys(String.valueOf(amount));

            //select voucher concept
            driver.findElement(By.xpath("//label[@for='AEV_Voucher_Concept_Id']/parent::div/descendant::input")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='AEV_Voucher_Concept_Id']/parent::div/descendant::input/following-sibling::ul/li["+voucherConcept+"]")));
            waitAndClick(driver.findElement(By.xpath("//label[@for='AEV_Voucher_Concept_Id']/parent::div/descendant::input/following-sibling::ul/li["+voucherConcept+"]")));

            //click on save
            driver.findElement(By.id("btnSave")).click(); 
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
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
		    je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@id='card-alert']/button")));
        }
        driver.close();
    }
    public void waitAndClick(WebElement element)
	{
		Wait <WebDriver> fluentWait=new FluentWait<WebDriver>(driver)
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
