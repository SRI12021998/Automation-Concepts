package testNG.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class MyRetryAnalyzer implements IRetryAnalyzer
{
    int count=0;
    int maxCount=4;
    @Override
    public boolean retry(ITestResult result) 
    {
        if(count<maxCount)
        {
            count++;
            return true;
        }
        return false;
    }

}
