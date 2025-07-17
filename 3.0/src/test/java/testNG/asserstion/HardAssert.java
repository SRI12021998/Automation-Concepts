package testNG.asserstion;

import org.testng.annotations.Test;
import org.testng.Assert;

public class HardAssert 
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

    @Test
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
    @Test
    public void methodE() 
    {
        String result=null;
        Assert.assertNull(result, "Test failed, result was not NULL");
        System.out.println("Method E executed successfully.");
    }
    @Test
    public void methodF() 
    {
        String result=null;
        Assert.assertNotNull(result, "Test failed, result was NULL");
        System.out.println("Method F executed successfully.");
    }

    @Test
    public void anotherTest()
    {
        try 
        {
            System.out.println(1/0); // This will throw an ArithmeticException
        } catch (ArithmeticException e) 
        {
            Assert.fail();
        }
    }
}
