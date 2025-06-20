package testNG.hierarchy;

import org.testng.annotations.*;
/**
 * This class demonstrates the hierarchy of TestNG annotations.
 * The order of execution is as follows:
 * 1. @BeforeSuite
 * 2. @BeforeTest
 * 3. @BeforeClass
 * 4. @BeforeMethod
 * 5. @Test (testCase1)
 * 6. @AfterMethod
 * 7. @BeforeMethod
 * 8. @Test (testCase2)
 * 9. @AfterMethod
 * 10. @AfterClass
 * 11. @AfterTest
 * 12. @AfterSuite
 */
public class Annotation 
{

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("1 - Before Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("2 - Before Test");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("3 - Before Class");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("4 - Before Method");
    }

    @Test
    public void testCase1() {
        System.out.println("5 - Test Case 1");
    }

    @Test
    public void testCase2() {
        System.out.println("5 - Test Case 2");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("6 - After Method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("7 - After Class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("8 - After Test");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("9 - After Suite");
    }
}
