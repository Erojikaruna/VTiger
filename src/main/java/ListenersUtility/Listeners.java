package ListenersUtility;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import BaseClassUtility.Baseclass;
import GenericUtilities.ClassObject_Utility;
import GenericUtilities.Java_Utility;

public class Listeners implements ITestListener, ISuiteListener {

	public ExtentReports report = null;
	public ExtentTest test = null;

	@Override
	public void onStart(ISuite suite) {
		Reporter.log("Report configuration", true);
		String time = new Date().toString().replace(" ", "_").replace(":", "_");

		// Configure the project
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReports/Repots" +time+ ".html");
		spark.config().setDocumentTitle("demo test");
		spark.config().setReportName("Demo Report");
		spark.config().setTheme(Theme.DARK);

		// Add Environment configurations
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("Os version", "Windows-11");
		report.setSystemInfo("Browser version", "chrome-135");
		test = report.createTest("VTiger Runtime Events");
		ClassObject_Utility.setTest(test);

	}

	@Override
	public void onFinish(ISuite suite) {
		Reporter.log("Report Backup", true);
		report.flush();
		test.log(Status.INFO, "Suite Execution Finished");
	}

	@Override
	public void onTestStart(ITestResult result) {

		Reporter.log(result.getMethod().getMethodName() + "--Started--", true);
		test = report.createTest(result.getMethod().getMethodName());
		test.log(Status.INFO, result.getMethod().getMethodName() + "--started--");
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		Reporter.log(result.getMethod().getMethodName() + "--Success--", true);
		// test = report.createTest(result.getMethod().getMethodName());
		test.log(Status.PASS, result.getMethod().getMethodName() + "--success--");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + "--Failed--", true);
		test.log(Status.FAIL, testname + "--Failed--");

		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		TakesScreenshot ts = (TakesScreenshot) Baseclass.sdriver;
		String filepath = ts.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(filepath, testname + "_" + time);

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Reporter.log(result.getMethod().getMethodName() + "--Skipped--", true);
		test.log(Status.INFO, result.getMethod().getMethodName() + "--Skipped--");
	}

}
