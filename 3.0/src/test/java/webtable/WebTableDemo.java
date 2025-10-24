package webtable;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebTableDemo 
{

	public static void main(String[] args) 
	{
		RemoteWebDriver driver=new ChromeDriver();
		driver.get("https://letcode.in/");
		
		driver.findElement(By.xpath("//a[.='Work-Space']")).click();
		
		driver.findElement(By.xpath("//a[normalize-space()='Advance table']")).click();
		
		WebElement table=driver.findElement(By.id("advancedtable"));
		
		List<WebElement> header=table.findElements(By.xpath("//thead/tr/th"));
		
		Iterator<WebElement> itr=header.iterator();
		
		while(itr.hasNext())
		{
			System.out.println(itr.next().getText());
		}
	}

}
