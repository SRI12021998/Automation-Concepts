package pom.test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pom.pages.LoginPage;

public class LoginTest 
{
	RemoteWebDriver driver;
    @Parameters({"url","login","password"})
    @Test()
    public void loginCheck(String url, String login, String password)
    {
    	ChromeOptions option=new ChromeOptions();
    	option.addArguments("--start-maximized");
        driver=new ChromeDriver(option);
        LoginPage loginPage=new LoginPage(driver);

        driver.get(url);
        loginPage.userLogin.sendKeys(login);
        loginPage.password.sendKeys(password);
        loginPage.loginButton.click();
    }
    
    @AfterSuite
    public void tearDown()
    {
    	driver.quit();
    }
}
