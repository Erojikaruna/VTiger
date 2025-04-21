package Contacts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClassUtility.Baseclass;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.Property_Utility;
import GenericUtilities.WebDriver_Utility;
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

public class CreateContactWithOrgnameTest extends Baseclass {

	// @Parameters("browser")
	@Test(groups = "regression")
	public void CreateContact() throws IOException, InterruptedException {

		// Fetch data from property file
//		Property_Utility pro = new Property_Utility();
//		//String browser = (pro.FetchDataFromPropertyFile("Browser"));
//		String url = (pro.FetchDataFromPropertyFile("url"));
//		String username = (pro.FetchDataFromPropertyFile("username"));
//		String pwd = (pro.FetchDataFromPropertyFile("password"));
//		String timeouts = (pro.FetchDataFromPropertyFile("timeouts"));

		WebDriver_Utility w_util = new WebDriver_Utility();
		// FETCH data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String lastname = excel_util.FetchDataFromExcelFile("Contact", 9, 3) + random;
		String orgname = excel_util.FetchDataFromExcelFile("Contact", 9, 4) + random;

		// WebDriver driver = null;
//		if (browser.equals("chrome")) {
//			driver = new ChromeDriver();
//		} else if (browser.equals("edge")) {
//			driver = new EdgeDriver();
//		}

		// maximize the window
		// w_util.maximizeTheWindow(driver);

		// Implicitly wait
		// w_util.waitTillElementFound(timeouts, driver);

		// navigate to vtiger appln
		// w_util.navigateToAnAppl(driver, url);

		// Login to vtiger appln
		// LoginPomPage l = new LoginPomPage(driver);
		// l.login(username, pwd);

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
		// driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();

		// verify actual org name with expected org name
		// WebElement header = driver.findElement(By.xpath("//span[contains(text(),'
		// Organization Information')]"));
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);

		boolean exp_res1 = orgdetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res1, true);

//		if (header.contains(orgname)) {
//			Reporter.log(orgname + "Test pass",true);
//		} else {
//			Reporter.log("org not created",true);
//		}

		// Identigy contacts tab in home page and click on it
		// driver.findElement(By.linkText("Contacts")).click();
		home.getCont_tab();

		// Identify plus button and click on it
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// driver.findElement(By.name("lastname")).sendKeys(lastname);
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);

		// select org name
		String pwid = driver.getWindowHandle();
		// driver.findElement(By.xpath("//img[@alt='Select']")).click();
		newcon.getOrgplusicon();

		w_util.switchToTabUsingUrl(driver, "module=Accounts&action");

		// driver.findElement(By.id("search_txt")).sendKeys(orgname);
		// driver.findElement(By.name("search")).click();
		newcon.getOrgsearchTF(orgname);
		newcon.getOrgsearchBtn();
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		w_util.switchBackToParentWindow(driver, pwid);

		// driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();
		newcon.getSaveBtn();

		// verify actual org name with expected org name
		// WebElement header1 = driver.findElement(By.xpath("//span[contains(text(),'
		// Contact Information')]"));
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);

		boolean exp_res_con = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res_con, true);

//		if (conheader.contains(lastname)) {
//			Reporter.log(lastname + "Test pass",true);
//		} else {
//			Reporter.log("lname not created",true);
//		}

		// click on contacts tab and delete the created the contact
		// driver.findElement(By.linkText("Contacts")).click();
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);
		// Handle the popup
		// driver.switchTo().alert().accept();
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

		// Logout of the appln
		// WebElement admin =
		// driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
//		WebElement admin = home.getAdmin_icon();
//
//		w_util.Action_MouseHovering(driver, admin);
//
//		//driver.findElement(By.linkText("Sign Out")).click();
//		home.getSignout();
//
//		// close the browser
//		w_util.QuitTheBrowser(driver);

	}
}
