package testNG.dispatchSuite;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import junit.framework.Assert;

public class DispatchLogin 
{
    WebDriver driver;
	WebDriverWait wait;
	Actions action;
	String year;
	String month;
	String date;
	JavascriptExecutor je;

	DispatchLogin()
	{
		//browser chrome
		this.driver=new ChromeDriver();
		//wait config
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//action 
		this.action = new Actions(driver);

		//javascriptexecutor
		je=(JavascriptExecutor)driver;

		//date config
		LocalDateTime dt=LocalDateTime.now();
		
		this.year=dt.format(DateTimeFormatter.ofPattern("YYYY"));
		this.month=dt.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH));
		this.date=dt.format(DateTimeFormatter.ofPattern("d"));
	}

    @BeforeSuite
    public void marketLinkCheck()
    {
        driver.get("file:///D:/WORKSPACE/test_links.html");
		driver.manage().window().maximize();
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
    }

    @Test
    public void loginCheck()
    {
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

    @Test(dependsOnMethods="salesOrderScreenLoad")
    public void salesOrderCreation()
	{
		//wait and switch to i content
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iContent")));
		
		//wait and enter sales person 
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("SalesPerson")));
		waitAndClick(driver.findElement(By.id("SalesPerson")));
		driver.findElement(By.id("SalesPerson")).sendKeys(Keys.ARROW_DOWN);

		//wait for suggestion to load
		for(long period=System.currentTimeMillis()+5000;period>System.currentTimeMillis();)
		{
		try
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(.,'014214_3000')])[1]")));
				break;
			}
		catch(TimeoutException e)
			{
				driver.findElement(By.id("SalesPerson")).sendKeys(Keys.ARROW_DOWN);
			}
		}
		
		//select the salesperson
		action.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'014214_3000')])[1]"))).doubleClick().perform();
		
		//wait and enter retailer
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Retailer']")));
		waitAndClick(driver.findElement(By.xpath("//input[@id='Retailer']")));
		action.keyDown(Keys.ARROW_DOWN).perform();
		// driver.findElement(By.xpath("//input[@id='Retailer']")).sendKeys("DEMORET1");

		//wait for suggestion to loads
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(.,'DEMORET1')])[1]")));
		
		//select the retailer
		action.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'DEMORET1')])[1]"))).doubleClick().perform();
		
		//wait and select the delivery date
		waitAndClick(driver.findElement(By.xpath("//input[@id='DeliveryDate']")));
		driver.findElement(By.xpath("//select[@class='ui-datepicker-year']/option[.='"+year+"']")).click();
		driver.findElement(By.xpath("//select[@class='ui-datepicker-month']/option[.='"+month+"']")).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//a[.='"+date+"']")).click();
		
		//wait and select shipping address
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']/a")));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@id='divShipAddressId']/div/ul/li[contains(.,'VEREDA')]"))));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div/ul/li[contains(.,'VEREDA')]")).click();

		//select payment method
		driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/div[@class='mt-select']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")));
		driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")).click();
		
		//add sku
		SkuData sd=new SkuData();
		HashMap<String, Integer> input= sd.skuDetails();
		for(Map.Entry<String,Integer> entry:input.entrySet())
		{
			try 
			{
				skuInput(entry.getKey(),entry.getValue());
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		//click on submit order button
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(text(),'Submit Order')]")));
		
		//wait and capture the message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p/i[@class='fa fa-check']")));
		String message=driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[@id='card-alert']/div/p")).getText();
		System.out.println(message);
		
		//close the notification
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[2]/button")));
		
		//close the print
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@id='btnPrint']/following-sibling::button")));

	}
	public void waitAndClick(WebElement element)
	{
		Wait <WebDriver> fluentWait=new FluentWait<WebDriver>(driver)
		.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofMillis(300))
		.ignoring(org.openqa.selenium.ElementClickInterceptedException.class)
		.ignoring(org.openqa.selenium.StaleElementReferenceException.class)
		.ignoring(org.openqa.selenium.ElementNotInteractableException.class)
		.ignoring(TimeoutException.class);
		fluentWait.until(webDriver->{	element.click();
		return true;});
	}
	public void skuInput(String skuCode, int qty) throws InterruptedException
	{
		//enter SKU and select
		driver.findElement(By.xpath("//input[@id='SearchName']")).sendKeys(skuCode);
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul/li/aundefined"),0));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//aundefined[@tabindex='-1']")));
		waitAndClick(driver.findElement(By.xpath("//aundefined[@tabindex='-1']")));

		//enter SKU qty
		driver.findElement(By.xpath("//tbody[@class='grid-data-container']/tr/td[contains(.,'"+skuCode+"')]/parent::tr/td[7]/input")).sendKeys(String.valueOf(qty));
		driver.findElement(By.xpath("//tbody[@class='grid-data-container']/tr/td[contains(.,'"+skuCode+"')]/parent::tr/td[7]/input")).sendKeys(Keys.TAB);
		action.moveToElement(driver.findElement(By.xpath("//div[@id='soHeader']"))).click().perform();
	}
    
}
