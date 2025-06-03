package testNG.dependency;

import org.testng.annotations.Test;

public class DependencyTest 
{
    @Test(enabled = false)
    public void grnCreate()
    {
        System.out.println("Grn created successfully");
    }

    @Test(dependsOnMethods="grnCreate")
    public void grnAccept()
    {
        System.out.println("Grn accepted successfully");
    }

    @Test(dependsOnMethods="grnAccept")
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
