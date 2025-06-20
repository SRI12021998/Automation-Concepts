package testNG.groups;

import org.testng.annotations.Test;

public class Dispatcher 
{
    @Test(groups = "functional")
     public void dispatchLogin()
    {
        System.out.println("Dispatch user logged in");
    }

    @Test(groups = "smoke")
    public void homeScreenLoad()
    {
        System.out.println("Home page loaded");
    }

    @Test(groups = {"regression", "smoke"})
    public void grnCreate()
    {
        System.out.println("Grn created successfully");
    }

    @Test(groups = {"regression"}, dependsOnMethods="salesOrderCreation")
    public void grnAccept()
    {
        System.out.println("Grn accepted successfully");
    }

    @Test(groups = {"sanity"})
    public void salesOrderCreation()
    {
        //eventhough im running only regression group through xml its running dependent test method from sanity group
        System.out.println("Sales order created successfully");
    }

    @Test(groups = {"smoke"})
    public void autoStockAllocation()
    {
        System.out.println("Autostock allocation completed successfully");
    }
}
