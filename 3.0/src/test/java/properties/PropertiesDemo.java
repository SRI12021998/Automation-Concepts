package properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class PropertiesDemo 
{
    WebDriver driver;
    @Test
    void loginDispatchUser()
    {
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--start-maximized");
        driver=new ChromeDriver(options);

        
        driver.get(ConfigReader.getProperty("url"));

        driver.findElement(By.id("UserName")).sendKeys(ConfigReader.getProperty("userName"));

        driver.findElement(By.id("Password")).sendKeys(ConfigReader.getProperty("password"));

        driver.findElement(By.id("Login")).click();
        driver.quit();
    }
}
