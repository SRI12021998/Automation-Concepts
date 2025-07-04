package AutomationTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
public class SalesOrderCreate 
{
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions action;
	private String year;
	private String month;
	private String date;
	private JavascriptExecutor je;
	Robot robot;
	SalesOrderCreate() throws AWTException
	{
		//browser chrome
		this.driver=new ChromeDriver();
		//wait config
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//action 
		this.action = new Actions(driver);

		//javascriptexecutor
		this.je=(JavascriptExecutor)driver;

		//robot for keyboard actions
		robot = new Robot();

		//date config
		LocalDateTime dt=LocalDateTime.now();
		
		this.year=dt.format(DateTimeFormatter.ofPattern("YYYY"));
		this.month=dt.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH));
		this.date=dt.format(DateTimeFormatter.ofPattern("d"));
	}

	@Test
	public void orderCreation() throws InterruptedException
	{
		//opens portal
		driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
		Point position = new Point(680, 0);
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
		
		//login as dispatch user
		driver.findElement(By.id("UserName")).sendKeys("251307");
		driver.findElement(By.name("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		
		//wait for page to load
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
		//open sales management menu
		driver.findElement(By.xpath("//a[@title='Sales Management']")).click();
		
		//move to sales order create sub menu and open
		WebElement element=driver.findElement(By.cssSelector("a[title='Sales Order Create']"));
		action.moveToElement(element).click().perform();
		
		//wait and switch to i content
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iContent")));
		
		//wait and enter sales person 
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("SalesPerson")));
		waitAndClick(driver.findElement(By.id("SalesPerson")));
		driver.findElement(By.id("SalesPerson")).sendKeys("014214_7777");
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'014214_7777')]")));
		}
		catch (Exception e)
		{
			driver.findElement(By.id("SalesPerson")).clear();
			driver.findElement(By.id("SalesPerson")).sendKeys("014214_7777");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'014214_7777')]")));
			System.out.println("Tried after exception");
		}
		
		//select the salesperson
		action.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'014214_7777')])[1]"))).doubleClick().perform();
		
		//wait and enter retailer
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Retailer']")));
		waitAndClick(driver.findElement(By.xpath("//input[@id='Retailer']")));
		action.keyDown(Keys.ARROW_DOWN).perform();

		//wait for suggestion to loads
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(.,'SUPERMERCADO MERCA Z CHIPICHAPE')])[1]")));
		
		//select the retailer
		action.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'SUPERMERCADO MERCA Z CHIPICHAPE')])[1]"))).doubleClick().perform();
		
		//wait and select the delivery date
		waitAndClick(driver.findElement(By.xpath("//input[@id='DeliveryDate']")));
		driver.findElement(By.xpath("//select[@class='ui-datepicker-year']/option[.='"+year+"']")).click();
		driver.findElement(By.xpath("//select[@class='ui-datepicker-month']/option[.='"+month+"']")).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//a[.='"+date+"']")).click();
		
		//wait and select shipping address
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']/a")));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@id='divShipAddressId']/div/ul/li[2]"))));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div/ul/li[2]")).click();

		//select payment method if cash retailer
		if (driver.findElement(By.xpath("//div[@id='divPaymentMethodId']")).isDisplayed())
		{
			driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/div[@class='mt-select']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")));
			driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")).click();
		}
		
		//add sku
		skuInput("0120",5);
		skuInput("0981",7);
		skuInput("1313",9);
		
		//click on submit order button
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(text(),'Submit Order')]")));
		
		//wait and capture the message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
		String message=driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[@id='card-alert']/div/p")).getText();
		System.out.println(message);
		
		//close the notification
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[2]/button")));
		
		//close the print
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@id='btnPrint']/following-sibling::button")));

		closeBrowser();
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
		
		fluentWait.until(webDriver->{	element.click();
		return true;});
	}
	public void skuInput(String skuCode, int qty) throws InterruptedException
	{
		//enter SKU and select
		driver.findElement(By.xpath("//input[@id='SearchName']")).sendKeys(skuCode);
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul/li/aundefined"),0));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//aundefined[@tabindex='-1']")));
		waitAndClick(driver.findElement(By.xpath("//aundefined[@tabindex='-1']")));

		//enter SKU qty
		driver.findElement(By.xpath("//tbody[@class='grid-data-container']/tr/td[contains(.,'"+skuCode+"')]/parent::tr/td[7]/input")).sendKeys(String.valueOf(qty));
		driver.findElement(By.xpath("//tbody[@class='grid-data-container']/tr/td[contains(.,'"+skuCode+"')]/parent::tr/td[7]/input")).sendKeys(Keys.TAB);
		action.moveToElement(driver.findElement(By.xpath("//div[@id='soHeader']"))).click().perform();
	}
	public void closeBrowser()
	{
		if (driver!=null)
		{
			driver.quit();
		}
	}
}