package testNG.dispatchregression;

import org.openqa.selenium.ElementClickInterceptedException;
import org.testng.annotations.Test;

public class Login 
{
    @Test(priority = 2)
    public void dispatchLogin()
    {
        System.out.println("Dispatch user logged in");
    }

    @Test(priority = 1, groups = "A")
    public void homeScreenLoad()
    {
        throw new ElementClickInterceptedException("home screen loading failed");
        // System.out.println("Home page loaded");
    }

}
