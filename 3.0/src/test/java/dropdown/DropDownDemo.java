package dropdown;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class DropDownDemo 
{
    public static void main(String[] args) throws InterruptedException 
    {
        WebDriver driver=new ChromeDriver();
        driver.get("https://letcode.in/test");
        driver.manage().window().maximize();
        Actions action=new Actions(driver);
        

        driver.findElement(By.cssSelector("a[href='/dropdowns']")).click();

        Select fruit=new Select(driver.findElement(By.id("fruits")));
        fruit.selectByVisibleText("Banana");

        //multiple selection method-1
        Select hero=new Select(driver.findElement(By.id("superheros")));
        System.out.println("Is this multiple selection dropdown - "+hero.isMultiple());
        hero.selectByIndex(0);
        hero.selectByIndex(1);
        List <WebElement> selectedElements=hero.getAllSelectedOptions();
        selectedElements.forEach(element-> System.out.println(element.getText()));
        System.out.println();
        List <WebElement> allElements=hero.getOptions();
        allElements.forEach(element->System.out.println(element.getText()));
        hero.deselectAll();

        //multiple selection method-2
        Thread.sleep(3000);
        action.keyDown(Keys.CONTROL).click(driver.findElement(By.xpath("//option[.='Ant-Man']")))
        .click(driver.findElement(By.xpath("//option[.='Aquaman']")))
        .click(driver.findElement(By.xpath("//option[.='Batman']")))
        .keyUp(Keys.CONTROL).build().perform();
        hero.deselectAll();

        //multiple selection method-3
        Thread.sleep(3000);
        action.clickAndHold(driver.findElement(By.xpath("//option[.='Ant-Man']")))
        .moveToElement(driver.findElement(By.xpath("//option[.='Batman']"))).release().build().perform();

    }
}
