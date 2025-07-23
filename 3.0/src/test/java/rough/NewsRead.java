package rough;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.awt.AWTException;
import java.awt.Robot;

public class NewsRead 
{
    @Test
    void NewsReadScroll() throws InterruptedException, AWTException
    {
        WebDriver driver=new ChromeDriver();
        driver.get("https://duckduckgo.com/");
        driver.manage().window().maximize();

        driver.findElement(By.cssSelector("input[id^='search']")).sendKeys("automation");

        Actions action=new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();

        Thread.sleep(2000);
        JavascriptExecutor je=(JavascriptExecutor)driver;
        je.executeScript("window.scrollBy({top: 800, behavior: 'smooth'});");

        for(int i=0;i<36;i++)
        {
            je.executeScript("window.scrollBy({top:50, behavior:'smooth'});");
            Thread.sleep(500);
        }
        Thread.sleep(3000);
    
        for(int i=0;i<36;i++)
        {
            je.executeScript("window.scrollBy({top:-50, behavior:'smooth'});");
            Thread.sleep(500);
        }

        Robot robot=new Robot();
        for(int i=0;i<20;i++)
        {
            robot.mouseWheel(1);
            Thread.sleep(500);
        }

        Thread.sleep(3000);
        for(int i=0;i<20;i++)
        {
            robot.mouseWheel(-1);
            Thread.sleep(500);
        }

        Thread.sleep(2000);
        driver.quit();
    }
}
