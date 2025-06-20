package constructorchainingscript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login extends Title
{
    WebDriver driver;//loose coupling
    Login(WebDriver driver)
    {
        super(driver);//calling super class constructor
        this.driver=driver;
    }
    public void loginPage(String user, String password)
    {
        driver.findElement(By.id("UserName")).sendKeys(user);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("Login")).click();
    }
}
