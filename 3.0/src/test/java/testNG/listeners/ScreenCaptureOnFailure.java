package testNG.listeners;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ScreenCaptureOnFailure implements ITestListener
{
    @Override
    public void onTestFailure(ITestResult result) 
    {
        try 
        {
            Rectangle rec=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Robot robot=new Robot();
            BufferedImage src= robot.createScreenCapture(rec);
            ImageIO.write(src, "png", new File("D:/VS Local repo/evidence/"+System.currentTimeMillis()+".png"));
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
