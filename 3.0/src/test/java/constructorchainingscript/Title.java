package constructorchainingscript;

import org.openqa.selenium.WebDriver;

public class Title 
{
    WebDriver driver;
    Title(WebDriver driver)
    {
        this.driver=driver;
    }
    public String getTitle()
    {
       return driver.getTitle();
    }
}
