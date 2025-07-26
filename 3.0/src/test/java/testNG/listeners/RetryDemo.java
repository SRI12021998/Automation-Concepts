package testNG.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ScreenCaptureOnFailure.class)
public class RetryDemo 
{
    ThreadLocal <RemoteWebDriver> driver=new ThreadLocal<>();
    @BeforeMethod
    public void setup()
    {
        driver.set(new ChromeDriver());
        
    }
    public RemoteWebDriver getDriver()
    {
        return driver.get();
    }

    @Test(retryAnalyzer=MyRetryAnalyzer.class)
	public void ecuadorDispatchLogin()
	{
		getDriver().get("https://bimbo-ec-01-qa.ivycpg.com/web/DMS");
		getDriver().findElement(By.id("UserName")).sendKeys("8847");
		getDriver().findElement(By.id("Password")).sendKeys("1");
		getDriver().findElement(By.id("Logins")).click();
	}

    @AfterMethod
    public void teardown()
    {
        getDriver().quit();
        driver.remove();
    }
}
