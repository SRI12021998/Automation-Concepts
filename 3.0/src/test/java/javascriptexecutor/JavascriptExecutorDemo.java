package javascriptexecutor;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
public class JavascriptExecutorDemo 
{

	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver=new ChromeDriver();
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2Fjewelry");
		driver.manage().window().maximize();
		Actions action=new Actions(driver);
		//space should not be there if locator is class not applicable for xpath
		action.moveToElement(driver.findElement(By.className("register-button"))).click().perform();
		LinkedHashMap <By,String> field=new LinkedHashMap<>();
		field.put(By.id("gender-male"), null);
		field.put(By.name("FirstName"), "Sriram");
		field.put(By.name("LastName"), "Balaji");
		field.put(By.id("Email"), "s781@gmail.com");
		field.put(By.id("Company"), "IVY MOBILITY");
		field.put(By.id("Newsletter"), null);
		field.put(By.id("Password"), "drunkenmonkey");
		field.put(By.id("ConfirmPassword"), "drunkenmonkey");
		field.put(By.xpath("//button[@id='register-button']"), null);
		JavascriptExecutor je=(JavascriptExecutor)driver;
		for(Entry<By, String> job:field.entrySet())
		{
			WebElement element=driver.findElement(job.getKey());
			je.executeScript("arguments[0].scrollIntoView(true)", element);
			if(job.getValue()==null)
				element.click();
			else
				element.sendKeys(job.getValue());
		}
		Thread.sleep(3000);
		try 
		{
			System.out.println(driver.findElement(By.xpath("//div[@class='result']")).getText());
		}
		catch (Exception e) 
		{
			System.out.println(driver.findElement(By.xpath("//li[text()='The specified email already exists']")).getText());
		}
		driver.manage().window().minimize();
		driver.quit();
	}

}
