package testNG.listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListener implements ITestListener
{
    public void onTestStart(ITestResult result) 
    {
        System.out.println("Its getting started"+ result.getName());
    }


    public void onTestSuccess(ITestResult result) 
    {
        System.out.println("Completed successfully"+ result.getStatus());
    }


   public void onTestFailure(ITestResult result) 
    {
        System.out.println("Failed as expected"+ result.wasRetried());
    }


   public void onTestSkipped(ITestResult result) 
    {
         System.out.println("Skipped as expected"+result.isSuccess());
    }


   public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
    {
        
    }


   public void onTestFailedWithTimeout(ITestResult result) 
    {
        onTestFailure(result);
    }


   public void onStart(ITestContext context) 
    {
        System.out.println("Starting test execution for DemoTest");
    }


   public void onFinish(ITestContext context) 
    {
         System.out.println("Finishing test execution for DemoTest");
    }
}
