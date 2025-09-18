package shadow;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class OpenShadow 
{
	WebDriver driver;
	WebDriverWait wait;
	Actions action;
	JavascriptExecutor je;
	
	@BeforeSuite
	public void setup ()
	{
		ChromeOptions option=new ChromeOptions();
		option.addArguments("--start-maximized");
		driver=new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		action=new Actions(driver);
		je=(JavascriptExecutor)driver;
		driver.get("https://letcode.in/test");
	}
	
	@Test(priority=1)
	public void openRootDemo() throws InterruptedException
	{
		action.moveToElement(driver.findElement(By.cssSelector("a[href='/shadow']"))).click().perform();
		
		WebElement shadowHost = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#open-shadow")));

		WebElement element = (WebElement) je.executeScript(
		    "return arguments[0].shadowRoot.querySelector('#fname')", shadowHost
		);
		element.sendKeys("Sriram");
		Thread.sleep(2000);
		element.clear();
	}
	@Test(priority=2)
	public void closedRootDemo() throws InterruptedException
	{
		je.executeScript("document.querySelector(arguments[0]).myRoot.querySelector(arguments[1]).value=arguments[2]", 
				"my-web-component", "#lname", "Balaji");
		Thread.sleep(2000);
		je.executeScript("document.querySelector(arguments[0]).myRoot.querySelector(arguments[1]).value=arguments[2]", 
				"my-web-component", "#lname", "");
		Thread.sleep(2000);
	}
	
	@AfterSuite
	public void tearDown()
	{
		if (driver!=null)
		{
			driver.quit();
		}
	}
}
