package cookie;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CookieExample 
{
    public static void main(String[] args) 
    {
        WebDriver driver=new ChromeDriver();
        driver.get("https://letcode.in/test");

        Set<Cookie> cookies=driver.manage().getCookies();
        Iterator<Cookie>itr=cookies.iterator();
        while(itr.hasNext())
        {
            System.out.println(itr.next().getValue());
        }
    }
    
}
