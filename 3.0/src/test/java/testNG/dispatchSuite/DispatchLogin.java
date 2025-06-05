package testNG.dispatchSuite;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import junit.framework.Assert;

public class DispatchLogin 
{
    WebDriver driver;

    @BeforeSuite
    public void marketLinkCheck()
    {
        driver=new ChromeDriver();
        driver.get("file:///D:/WORKSPACE/test_links.html");
        String url=driver.findElement(By.xpath("//a[.='CO QA']")).getAttribute("href");
        HttpURLConnection connection;
        try 
        {
            connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode=connection.getResponseCode();
            if(responseCode>=400)
                {
                    System.out.println("Link not working error code - "+responseCode);
                    Assert.fail("Testing stopped due to server down");
                }
            else
                {
                    System.out.println("Link working status - "+responseCode);
                }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally
        {
            driver.quit();
        }
    }

    @Test
    public void loginCheck()
    {
        driver=new ChromeDriver();
        driver.get("file:///D:/WORKSPACE/test_links.html");
        driver.findElement(By.xpath("//a[.='CO QA']")).click();
        Credentials cs=new Credentials();
        cs.credentialsFetch("dispatch");
        driver.findElement(By.id("UserName")).sendKeys(Credentials.login);
		driver.findElement(By.name("Password")).sendKeys(Credentials.pwd);
		driver.findElement(By.id("Login")).click();
    }

    @Test(dependsOnMethods="loginCheck")
    public void salesOrderScreenLoad()
    {
        //wait for page to load
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
		//open sales management menu
		driver.findElement(By.xpath("//a[@title='Sales Management']")).click();
		
		//move to sales order create sub menu and open
		WebElement element=driver.findElement(By.cssSelector("a[title='Sales Order Create']"));
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();

        //wait and take screenshot
        WebDriverWait wait=new WebDriverWait (driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[.='Sales Order – Seller Creation']")));
        TakesScreenshot ts=(TakesScreenshot)driver;
        File src=ts.getScreenshotAs(OutputType.FILE);
        try 
        {
            FileUtils.copyFile(src,new File("D:\\VS Local repo\\evidence\\"+System.currentTimeMillis()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
