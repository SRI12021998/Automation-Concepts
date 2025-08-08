package extentreport;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtentReportDemo 
{
	@Test
	public void testA()
	{
		Assert.assertTrue(true, "Test A passed");
	}

    @Test
    public void testB()
    {
        Assert.assertTrue(false, "Test B failed");
    }

    @Test(dependsOnMethods = {"testB"})
    public void testC()
    {
       System.out.println("this will not run because testB failed");
    }
}
