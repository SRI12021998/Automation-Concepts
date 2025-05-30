package AutomationTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VersionUpgrade 
{

	public static void main(String[] args) throws InterruptedException 
	{
		Scanner scn=new Scanner(System.in);
		System.out.println("Enter market URL");
		String marketURL=scn.next();
		System.out.println("Enter Ivy admin password");
		String password=scn.next();
		System.out.println("Enter old version - 4 digits");
		String oldVersion=scn.next();
		System.out.println("Enter new version - 4 digits");
		String newVersion=scn.next();
		System.out.println("Enter new apk URL");
		String apk=scn.next();
		System.out.println("Getting started...");
		scn.close();
		LocalDateTime local=LocalDateTime.now();
		DateTimeFormatter dateFormat=DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter monthFormat=DateTimeFormatter.ofPattern("MMM",Locale.ENGLISH);
		DateTimeFormatter yearFormat=DateTimeFormatter.ofPattern("YYYY");
		String date=local.format(dateFormat);
		String month=local.format(monthFormat);
		String year=local.format(yearFormat);
		
	
		WebDriver driver=new ChromeDriver();
		driver.get(marketURL);
		JavascriptExecutor je=(JavascriptExecutor)driver;
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(15));
		driver.manage().window().maximize();
		//login
		driver.findElement(By.id("UserName")).sendKeys("IvyAdmin");
		driver.findElement(By.id("Password")).sendKeys(password);
		driver.findElement(By.id("Login")).click();
		//open tenant menu
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Tenant']")));
		driver.findElement(By.xpath("//a[@title='Tenant']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		//open HHT sub menu
		je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[@title='HHT']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='HHT']")));
		driver.findElement(By.xpath("//a[@title='HHT']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		//open version Menu
		je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[@title='Version']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Version']")));
		driver.findElement(By.xpath("//a[@title='Version']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		//open version master sub menu
		je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[@title='Version Master']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Version Master']")));
		driver.findElement(By.xpath("//a[@title='Version Master']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		//switch to frame 
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@id='iContent' and contains(@src,'/web/HHTVersion/Index')]")));
		
		//click on tenant dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo']/ancestor::div[contains(@class,'select-wrapper')]/input")));
		driver.findElement(By.xpath("//span[.='Bimbo']/ancestor::div[contains(@class,'select-wrapper')]/input")).click();
		
		//select tenant
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo']")));
		driver.findElement(By.xpath("//span[.='Bimbo']")).click();
		
		//click on division dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo Division Admin']/ancestor::div[contains(@class,'select')]/input")));
		driver.findElement(By.xpath("//span[.='Bimbo Division Admin']/ancestor::div[contains(@class,'select')]/input")).click();
		
		//select division
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo Division Admin']")));
		driver.findElement(By.xpath("//span[.='Bimbo Division Admin']")).click();
		
		//click on add button
		driver.findElement(By.xpath("//a[.='Add']")).click();
		
		//enter version
		driver.findElement(By.xpath("//input[@id='AHV_Version']")).sendKeys(newVersion);
		
		//enter version code
		driver.findElement(By.xpath("//input[@id='AHV_Version_Code']")).sendKeys(newVersion);
		
		//enter apk download url
		driver.findElement(By.xpath("//input[@id='AHV_Download_Url']")).sendKeys(apk);
		
		//open calendar
		driver.findElement(By.xpath("//input[@id='AHV_Date']/following-sibling::button")).click();
		
		//select year
		WebElement yearDropDown=driver.findElement(By.xpath("//select[@class='ui-datepicker-year']"));
		Select yyyy=new Select(yearDropDown);
		yyyy.selectByVisibleText(year);
		
		//select month
		WebElement monDropDown=driver.findElement(By.xpath("//select[@class='ui-datepicker-month']"));
		Select mon=new Select(monDropDown);
		mon.selectByVisibleText(month);
		
		//pick date
		driver.findElement(By.xpath("//tr/td/a[.="+date+"]")).click();
		
		//save version details
		driver.findElement(By.id("btnAddSave")).click();
		
		//switch back from frame to capture notification message
		driver.switchTo().defaultContent();
		
		//show notification message
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p")));
		System.out.println(driver.findElement(By.xpath("//div[@id='card-alert']/div/p")).getText());
		
		//close notification
		driver.findElement(By.xpath("//div[@id='card-alert']/button[@aria-label='Close']")).click();
		
//		wait until pop is closed - alternative 
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p")));
		
		//switch to frame for closing add window
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='iContent' and contains(@src,'/web/HHTVersion/Index')]")));
		
		//close the add window
		driver.findElement(By.xpath("//button[.='Close']")).click();
		
		//switch back from frame
		driver.switchTo().defaultContent();
		
		//open webapi menu
		je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[@title='WebApi']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='WebApi']")));
		driver.findElement(By.xpath("//a[@title='WebApi']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		//open versionWiseUrl submenu
		je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[@title='VersionWiseUrl']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='VersionWiseUrl']")));
		driver.findElement(By.xpath("//a[@title='VersionWiseUrl']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		
		//switch to frame
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@id='iContent' and contains(@src,'/web/HHTVersionwiseApiUrl')]")));
		
		//click on tenant dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo']/ancestor::div[contains(@class,'select-wrapper')]/input")));
		driver.findElement(By.xpath("//span[.='Bimbo']/ancestor::div[contains(@class,'select-wrapper')]/input")).click();
		
		//select tenant
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo']")));
		driver.findElement(By.xpath("//span[.='Bimbo']")).click();
		
		//click on division dropdown
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo Division Admin']/ancestor::div[contains(@class,'select')]/input")));
		driver.findElement(By.xpath("//span[.='Bimbo Division Admin']/ancestor::div[contains(@class,'select')]/input")).click();
		
		//select division
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Bimbo Division Admin']")));
		driver.findElement(By.xpath("//span[.='Bimbo Division Admin']")).click();
		
		//store seller type elements
		VersionUpgrade.waitForPageToLoad(driver);
		List <WebElement> options=driver.findElements(By.xpath("//select[@id='Seller_Type']/option"));
		ArrayList <String> sellerNames=new ArrayList<>();
		
		//remove unwanted options & store visible text values
		Iterator <WebElement> itr=options.iterator();
		while(itr.hasNext())
		{
			String text = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerText;", itr.next());
			if (text.equals("--Select--")||text.equals("-ALL-"))
			{
				continue;
			}
			else
			{
				sellerNames.add(text);
			}
		}

		//Version url copy for each seller
		for (int i=0;i<sellerNames.size();i++)
		{
			//select seller type
			je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//select[@id='Seller_Type']/parent::div/input")));
			driver.findElement(By.xpath("//select[@id='Seller_Type']/parent::div/input")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//select[@id='Seller_Type']/parent::div/descendant::li/span[contains(.,'"+sellerNames.get(i)+"')]")));
			driver.findElement(By.xpath(
					"//select[@id='Seller_Type']/parent::div/descendant::li/span[contains(.,'"+sellerNames.get(i)+"')]")).click();
			VersionUpgrade.waitForPageToLoad(driver);
			
			//select new version
			driver.findElement(By.xpath("//select[@id='VersionId']/parent::div/input")).click();
			VersionUpgrade.waitForPageToLoad(driver);
			je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath(
					"//select[@id='VersionId']/parent::div/descendant::li/span[.="+newVersion+"]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//select[@id='VersionId']/parent::div/descendant::li/span[.="+newVersion+"]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//select[@id='VersionId']/parent::div/descendant::li/span[.="+newVersion+"]")));
			driver.findElement(By.xpath("//select[@id='VersionId']/parent::div/descendant::li/span[.="+newVersion+"]")).click();
			
			//click copy button
			driver.findElement(By.xpath("//label[.='Copy']")).click();
			
			//select seller type to copy
			driver.findElement(By.xpath("//select[@id='Source_SellerTypeId']/parent::div/input")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//select[@id='Source_SellerTypeId']/parent::div/descendant::li/span[contains(.,'"+sellerNames.get(i)+"')]")));
			driver.findElement(By.xpath(
					"//select[@id='Source_SellerTypeId']/parent::div/descendant::li/span[contains(.,'"+sellerNames.get(i)+"')]")).click();
			VersionUpgrade.waitForPageToLoad(driver);
			
			//select old version to copy from
			driver.findElement(By.xpath("//select[@id='Source_VersionId']/parent::div/input")).click();
			VersionUpgrade.waitForPageToLoad(driver);
			je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath(
					"//select[@id='Source_VersionId']/parent::div/descendant::li/span[.="+oldVersion+"]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//select[@id='Source_VersionId']/parent::div/descendant::li/span[.="+oldVersion+"]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//select[@id='Source_VersionId']/parent::div/descendant::li/span[.="+oldVersion+"]")));
			driver.findElement(By.xpath("//select[@id='Source_VersionId']/parent::div/descendant::li/span[.="+oldVersion+"]")).click();
			
			//click apply button
			driver.findElement(By.xpath("//button[@id='btnApply']")).click();
			
			//click close button
			driver.findElement(By.xpath("//button[@id='btnApply']/preceding-sibling::button")).click();
			
			//click save button
			driver.findElement(By.xpath("//label[.='Save']")).click();
			
			
			//switch back from frame to capture notification message
			driver.switchTo().defaultContent();
			
			//show notification message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='card-alert']/div/p")));
			System.out.println(driver.findElement(By.xpath("//div[@id='card-alert']/div/p")).getText()+" for "+sellerNames.get(i));
			
			//close notification
			driver.findElement(By.xpath("//div[@id='card-alert']/button[@aria-label='Close']")).click();
			
			//switch back to frame 
			driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='iContent' and contains (@src,'/web/HHTVersionwiseApiUrl')]")));
		}
		
		//switch to default
		driver.switchTo().defaultContent();
		
		//open version Menu
		je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[@title='Application Configuration']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Application Configuration']")));
		driver.findElement(By.xpath("//a[@title='Application Configuration']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
				
		//open version master sub menu
		je.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//a[@title='Clear Cache']")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Clear Cache']")));
		driver.findElement(By.xpath("//a[@title='Clear Cache']")).click();
		VersionUpgrade.waitForPageToLoad(driver);
		
		//switch to frame
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(
				"//iframe[@id='iContent' and contains (@src,'/web/DMS/Cache/Index')]")));
		for (int i=0;i<4;i++)
		{
			
			//click clear-all button
			VersionUpgrade.waitForPageToLoad(driver);
			driver.findElement(By.xpath("//a[.='Clear All ']")).click();
			
			//click ok
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.='OK']")));
			driver.findElement(By.xpath("//button[.='OK']")).click();
			
			//close notification
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='card-alert']/button[@aria-label='Close']")));
			driver.findElement(By.xpath("//div[@id='card-alert']/button[@aria-label='Close']")).click();
		}
		System.out.println("Clear cache done");
		driver.quit();
		System.out.println("New version addded & mapped successfully");
	}
	public static void waitForPageToLoad(WebDriver driver) 
	{
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    wait.until(webDriver -> ((JavascriptExecutor) webDriver)
	            .executeScript("return document.readyState").equals("complete"));
	    //Wait for jQuery AJAX to finish
	    wait.until(webDriver -> ((JavascriptExecutor) webDriver)
	            .executeScript("return typeof jQuery == 'undefined' || jQuery.active == 0"));
	}

}
