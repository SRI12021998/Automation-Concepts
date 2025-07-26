package threadlocal;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParallelActions
{

	ThreadLocal<RemoteWebDriver> driver=new ThreadLocal<>();

	@BeforeMethod
	public void setup()
	{
		driver.set(new ChromeDriver());
	}

	public RemoteWebDriver getDriver()
	{
		return driver.get();
	}

	@Test
	public void ecuadorDispatchLogin()
	{
		getDriver().get("https://bimbo-ec-01-qa.ivycpg.com/web/DMS");
		getDriver().findElement(By.id("UserName")).sendKeys("8847");
		getDriver().findElement(By.id("Password")).sendKeys("1");
		getDriver().findElement(By.id("Login")).click();
	}

	@Test
	public void colombiaDispatchLogin()
	{
		getDriver().get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
		getDriver().findElement(By.id("UserName")).sendKeys("251307");
		getDriver().findElement(By.id("Password")).sendKeys("1");
		getDriver().findElement(By.id("Login")).click();
	}

	@Test
	public void costaricaDispatchLogin()
	{
		getDriver().get("https://bimbo-cr-qa.ivycpg.com/web/DMS");
		getDriver().findElement(By.id("UserName")).sendKeys("BHAVANI_DISP_01");
		getDriver().findElement(By.id("Password")).sendKeys("1");
		getDriver().findElement(By.id("Login")).click();
	}

    @AfterMethod
    public void teardown()
    {
        getDriver().quit();
        driver.remove();
    }
}
