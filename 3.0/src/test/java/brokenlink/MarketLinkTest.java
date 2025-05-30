package brokenlink;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MarketLinkTest 
{

	public static void main(String[] args) 
	{
		WebDriver driver=new ChromeDriver();
		driver.get("file:///D:/WORKSPACE/test_links.html");
		driver.manage().window().maximize();
		
		//store all the links
		List <WebElement> links=driver.findElements(By.tagName("a"));
		
		//iterate and take href value
		for(WebElement link: links)
		{
			String url=link.getAttribute("href");
			try 
			{
				HttpURLConnection connection=(HttpURLConnection)(new URL(url).openConnection());
				connection.setRequestMethod("GET");
				connection.connect();
				int statusCode=connection.getResponseCode();
				
				if(statusCode>=400)
				{
					System.out.println("Broken link: "+url+" | Status Code: " +statusCode);
				}
				else 
                {
                    System.out.println("Valid link: "+url+" | Status Code: " + statusCode);
                }
			} 
			catch (MalformedURLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		driver.quit();
	}

}
