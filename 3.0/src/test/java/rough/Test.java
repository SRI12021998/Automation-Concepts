package rough;

import org.openqa.selenium.Dimension;
import java.awt.Toolkit;

public class Test 
{
    public static void main(String[] args) 
    {
        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Screen Width: " + screenSize.width);
        System.out.println("Screen Height: " + screenSize.height);
    }
}
