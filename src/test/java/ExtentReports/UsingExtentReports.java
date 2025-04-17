package ExtentReports;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class UsingExtentReports {

	@Test
	public void demo() {
		
		//Configure the project
		ExtentSparkReporter spark =new ExtentSparkReporter("./AdvanceReport/demo.html");
		spark.config().setDocumentTitle("demo test");
		spark.config().setReportName("Demo Report");
		spark.config().setTheme(Theme.DARK);
		
		//set Environment configurations
		ExtentReports report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("Os version", "Windows-11");
		report.setSystemInfo("Browser version","chrome-135");
		ExtentTest test= report.createTest("Demo Test Created");
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://www.facebook.com");
		test.log(Status.INFO, "Navigated to Facebook appln");
		
		WebElement header = driver.findElement(By.xpath("//h2"));
		if(header.getText().contains("Facebook")) {
			test.log(Status.PASS, "Header Verified true");
			driver.findElement(By.id("email")).sendKeys("selenium");
		}
		else {
			test.log(Status.FAIL, "Header verified false");
			System.out.println("Test fail");
		}
		
		report.flush();
		
	}
}
