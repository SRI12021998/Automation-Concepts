package rough;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SanthoshDoubt 
{
    @Test
    public void find() throws InterruptedException 
    {
        WebDriver driver=new ChromeDriver();
        driver.get("https://www.expedia.com/");
        driver.manage().window().maximize();
        JavascriptExecutor je = (JavascriptExecutor) driver;
        
//        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(15));
        
        FluentWait<WebDriver> wait=new FluentWait<WebDriver>(driver)
        		.pollingEvery(Duration.ofMillis(300))
        		.withTimeout(Duration.ofSeconds(15))
        		.ignoring(NoSuchElementException.class);
        
        driver.findElement(By.xpath("//span[.='Flights']")).click();
        
        wait.until(webDriver -> je.executeScript("return document.readyState").equals("complete"));
        
        wait.withMessage("waiting for the element to be clickable")
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Leaving from')]")));
        
        driver.findElement(By.xpath("//button[contains(@aria-label,'Leaving from')]")).click();
        
        wait.until(webDriver -> je.executeScript("return document.readyState").equals("complete"));
        
        wait.withMessage("waiting for the element to enter text")
		.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='origin_select']")));
        
        driver.findElement(By.xpath("//input[@id='origin_select']")).sendKeys("chen");
        
        wait.until(webDriver -> je.executeScript("return document.readyState").equals("complete"));
        
        wait.withMessage("Waiting for the element to be visible")
		.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@role='list']"))));
        
        List<WebElement> suggestions=driver.findElements(By.xpath("//ul[@role='list']/li"));
        
        suggestions.forEach(suggestion-> System.out.println(suggestion.findElement(By.xpath("descendant::button")).getAttribute("aria-label")));
        
    }
}
