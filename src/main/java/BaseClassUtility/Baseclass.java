package BaseClassUtility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import GenericUtilities.Database_Utility;
import GenericUtilities.Property_Utility;
import GenericUtilities.WebDriver_Utility;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;

public class Baseclass {
	Database_Utility db = new Database_Utility();
	Property_Utility pro = new Property_Utility();
	WebDriver_Utility w_util = new WebDriver_Utility();
	public WebDriver driver = null;
	
	public static WebDriver sdriver = null;

	@BeforeSuite
	public void beforeSuite() {
		Reporter.log("Configure the DB:Connect", true);
		db.getDatabaseConnection();
	}

	@BeforeTest
	public void beforeTest() {
		Reporter.log("BT:Parallel Exe", true);
	}

	@BeforeClass
	public void beforeClass() throws IOException {
		
		Reporter.log("Launch the browser", true);
		
		String Browser = pro.FetchDataFromPropertyFile("Browser");
		
		if (Browser.equals("chrome")) {
			driver = new ChromeDriver();
		}
		if (Browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		
		sdriver = driver;
	}

	@BeforeMethod
	public void beforeMethod() throws IOException {
		Reporter.log("Login to the appln", true);
		
		String url = (pro.FetchDataFromPropertyFile("url"));
		String username = (pro.FetchDataFromPropertyFile("username"));
		String pwd = (pro.FetchDataFromPropertyFile("password"));
		LoginPomPage l = new LoginPomPage(driver);
		w_util.navigateToAnAppl(driver, url);
		w_util.maximizeTheWindow(driver);
		l.login(username, pwd);
		String timeouts = (pro.FetchDataFromPropertyFile("timeouts"));
		w_util.waitTillElementFound(timeouts, driver);
		
	}

	@AfterMethod
	public void afterMethod() {
		Reporter.log("Logout to the appln", true);
		HomePomPage home = new HomePomPage(driver);
		home.logout(driver);
	}

	@AfterClass
	public void afterClass() {
		Reporter.log("close the browser", true);
		WebDriver_Utility wb = new WebDriver_Utility();
		wb.QuitTheBrowser(driver);
	}

	@AfterTest
	public void afterTest() {
		Reporter.log("AT:Parallel Exe", true);
	}

	@AfterSuite
	public void afterSuite() {
		Reporter.log("Close the DB Connection", true);
		db.closeDatabaseConnection();
	}

}
