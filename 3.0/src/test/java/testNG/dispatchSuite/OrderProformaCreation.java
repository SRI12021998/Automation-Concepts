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
import org.openqa.selenium.io.FileHandler;
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

public class OrderProformaCreation 
{
    WebDriver driver;
	WebDriverWait wait;
	Actions action;
	String year;
	String month;
	String date;
	JavascriptExecutor je;
	TakesScreenshot ts;
	SkuData sd;
	SellerRetailerData srd;
	String orderNo;

	OrderProformaCreation()
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

		//screenshot
		ts=(TakesScreenshot)driver;

		//instance of skuData
		sd=new SkuData();

		//instance of sellerRetailerData
		srd=new SellerRetailerData();
		srd.sellerRetailerDetails();
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
		action.moveToElement(element).click().perform();

        //wait and take screenshot
        WebDriverWait wait=new WebDriverWait (driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[.='Sales Order – Seller Creation']")));
        File src=ts.getScreenshotAs(OutputType.FILE);
        try 
        {
            FileUtils.copyFile(src,new File("D:\\VS Local repo\\evidence\\"+System.currentTimeMillis()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test(dependsOnMethods="salesOrderScreenLoad")
    public void salesOrderCreation() throws InterruptedException
	{
		//wait and switch to i content
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@src='/web/DMS/SalesOrder/Create/Sales']")));
		
		//wait and enter sales person 
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("SalesPerson")));
		waitAndClick(driver.findElement(By.id("SalesPerson")));
		driver.findElement(By.id("SalesPerson")).sendKeys(srd.seller);		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(.,'"+srd.seller+"')]")));
		
		//select the salesperson
		action.moveToElement(driver.findElement(By.xpath("//a[contains(.,'"+srd.seller+"')]"))).click().perform();
		
		//wait and enter retailer
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='Retailer']")));
		waitAndClick(driver.findElement(By.xpath("//input[@id='Retailer']")));
		action.keyDown(Keys.ARROW_DOWN).perform();

		//wait for suggestion to loads
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[contains(.,'"+srd.retailer+"')])[1]")));
		
		//select the retailer
		action.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'"+srd.retailer+"')])[1]"))).doubleClick().perform();
		
		//wait and select the delivery date
		waitAndClick(driver.findElement(By.xpath("//input[@id='DeliveryDate']")));
		driver.findElement(By.xpath("//select[@class='ui-datepicker-year']/option[.='"+year+"']")).click();
		driver.findElement(By.xpath("//select[@class='ui-datepicker-month']/option[.='"+month+"']")).click();
		driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//a[.='"+date+"']")).click();
		
		//wait and select shipping address
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']/a")));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div[@class='mt-select']/a")).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@id='divShipAddressId']/div/ul/li[2]"))));
		driver.findElement(By.xpath("//div[@id='divShipAddressId']/div/ul/li[2]")).click();

		//select payment method if cash retailer
		if (driver.findElement(By.xpath("//div[@id='divPaymentMethodId']")).isDisplayed())
		{
			driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/div[@class='mt-select']")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")));
			driver.findElement(By.xpath("//div[@id='divPaymentMethodId']/descendant::li[2]")).click();
		}
		
		
		//add sku
		HashMap<String, Integer> input= sd.skuDetails();
		for(Map.Entry<String,Integer> entry:input.entrySet())
		{
			try 
			{
				skuInput(entry.getKey(),entry.getValue());
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
		orderNo=message.replace(" - Order Submitted Successfully..!!","");

		//close the notification
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='container-fluid content-wraper']/div[2]/button")));
		
		//close the print
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[@id='btnPrint']/following-sibling::button")));

	}

	//used for salesOrderCreation
	public void waitAndClick(WebElement element)
	{
		Wait <WebDriver> fluentWait=new FluentWait<WebDriver>(driver)
		.withTimeout(Duration.ofSeconds(15))
		.pollingEvery(Duration.ofMillis(300))
		.ignoring(org.openqa.selenium.ElementClickInterceptedException.class)
		.ignoring(org.openqa.selenium.StaleElementReferenceException.class)
		.ignoring(org.openqa.selenium.ElementNotInteractableException.class)
		.ignoring(TimeoutException.class);
		fluentWait.until(webDriver->
		{element.click();
		return true;});
	}

	//used for salesOrderCreation
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
    

	@Test(dependsOnMethods="salesOrderCreation")
	public void proformaCreationScreenLoad()
	{
		//switch back to default
		driver.switchTo().defaultContent();

		//move and click on proforma invoice menu
		action.moveToElement(driver.findElement(By.cssSelector("a[title='Proforma Invoice']"))).click().perform();

		//open sub menu 
		action.moveToElement(driver.findElement(By.xpath("//a[contains(.,'Proforma Invoice Create')]"))).click().perform();

		//wait and take screenshot
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[.='Proforma Invoice Creation']")));
		File src=ts.getScreenshotAs(OutputType.FILE);
		try 
		{
			FileHandler.copy(src,new File("D:\\VS Local repo\\evidence\\"+System.currentTimeMillis()+".png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


	@Test(dependsOnMethods="proformaCreationScreenLoad")
	public void proformaCreation()
	{
		//wait and switch to frame
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(By.xpath("//iframe[@src='/web/DMS/SalesInvoiceN/Index/PROFORMA']"))));

		//wait and enter sales person 
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("SalesManName")));
		waitAndClick(driver.findElement(By.id("SalesManName")));
		driver.findElement(By.id("SalesManName")).sendKeys(srd.seller);
		driver.findElement(By.id("SalesManName")).sendKeys(Keys.ARROW_DOWN);
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'"+srd.seller+"')]")));
		}
		catch(Exception e)
		{
			driver.findElement(By.id("SalesManName")).sendKeys(Keys.ARROW_DOWN);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'"+srd.seller+"')]")));
			System.out.println("proforma seller selection tried with exception");
		}
		
		//select the salesperson
		driver.findElement(By.xpath("//a[contains(.,'"+srd.seller+"')]")).click();

		//input sale order number
		driver.findElement(By.id("SalesOrderNo")).sendKeys(orderNo);

		//click on search button
		je.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//a[.='Search'])[1]")));
		
		//wait for record to be fetched
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.='Generate Proforma Invoice']")));

		//click on generate button
		waitAndClick(driver.findElement(By.xpath("//a[.='Generate Proforma Invoice']")));

		//wait for proforma header screen to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[.='Proforma Invoice Header']")));

		//click on ok button to close pop-up
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='Next']")));
		waitAndClick(driver.findElement(By.xpath("//button[.='Next']")));

		//wait and click create proforma invoice button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSubmitInvoice")));
		je.executeScript("arguments[0].click();", driver.findElement(By.id("btnSubmitInvoice")));

		//wait and click ok button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mymodelbtnOk")));
		je.executeScript("arguments[0].click();", driver.findElement(By.id("mymodelbtnOk")));
	}
}
