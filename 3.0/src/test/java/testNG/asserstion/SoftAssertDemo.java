package testNG.asserstion;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SoftAssertDemo
{
    @Test
    public void testSoftAssertion() 
    {
    SoftAssert softAssert = new SoftAssert();

    softAssert.assertEquals("abc", "xyz", "Strings don't match");
    softAssert.assertTrue(false, "Condition is false");
    System.out.println("Test continues even after assertion failures.");

    softAssert.assertAll();  // Reports all failures
    }
    
}
