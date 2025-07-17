package testNG.dependency;

import org.testng.annotations.Test;

public class DependencyMethodTest 
{
    @Test(enabled = true)
    public void grnCreate()
    {
        System.out.println("Grn created successfully");
    }

    @Test(dependsOnMethods="grnCreate")
    public void grnAccept()
    {
        // throw new RuntimeException("Grn acceptance failed");
        System.out.println("Grn accepted successfully");
    }

    @Test(dependsOnMethods={ "grnCreate"})
    public void salesOrderCreation()
    {
        System.out.println("Sales order created successfully");
    }

    @Test(dependsOnMethods="salesOrderCreation")
    public void autoStockAllocation()
    {
        System.out.println("Autostock allocation completed successfully");
    }
}
