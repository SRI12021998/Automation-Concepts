package testNG.hierarchy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class LoginTest {

    WebDriver driver;

    @BeforeSuite
    public void connectToDB() {
        // Simulate DB connection setup
        System.out.println("Connected to database");
    }

    @BeforeTest
    public void setUpTestEnvironment() {
        // Simulate test environment setup
        System.out.println("Setup test environment: QA URL, configs, etc.");
    }

    @BeforeClass
    public void launchBrowser() {
        // Launch browser once for this class
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println("Browser launched");
    }

    @BeforeMethod
    public void loginToApp() {
        // Login before each test
        driver.get("https://bimbo-co-01-qa.ivycpg.com/web/DMS");
        System.out.println("User logged in");
    }

    @Test
    public void verifyDashboardTitle() {
        String actualTitle = driver.getTitle();
        String expectedTitle = "IvyDMS - Login";
        Assert.assertEquals(actualTitle, expectedTitle, "Title mismatch");
        System.out.println("Dashboard title verified successfully");
    }

    @Test
    public void verifyProfileSection() {
        boolean isProfileVisible = driver.getPageSource().contains("Profile");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isProfileVisible, "Profile section is missing");
        System.out.println("I will be visible for you");
        softAssert.assertAll();
        System.out.println("Profile section verified successfully");
    }

    @AfterMethod
    public void logout() {
        // Logout after each test
        System.out.println("User logged out");
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
        System.out.println("Browser closed");
    }

    @AfterTest
    public void cleanupTestEnvironment() {
        System.out.println("Test environment cleaned up");
    }

    @AfterSuite
    public void disconnectFromDB() {
        System.out.println("Disconnected from database");
    }
}
