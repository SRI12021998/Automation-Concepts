package extentreport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LogLevels {

	@Test
	public void logCheck() throws IOException 
	{
		ExtentReports report=new ExtentReports();
		ExtentSparkReporter spark=new ExtentSparkReporter("New Report.html");
		report.attachReporter(spark);
		
		ExtentTest login=report.createTest("Login as dispatch user");
		ExtentTest openSalesOrderMenu=report.createTest("Open sales order menu");//default log is pass
		ExtentTest createSalesOrder=report.createTest("Create sales order");
		ExtentTest logout=report.createTest("Logout as dispatch user");
		
//		login.pass("Logged in successfully");//priority -4
//		login.info("Took 10 secs time to login");//priority -5
//		login.fail("Not able to login");//priority -1
//		login.warning("Took too much time to login");//priority -3
		login.skip("Case is skipped");//priority -2
		
		report.flush();
		Desktop.getDesktop().browse(new File("New Report.html").toURI());
	}

}
