package threadlocal;



import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
public class WithoutThreadLocal
{

	RemoteWebDriver driver=new ChromeDriver();


	@Test
	public void ecuadorDispatchLogin()
	{
		driver.get("https://bimbo-ec-01-qa.ivycpg.com/web/DMS");
		driver.findElement(By.id("UserName")).sendKeys("8847");
		driver.findElement(By.id("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		driver.quit();
	}

	@Test
	public void colombiaDispatchLogin()
	{
		driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
		driver.findElement(By.id("UserName")).sendKeys("251307");
		driver.findElement(By.id("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		driver.quit();
	}

	@Test
	public void costaricaDispatchLogin()
	{
		driver.get("https://bimbo-cr-qa.ivycpg.com/web/DMS");
		driver.findElement(By.id("UserName")).sendKeys("BHAVANI_DISP_01");
		driver.findElement(By.id("Password")).sendKeys("1");
		driver.findElement(By.id("Login")).click();
		driver.quit();
	}
}


