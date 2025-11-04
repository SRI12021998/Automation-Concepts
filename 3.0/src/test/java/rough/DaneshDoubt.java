package rough;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class DaneshDoubt 
{

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions action;
	
	DaneshDoubt()
	{
		this.driver=new ChromeDriver();
		//wait config
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//action 
		this.action = new Actions(driver);
	}
	
	@Test
	public void openMaster()
	{
		driver.get("https://bimbo-sv-qa.ivycpg.com/web/DMS");
		driver.findElement(By.id("UserName")).sendKeys("L1_user");
		driver.findElement(By.name("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		
		//wait for page to load
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
		//open sales management menu
		WebElement element=driver.findElement(By.cssSelector("a[title='Masters']"));
		
		//move to sales order create sub menu and open
		action.moveToElement(element).click().perform();
	}
}
