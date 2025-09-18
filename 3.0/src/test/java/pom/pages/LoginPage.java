package pom.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
    private RemoteWebDriver driver;

    public LoginPage(RemoteWebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(this.driver, LoginPage.class);
    }

    @FindBy (id="UserName")
    public WebElement userLogin;

    @FindBy (name = "Password")
    public WebElement password;

    @FindBy (xpath = "//button[@id='Login']")
    public WebElement loginButton;

    @FindBy (xpath = "//a[.='Recuperar Contraseña']")
    public WebElement recoverPassword;

    @FindBy (xpath = "//div[contains(.,'Invalid User Name Or Password.')]")
    public WebElement errorIndicator;


}
