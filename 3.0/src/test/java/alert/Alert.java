package alert;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Alert 
{
     public static void main(String[] args) throws InterruptedException 
    {
        RemoteWebDriver driver=new ChromeDriver();
        driver.get("https://letcode.in/test");
        driver.manage().window().maximize();
        Actions action = new Actions(driver);

        //button functionality screen
        action.moveToElement(driver.findElement(By.xpath("//a[contains(.,'Dialog')]"))).click().perform();

        Thread.sleep(2000); // Wait for the page to load
        //simple alert
        driver.findElement(By.id("accept")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        Thread.sleep(2000);
        //confirm alert
        driver.findElement(By.id("confirm")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();

        Thread.sleep(2000);
        //prompt alert
        driver.findElement(By.id("prompt")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().sendKeys("Hello, this is a test alert!");
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        Thread.sleep(2000);
        //modern alert
        action.moveToElement(driver.findElement(By.id("modern"))).click().perform();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button[aria-label='close']")).click();
        Thread.sleep(2000);

        driver.quit();

    } 
}
