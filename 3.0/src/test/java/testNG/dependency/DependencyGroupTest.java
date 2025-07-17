package testNG.dependency;

import org.testng.annotations.Test;

public class DependencyGroupTest 
{
     @Test(enabled = true, groups = {"smoke"})
    public void grnCreate()
    {
        System.out.println("Grn created successfully");
    }

    @Test(dependsOnGroups={"smoke"})//waits until all methods in the "smoke" group are executed
    public void grnAccept()
    {
        System.out.println("Grn accepted successfully");
    }

    @Test(groups = {"smoke"})
    public void salesOrderCreation()
    {
        System.out.println("Sales order created successfully");
    }

    @Test(groups = {"smoke"})
    public void autoStockAllocation()
    {
        System.out.println("Autostock allocation completed successfully");
    }
}
