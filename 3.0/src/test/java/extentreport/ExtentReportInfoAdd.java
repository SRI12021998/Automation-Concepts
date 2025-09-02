package extentreport;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.imageio.ImageIO;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ExtentReportInfoAdd 
{
	ExtentReports reports;
	ExtentSparkReporter spark;
	WebDriver drive=null;
	ThreadLocal <WebDriver> driver=new ThreadLocal<>();
	Robot robot=null;
	TakesScreenshot tk=null;
	
	public WebDriver getDriver()
	{
		return driver.get();
	}
	
	@BeforeTest
	public void initialize()
	{
		this.drive=new ChromeDriver();
		this.driver.set(this.drive);
		try {
			robot=new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		this.reports=new ExtentReports();
		this.spark=new ExtentSparkReporter("InfoExtentReport.html");
		try {
			spark.loadJSONConfig(new File("./src/main/resources/spark-config.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reports.attachReporter(spark);
		Capabilities capabilities=((RemoteWebDriver)getDriver()).getCapabilities();
		reports.setSystemInfo("os", System.getProperty("os.name"));
		reports.setSystemInfo("java version", System.getProperty("java.version"));
		reports.setSystemInfo("browser", capabilities.getBrowserName());
		reports.setSystemInfo("browser", capabilities.getBrowserVersion());
		
		
		tk=(TakesScreenshot)getDriver();
	}
	
	@Test
	public void testBlockItalic() 
	{
		
		
		ExtentTest test= reports.createTest("Login check", "To verify user able to login");
		test.assignAuthor("Sriram");
		test.assignCategory("Smoke");
		test.assignDevice("Chrome");
		test.pass("<b>This will be shown in block letters</b>");
		
		ExtentTest test2= reports.createTest("Home page check", "To verify home page is loaded");
		test2.assignAuthor("Divya");
		test2.assignCategory("Regression");
		test2.assignDevice("Firfox");
		test2.pass("<i>This will be shown in italic letters</>");
		test.info(MarkupHelper.createLabel("Highlight and show", ExtentColor.PURPLE));
		
	}
	
	@Test
	public void testXml()
	{
		String temp="<LugarRecep>\r\n"
				+ "	<Calle>CALLE 24 NO 9 56,BARRIO OBRERO,VALLE DEL CAUCA</Calle>\r\n"
				+ "	<Departamento>76</Departamento>\r\n"
				+ "	<Ciudad>76863</Ciudad>\r\n"
				+ "	<Pais>CO</Pais>\r\n"
				+ "	<CodigoPostal>000000</CodigoPostal>\r\n"
				+ "</LugarRecep>";
		ExtentTest test3=reports.createTest("Xml check", "TO verify xml data is loaded");
		test3.assignAuthor("Santo");
		test3.assignCategory("Functional");
		test3.assignDevice("Safari");
//		test3.fail("<pre>" + temp + "</pre>");
		test3.fail(MarkupHelper.createCodeBlock(temp, CodeLanguage.XML));
	}
	
	@Test
	public void testJson()
	{
		String temp="{\r\n"
				+ "    \"Master\": \"StockInHandMaster\",\r\n"
				+ "    \"Field\": [\r\n"
				+ "        \"pid\",\r\n"
				+ "        \"batchid\",\r\n"
				+ "        \"qty\"\r\n"
				+ "    ],\r\n"
				+ "    \"Data\": [\r\n"
				+ "        [\r\n"
				+ "            12889,\r\n"
				+ "            0,\r\n"
				+ "            1000\r\n"
				+ "        ],\r\n"
				+ "		[\r\n"
				+ "            12240,\r\n"
				+ "            0,\r\n"
				+ "            10859\r\n"
				+ "        ]\r\n"
				+ "    ],\r\n"
				+ "    \"ErrorCode\": \"0\",\r\n"
				+ "    \"Next\": \"0\"\r\n"
				+ "}";
		ExtentTest test4=reports.createTest("Json check", "To verify Mobile Json is loaded");
//		test4.skip(temp);
		test4.skip(MarkupHelper.createCodeBlock(temp, CodeLanguage.JSON));
	}
	
	@Test
	public void testListData()
	{
		ArrayList <Integer> age=new ArrayList<>();
		age.add(26);
		age.add(27);
		age.add(51);
		age.add(58);
		reports.createTest("Age list check","To verify age list is loaded")
		.info(MarkupHelper.createOrderedList(age));
	}
	
	@Test
	public void testSetData()
	{
		HashSet <String> names=new HashSet<>();
		names.add("Divya");
		names.add("Sriram");
		names.add("Balaji");
		names.add("Punitha");
		reports.createTest("Name set check","To verify name set is loaded")
		.info(MarkupHelper.createUnorderedList(names));
	}
	
	@Test
	public void testMapData()
	{
		HashMap<String,Character> occupation=new HashMap<>();
		occupation.put("Sriram", 'M');
		occupation.put("Divya", 'F');
		occupation.put("Balaji", 'M');
		occupation.put("Punitha", 'F');
		reports.createTest("Occupation map check","To verify occupation and gender map is loaded")
		.assignAuthor(new String[] {"Bala", "Sriram", "DP"})
		.assignCategory(new String[] {"Smoke", "Exploratory"})
		.info(MarkupHelper.createOrderedList(occupation));
	}
	
	@Test
	public void testException()
	{
		try {
			int a=1/0;
		}
		catch(Exception e)
		{
			reports.createTest("Exception check", "To check the exception occurred")
			.fail(e);
		}
	}
	
	@Test
	public void testSSCapture()
	{
		getDriver().get("https://bimbo-co-01-qa.ivycpg.com/web/DMS/Login/Index");
		getDriver().findElement(By.id("UserName")).sendKeys("L1_User");
		getDriver().findElement(By.name("Password")).sendKeys("1");
		getDriver().findElement(By.id("Login")).click();
		
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rec=new Rectangle(d.getSize());
		BufferedImage img=robot.createScreenCapture(rec);
		try {
			ImageIO.write(img, "png", new File ("robotimg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reports.createTest("Screenshot Robot check", "To check SSCapture after logged in")
		.pass(MediaEntityBuilder.createScreenCaptureFromPath("robotimg.png","This is a title of image").build());
		
		String base64=tk.getScreenshotAs(OutputType.BASE64);
		File src=tk.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("tssimg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		reports.createTest("Screenshot TakesScreenshot check", "To check SSCapture after logged in")
		.warning(MediaEntityBuilder.createScreenCaptureFromBase64String(base64).build());
		
		reports.createTest("Screenshot in test level",  "To check test level SSCapture after logged in")
		.addScreenCaptureFromPath("tssimg.png","Ithanda screenshot")
		.skip("Skipped internally");
	}
	
	@AfterSuite
	public void generate()
	{
		reports.flush();

		try {
			Desktop.getDesktop().browse(new File("InfoExtentReport.html").toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(getDriver()!=null)
		{
			getDriver().quit();
		}
	}
	
}
