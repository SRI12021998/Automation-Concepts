package testNG.dispatchregression;

import org.testng.annotations.Test;

public class Logout 
{
    @Test(priority = 2)
    public void dispatchLogout()
    {
        System.out.println("Dispatch user logged out");
    }
}
