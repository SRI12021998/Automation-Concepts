package testNG.listeners;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;

@Listeners(MyTestListener.class)
public class DemoTest
{
    @Test(priority = 1)
    public void methodA() 
    {
        String result="PASS";
        Assert.assertEquals(result,"PASS", "Test failed, result was not PASS");
        System.out.println("Method A executed successfully.");
    }

    @Test(priority = 2)
    public void methodB() 
    {
        String result="PASS";
        Assert.assertNotEquals(result,"PASS", "Test failed, result was PASS");
        System.out.println("Method B executed successfully.");
    }

    @Test (dependsOnMethods = { "methodB"}, priority = 3)
    public void methodC() 
    {
        String result="FAIL";
        Assert.assertTrue(result.equals("PASS"), "Test failed, result was not PASS");
        System.out.println("Method C executed successfully.");
    }
    // @Test
    // public void methodD() 
    // {
    //     String result="FAIL";
    //     Assert.assertFalse(result.equals("PASS"), "Test failed, result was not PASS");
    //     System.out.println("Method D executed successfully.");
    // }


}
