package testNG.listeners;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoAnnotationTransformer 
{
    @Test
    public void methodA() 
    {
        String result="PASS";
        Assert.assertEquals(result,"PASS", "Test failed, result was not PASS");
        System.out.println("Method A executed successfully.");
    }

    @Test
    public void methodB() 
    {
        String result="PASS";
        Assert.assertNotEquals(result,"PASS", "Test failed, result was PASS");
        System.out.println("Method B executed successfully.");
    }

    @Test// (dependsOnMethods = { "methodB"}, priority = 3)
    public void methodC() 
    {
        String result="FAIL";
        Assert.assertTrue(result.equals("PASS"), "Test failed, result was not PASS");
        System.out.println("Method C executed successfully.");
    }
    
    @Test
    public void methodD() 
    {
        String result="FAIL";
        Assert.assertFalse(result.equals("PASS"), "Test failed, result was not PASS");
        System.out.println("Method D executed successfully.");
    }
}
