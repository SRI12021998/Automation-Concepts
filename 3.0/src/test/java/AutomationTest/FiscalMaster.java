package AutomationTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FiscalMaster 
{
	static WebDriver driver=null;
	static FluentWait<WebDriver> wait=null;
	static Actions action=null;
	static JavascriptExecutor je=null;
	
	@BeforeSuite
	public static void setUp()
	{
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--start-maximized");
		driver=new ChromeDriver(options);
		driver.get("https://bimbo-cr-qa.ivycpg.com/web/DMS");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait=new FluentWait<WebDriver>(driver)
        		.pollingEvery(Duration.ofMillis(300))
        		.withTimeout(Duration.ofSeconds(15))
        		.ignoring(NoSuchElementException.class);
		action =new Actions(driver);
		je= (JavascriptExecutor) driver;
	}
	
	@BeforeTest
	public static void openFiscalMasterScreen()
	{
		driver.findElement(By.id("UserName")).sendKeys("L1_user");
		driver.findElement(By.id("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		

		action.moveToElement(driver.findElement(By.cssSelector("a[title='Masters']"))).click().build().perform();

		action.moveToElement(driver.findElement(By.cssSelector("a[title='Fiscal Reason Master']"))).click().build().perform();

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.cssSelector("iframe[src*='FiscalReasonMaster']"))));

		wait.until(webDriver -> je.executeScript("return document.readyState").equals("complete"));
	}
	
	@DataProvider(name="dataSet")
	public static String [][] fiscalMasterData()
	{
		return FiscalData.dataSet();		
	}
	
	@Test(dataProvider="dataSet")
	public static void reasonMasterAdd (String master, String dataType, String reasonCode, String description)
	{
		

		driver.findElement(By.xpath("//a[.='Add']")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='Master']/preceding-sibling::input")));
		
		
		driver.findElement(By.xpath("//select[@id='Master']/preceding-sibling::input")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='Master']/preceding-sibling::ul/li[.='"+master+"']")));
		
		driver.findElement(By.xpath("//select[@id='Master']/preceding-sibling::ul/li[.='"+master+"']")).click();
		
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='DataType']/preceding-sibling::input")));
			
			driver.findElement(By.xpath("//select[@id='DataType']/preceding-sibling::input")).click();
			}
		
		catch(StaleElementReferenceException e)
			{
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='DataType']/preceding-sibling::input")));
				
				driver.findElement(By.xpath("//select[@id='DataType']/preceding-sibling::input")).click();
				System.out.println("Tried after exception");
			}
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='DataType']/preceding-sibling::ul/li[.='"+dataType+"']")));
		
		driver.findElement(By.xpath("//select[@id='DataType']/preceding-sibling::ul/li[.='"+dataType+"']")).click();
		
		
		
		action.moveToElement(driver.findElement(By.id("ReasonCode"))).perform();
		
		driver.findElement(By.id("ReasonCode")).sendKeys(reasonCode);
		
		action.moveToElement(driver.findElement(By.id("Description"))).perform();
		
		driver.findElement(By.id("Description")).sendKeys(description);
		
		driver.findElement(By.id("btnReasonSave")).click();
		
		//close the notification
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
	     je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@id='card-alert']/button")));
	    
	     wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.cssSelector("iframe[src*='FiscalReasonMaster']"))));
	}
	
	@AfterSuite
	public static void tearDown()
	{
		if( driver!=null)
		{
			driver.quit();
		}
	}
}
