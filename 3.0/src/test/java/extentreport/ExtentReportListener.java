package extentreport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.testng.ITestListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportListener implements ITestListener
{
    ExtentReports report;
    ExtentTest test;

    @Override
    public void onStart(org.testng.ITestContext context) 
    {
        ExtentSparkReporter spark = new ExtentSparkReporter("extentReport.html");//
        report = new ExtentReports();
        report.attachReporter(spark);
    }

    @Override
    public void onTestStart(org.testng.ITestResult result) 
    {
        test=report.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(org.testng.ITestResult result) 
    {
        test.log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(org.testng.ITestResult result) 
    {
        test.log(Status.FAIL, "Test failed");
    }

    @Override
    public void onTestSkipped(org.testng.ITestResult result) 
    {
        test.skip("Test skipped");
    }

    @Override
    public void onFinish(org.testng.ITestContext context) 
    {
        report.flush();
        try {
			Desktop.getDesktop().browse(new File("extentReport.html").toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
