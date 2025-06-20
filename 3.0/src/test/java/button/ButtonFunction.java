package button;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ButtonFunction 
{
    public static void main(String[] args) throws InterruptedException 
    {
        RemoteWebDriver driver=new ChromeDriver();
        driver.get("https://letcode.in/test");
        driver.manage().window().maximize();
        Actions action = new Actions(driver);

        //button functionality screen
        driver.findElement(By.xpath("//a[contains(.,'Click')]")).click();

        //new page screen
        driver.findElement(By.id("home")).click();

        //navigate back to the previous page
        driver.navigate().back();
        driver.navigate().refresh();

        //find the position of the button
        Point p=driver.findElement(By.id("position")).getLocation();
        System.out.println("Button position: " + p.getX() + ", " + p.getY());

        //find the color of the button
        System.out.println(driver.findElement(By.id("color")).getCssValue("background-color"));
        
        //find the size of the button
        Rectangle rec=driver.findElement(By.id("property")).getRect();
        System.out.println(rec.height + " " + rec.width);

        //find the button enabled or disabled
        action.moveToElement(driver.findElement(By.cssSelector("button[title='Disabled button']"))).perform();
        boolean isEnabled = driver.findElement(By.cssSelector("button[title='Disabled button']")).isEnabled();
        System.out.println("isEnabled: " + isEnabled);

        //click and hold the button
        action.moveToElement(driver.findElement(By.xpath("//h2[contains(.,' Button Hold!')]/ancestor::button"))).perform();
        WebElement buttonToHold=driver.findElement(By.xpath("//h2[contains(.,' Button Hold!')]/ancestor::button"));
        action.clickAndHold(buttonToHold).perform();

        Thread.sleep(3000); // Hold for 2 seconds
        action.release().perform(); // Release the button

        driver.quit();
    }
}
