package testNG.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class MySuiteListener implements ISuiteListener
{
    @Override
    public void onStart(ISuite suite) 
    {
        suite.getAllMethods().forEach(method -> 
        {
            System.out.println("Method in suite: " + method.getMethodName());
        });
    }

    @Override
    public void onFinish(ISuite suite) 
    {
        suite.getAllInvokedMethods().forEach(method-> 
        {
            System.out.println(""+method.getTestMethod().getMethodName() + " was invoked"); 
        });
        System.out.println("Suite has finished: " + suite.getName());
    }
}
