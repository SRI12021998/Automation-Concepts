package AutomationTest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
public class SalesOrderCreate 
{

	public static void main(String[] args) throws InterruptedException
	{
		WebDriver driver=new ChromeDriver();
		driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
		driver.manage().window().maximize();
		
		//date config
		LocalDateTime dt=LocalDateTime.now();
		DateTimeFormatter yearFormat=DateTimeFormatter.ofPattern("YYYY");
		DateTimeFormatter monthFormat=DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
		DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("d");
		
		String year=dt.format(yearFormat);
		String month=dt.format(monthFormat);
		String date=dt.format(dateFormat);
		
		//login as dispatch user
		driver.findElement(By.id("UserName")).sendKeys("251307");
		driver.findElement(By.name("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();//click
		
		//wait for page to load
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
		//open sales management menu
		driver.findElement(By.xpath("//a[@title='Sales Management']")).click();//click
		
		//move to sales order create sub menu and open
		WebElement element=driver.findElement(By.cssSelector("a[title='Sales Order Create']"));
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
		
		//wait and switch to i content
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iContent")));
		
		//wait and enter sales person 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SalesPerson")));
		driver.findElement(By.id("SalesPerson")).click();//click
		driver.findElement(By.id("SalesPerson")).sendKeys("014214_3000");//input
		
		
		//wait for suggestion to load
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@id='ui-id-1']/li[@class='ui-menu-item']"), 0));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul[@id='ui-id-1']/li/a)[1]")));
		
		//go down and enter
		driver.findElement(By.id("SalesPerson")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.id("SalesPerson")).sendKeys(Keys.ENTER);
		
		//wait and enter retailer
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='Retailer']")));
		driver.findElement(By.xpath("//input[@id='Retailer']")).click();//click
		driver.findElement(By.xpath("//input[@id='Retailer']")).sendKeys("DEMORET1");//input
		
		//wait for suggestion to load
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@id='ui-id-5']/li[@class='ui-menu-item']"), 0));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ul[@id='ui-id-5']/li/a)[1]")));
		
		//go down and enter
		driver.findElement(By.xpath("//input[@id='Retailer']")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("//input[@id='Retailer']")).sendKeys(Keys.ENTER);
		
		
		//wait and select the delivery date
		
		driver.findElement(By.xpath("//input[@id='DeliveryDate']")).click();
		driver.findElement(By.xpath("//select[@class='ui-datepicker-year']/option[.='"+year+"']")).click();
		driver.findElement(By.xpath("//select[@class='ui-datepicker-month']/option[.='"+month+"']")).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//a[.='"+date+"']")).click();
		
		//wait and select shipping address
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']")));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']")).click();
		driver.findElement(By.xpath("//div[@class='mt-select']/ul/li[2]")).click();
		
		//select payment method
		driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/div[@class='mt-select']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")));
		driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")).click();
		
		//enter SKU and select
		driver.findElement(By.xpath("//input[@id='SearchName']")).sendKeys("2ss");//input
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//aundefined[@tabindex='-1']")));
		driver.findElement(By.xpath("//aundefined[@tabindex='-1']")).click();//click
		
		//enter SKU qty
		driver.findElement(By.xpath("//tbody[@class='grid-data-container']/tr/td[7]/input")).sendKeys("7");
		
		//enter SKU and select
		driver.findElement(By.xpath("//input[@id='SearchName']")).sendKeys("7ss");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//aundefined[@tabindex='-1']")));
		driver.findElement(By.xpath("//aundefined[@tabindex='-1']")).click();
		
		//enter SKU qty
		driver.findElement(By.xpath("//tbody[@class='grid-data-container']/tr[2]/td[7]/input")).sendKeys("8");
		
		//click on submit order button
		JavascriptExecutor je=(JavascriptExecutor)driver;
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(text(),'Submit Order')]")));
		
		//wait and capture the message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
		String message=driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[@id='card-alert']/div/p")).getText();
		System.out.println(message);
		
		//close the notification
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[2]/button")));
		
		//close the print
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@id='btnPrint']/following-sibling::button")));
		driver.quit();
	}
}