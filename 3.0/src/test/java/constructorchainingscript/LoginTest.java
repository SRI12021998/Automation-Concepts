package constructorchainingscript;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class LoginTest 
{
    WebDriver driver;
    LoginTest()
    {
        this.driver=new ChromeDriver();
    }
    @Test
    public void loginTest()
    {
        driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
        Login login=new Login(driver);
        login.loginPage("251307", "1");
        System.out.println(login.getTitle());
    }
}
