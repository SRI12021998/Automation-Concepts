package testNG.dispatchregression;

import org.testng.annotations.Test;

public class GRNLoad 
{
    @Test(dependsOnGroups = "A", alwaysRun = false)
    public void loadGRN()
    {
        System.out.println("GRN load created");
    }
}
