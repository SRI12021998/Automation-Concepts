package robot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
public class RobotDemo 
{
    public static void main(String[] args) throws AWTException, IOException 
    {
        RemoteWebDriver driver=new ChromeDriver();
        driver.get("https://www.google.com");
        Robot robot = new Robot();

        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage img=robot.createScreenCapture(screenRect);
        ImageIO.write(img, "png", new File("D:/VS Local repo/evidence/robotss-"+System.currentTimeMillis()+".png"));

        driver.quit();
        System.out.println("Screenshot taken and saved successfully.");
    }

}
