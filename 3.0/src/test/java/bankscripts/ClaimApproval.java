package bankscripts;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ClaimApproval 
{
	public static void main(String[] args) throws InterruptedException 
	{
		Scanner scn=new Scanner(System.in);
		System.out.println("Enter username");
		String userName=scn.next();
		System.out.println("Enter password");
		String Password=scn.next();
        scn.nextLine();
		System.out.println("Enter pacs name correctly in uppercase");
		String pacsName=scn.nextLine();
		System.out.println("Starting Auto approval please wait...");
		
		WebDriver driver=new ChromeDriver();
		driver.get("https://fasalrin.gov.in/login");
		driver.manage().window().maximize();
		Actions action=new Actions(driver);

		//login using credentials
		driver.findElement(By.name("username")).sendKeys(userName);
		driver.findElement(By.name("loginPwd")).sendKeys(Password);
		
		//wait for captcha & click login
		Thread.sleep(10000);
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		
		//wait and close window alert
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.cssSelector("button[class$='alertBodyModalBtn']")).click();
		
		//open dashboard menu
		driver.findElement(By.xpath("(//a[@title='Dashboard'])[1]")).click();
		
        //click on claim applications tab
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//a[.='Claim Applications']")).click();

        
		//click on view details of pending loan
        Thread.sleep(2000);
		// waitAndClick(driver.findElement(By.xpath("//p[contains(.,'Applications pending')]/following-sibling::button")), driver);
        driver.findElement(By.xpath("//p[contains(.,'Applications pending')]/following-sibling::button")).click();
		
		//select branch/pacs dropown
		Select pacs=new Select(driver.findElement(By.name("branchOrPacs")));
		pacs.selectByVisibleText("PACS");
		
		//select Pacs
		Select pacsList=new Select(driver.findElement(By.name("pacsList")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		pacsList.selectByVisibleText(pacsName);
		
		//click on proceed
		driver.findElement(By.xpath("//button/span[.='PROCEED']")).click();
		
		//store the no of records
		int records=Integer.parseInt(driver.findElement(By.xpath("//div[contains(.,'Total Count:')]/b")).getText());
		
		//move to review button
		for (int i=0;i<records;i++)
		{
			//move to review button
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement review=driver.findElement(By.xpath("//div[@class='claimApplicationListTable']/descendant::tbody/tr[1]/td[11]/div/button"));
			
			action.moveToElement(review).perform();
			
			//click on review button
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			review.click();
			
			//move to approve button
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement approve=driver.findElement(By.xpath("//button/span[.='APPROVE']"));
			action.moveToElement(approve).perform();
			
			//click on approve button
			approve.click();
			
			//click on confirm
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.findElement(By.xpath("//button[.='CONFIRM']")).click();
		
			//click on ok
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.findElement(By.xpath("//button[.='OK']")).click();
		}
        action.moveToElement(driver.findElement(By.xpath("(//span[.='Logout'])[1]"))).perform();
		Thread.sleep(2000);
		System.out.println("Operation completed");
		driver.quit();
	}

}

