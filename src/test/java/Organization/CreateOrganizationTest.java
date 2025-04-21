package Organization;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
//import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClassUtility.Baseclass;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.Property_Utility;
import GenericUtilities.WebDriver_Utility;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

public class CreateOrganizationTest extends Baseclass {
	//@Parameters("browser")
	@Test(groups = "smoke", retryAnalyzer = ListenersUtility.RetryAnalyser_Utility.class)

	public void createorganization() throws IOException, InterruptedException {

		/*
		 * // Fetch DATA FROM PROPERTY FILE FileInputStream pfis = new
		 * FileInputStream("./src/test/resources/CommonData.properties"); // Create an
		 * object of properties Properties p = new Properties(); p.load(pfis);
		 * 
		 * String Browser = (p.getProperty("Browser")); String url =
		 * (p.getProperty("url")); String username = (p.getProperty("username")); String
		 * pwd = (p.getProperty("password")); String time=(p.getProperty("timeouts"));
		 */

		// Fetch data from property file
//		Property_Utility pro = new Property_Utility();
//		//String Browser = (pro.FetchDataFromPropertyFile("Browser"));
//		String url = (pro.FetchDataFromPropertyFile("url"));
//		String username = (pro.FetchDataFromPropertyFile("username"));
//		String pwd = (pro.FetchDataFromPropertyFile("password"));
//		String timeouts = (pro.FetchDataFromPropertyFile("timeouts"));
//		long time = Long.parseLong(timeouts);

		/*
		 * // FETCH data from Excel FileInputStream efis = new
		 * FileInputStream("./src/test/resources/Oranization.xlsx");
		 * 
		 * Workbook wb = WorkbookFactory.create(efis); Sheet sh =
		 * wb.getSheet("organization"); Row r = sh.getRow(1); Cell c = r.getCell(3);
		 * String orgname = c.getStringCellValue();
		 */
		WebDriver_Utility w_util = new WebDriver_Utility();

		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();

		String orgname = excel_util.FetchDataFromExcelFile("Organization", 1, 3) + random;

		// Launch the browser
//		WebDriver driver = null;
//		if (Browser.equals("chrome")) {
//			driver = new ChromeDriver();
//		}
//		if (Browser.equals("edge")) {
//			driver = new EdgeDriver();
//		}

		
		// maximize the window
		//w_util.maximizeTheWindow(driver);

		// Implicitly wait
		//w_util.waitTillElementFound(timeouts, driver);

		// navigate to vtiger appln
		//w_util.navigateToAnAppl(driver, url);
		
		//Login to Vtiger appln
		//LoginPomPage l = new LoginPomPage(driver);
		//l.login(username, pwd);

		// Identify user name text field
		//driver.findElement(By.name("user_name")).sendKeys(username);

		// Identify PSWD textfield and pass the text
		//driver.findElement(By.name("user_password")).sendKeys(pwd);

		// Identify login button and click on it
		//driver.findElement(By.id("submitButton")).click();

		// Identigy organization tab in home page and click on it
		//driver.findElement(By.linkText("Organizations")).click();
		
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		home.getOrg_tab();

		// Identify plus button and click on it
		//driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save
		// String orgname = "cat";
		//driver.findElement(By.name("accountname")).sendKeys(orgname);
		//driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();

		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();
		
		// verify actual org name with expected org name
		//WebElement header = driver.findElement(By.xpath("//span[contains(text(),' Organization Information')]"));
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getVerifyOrgname().contains(orgname);
		
		Assert.assertEquals(exp_res_org,true);
		
//		if (header.contains(orgname)) {
//			Reporter.log(orgname + "Test pass",true);
//		} else {
//			Reporter.log("org not created",true);
//		}

		// click on org tab and delete the created the ornanization
		//driver.findElement(By.linkText("Organizations")).click();
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='"+ orgname +"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// driver.switchTo().alert().accept();
		Thread.sleep(2000);
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

		// Logout of the appln
//		WebElement admin = home.getAdmin_icon();//driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//
//		w_util.Action_MouseHovering(driver, admin);
//
//		//driver.findElement(By.linkText("Sign Out")).click();
//		home.getSignout();
//
//		// close the browser
//		w_util.CloseTheBrowser(driver);

	}
}
