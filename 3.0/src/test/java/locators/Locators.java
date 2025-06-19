package locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;


public class Locators 
{

	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver=new ChromeDriver();
		driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
		driver.manage().window().maximize();
		
		//locator1 id - enter username
		driver.findElement(By.id("UserName")).sendKeys("251307");
		
		//locator2 name - enter password
		driver.findElement(By.name("Password")).sendKeys("1");
		
		//click login button
		driver.findElement(By.id("Login")).click();
		Thread.sleep(2000);
		
		//navigate to another URL
		driver.navigate().to("file:///D:/WORKSPACE/test_links.html");
		Thread.sleep(2000);
		
		//locator3 link text
		driver.findElement(By.linkText("CO QA")).click();
		Thread.sleep(2000);
		
		//go back
		driver.navigate().back();
		Thread.sleep(2000);
		
		//locator4 partial link text
		driver.findElement(By.partialLinkText("EC")).click();
		Thread.sleep(2000);
		

		/*
		 * cssSelector
		 * tag#id
		 * tag.classname
		 * tag[attribute name='attribute value']
		 * tag[attribute name^='attribute value']
		 * tag[attribute name$='attribute value']
		 * tag[attribute name*='attribute value']
		 */
		
		/*
		 * 
		 */
		
//		driver.quit();
	}

}
