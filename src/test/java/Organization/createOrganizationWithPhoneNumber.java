package Organization;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;

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

public class createOrganizationWithPhoneNumber extends Baseclass{
	//@Parameters("browser")
	@Test(groups = "reg")
	public void createorganization() throws EncryptedDocumentException, IOException, InterruptedException {

		// Fetch data from property file
		//Property_Utility pro = new Property_Utility();
		//String Browser = (pro.FetchDataFromPropertyFile("Browser"));
//		String url = (pro.FetchDataFromPropertyFile("url"));
//		String username = (pro.FetchDataFromPropertyFile("username"));
//		String pwd = (pro.FetchDataFromPropertyFile("password"));
//		String timeouts = (pro.FetchDataFromPropertyFile("timeouts"));

		// FETCH data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = excel_util.FetchDataFromExcelFile("Organization", 6, 3) + random;
		String phone = excel_util.FetchDataFromExcelFile("Organization", 6, 4);

		// Launch the browser
//		WebDriver driver = null;
//		if (Browser.equals("chrome")) {
//			driver = new ChromeDriver();
//		}
//		if (Browser.equals("edge")) {
//			driver = new EdgeDriver();
//		}
		WebDriver_Utility w_util = new WebDriver_Utility();

		// maximize the window
		//w_util.maximizeTheWindow(driver);

		// Implicitly wait
		//w_util.waitTillElementFound(timeouts, driver);

		// navigate to vtiger appln
		//w_util.navigateToAnAppl(driver, url);

		// Login to Vtiger Appln
		//LoginPomPage l = new LoginPomPage(driver);
		//l.login(username, pwd);

		// Identify user name text field
		// driver.findElement(By.name("user_name")).sendKeys(username);

		// Identify PSWD textfield and pass the text
		// driver.findElement(By.name("user_password")).sendKeys(pwd);

		// Identify login button and click on it
		// driver.findElement(By.id("submitButton")).click();

		// Identigy organization tab in home page and click on it
		// driver.findElement(By.linkText("Organizations")).click();
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		home.getOrg_tab();

		// Identify plus button and click on it
		// driver.findElement(By.xpath("//img[@title='Create
		// Organization...']")).click();
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save

		// driver.findElement(By.name("accountname")).sendKeys(orgname);
		// driver.findElement(By.id("phone")).sendKeys(phone);
		// driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getOrgphone_TF(phone);
		neworg.getSaveBtn();

		// verify actual org name with expected org name
		// WebElement header = driver.findElement(By.xpath("//span[contains(text(),'
		// Organization Information')]"));
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);
		
//		if (header.contains(orgname)) {
//			Reporter.log(orgname + "Test pass",true);
//			//System.out.println(orgname + "Test pass");
//		} else {
//			Reporter.log(orgname + "org not created",true);
//			//System.out.println("org not created");
//		}

		// verify actual phn number with expected number
		// WebElement phtf = driver.findElement(By.id("mouseArea_Phone"));
		
		boolean exp_res_phnno =orgdetail.getVerigyOrgPhno().contains(phone);
		Assert.assertEquals(exp_res_phnno,true);
		
		
//		if (phtf.contains(phone)) {
//			Reporter.log(phone + "Test pass",true);
//			
//		} else {
//			Reporter.log(" phone number not created",true);
//			
//		}

		// click on org tab and delete the created the ornanization
		// driver.findElement(By.linkText("Organizations")).click();
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='"+orgname+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
		// driver.switchTo().alert().accept();
		Thread.sleep(2000);
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

		// Logout of the appln
//		WebElement admin = home.getAdmin_icon();// findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//
//		w_util.Action_MouseHovering(driver, admin);
//		home.getSignout();
//
//		// driver.findElement(By.linkText("Sign Out")).click();
//
//		// close the browser
//		w_util.CloseTheBrowser(driver);

	}
}
