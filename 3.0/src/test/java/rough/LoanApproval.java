package rough;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoanApproval 
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
		
		//click on view details of pending loan
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
			WebElement review=driver.findElement(By.xpath("//div[@class='loanApplicationTable']/descendant::tbody/tr[1]/td[8]/div/button"));
			Actions action=new Actions(driver);
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
		driver.findElement(By.xpath("(//span[.='Logout'])[1]")).click();
		Thread.sleep(2000);
		System.out.println("Operation completed");
		driver.quit();
	}
}
