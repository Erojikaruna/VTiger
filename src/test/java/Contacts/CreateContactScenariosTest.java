package Contacts;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import BaseClassUtility.Baseclass;
import GenericUtilities.ClassObject_Utility;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.WebDriver_Utility;
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;

//@Listeners(ListenersUtility.Listeners.class)
public class CreateContactScenariosTest extends Baseclass {

	@Test(groups = "smoke") //retryAnalyzer = ListenersUtility.RetryAnalyser_Utility.class)
	public void CreateContact() throws IOException {

		WebDriver_Utility w_util = new WebDriver_Utility();
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel File");
		
		// Fetch data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String lastname = excel_util.FetchDataFromExcelFile("Contact", 1, 3) + random;

		// Identigy contacts tab in home page and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		//Identify contact tab in home page
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to contact tab");
		home.getCont_tab();
		// driver.findElement(By.linkText("Contacts")).click();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new contact plus icon");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// Enter lastname in create contact page
		ClassObject_Utility.getTest().log(Status.INFO, "Creating New contact");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);
		newcon.getSaveBtn();

		//verify actual contact name with expected contact name
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying the contact");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		//String header = condetail.getHeader();
		boolean exp_res1 = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res1,true);

		//click on contact tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to contact tab and delete the contact");
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='"+lastname+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popup");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}
	
	@Test(groups = "regression", retryAnalyzer = ListenersUtility.RetryAnalyser_Utility.class)
	public void CreateContactwithorg() throws IOException, InterruptedException {


		WebDriver_Utility w_util = new WebDriver_Utility();
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel File");
		// FETCH data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String lastname = excel_util.FetchDataFromExcelFile("Contact", 9, 3) + random;
		String orgname = excel_util.FetchDataFromExcelFile("Contact", 9, 4) + random;

		
		// Identigy organization tab in home page and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		//Identify org tab and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to organization tab");
		home.getOrg_tab();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save
		ClassObject_Utility.getTest().log(Status.INFO, "Create new org");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();

		// verify actual org name with expected org name
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying the org");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res1 = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res1, true);

        //Click on Contact tab
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to contact tab");
		home.getCont_tab();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to create new contat plus icon");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// driver.findElement(By.name("lastname")).sendKeys(lastname);
		ClassObject_Utility.getTest().log(Status.INFO, "Create new contact with org name");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);

		// select org name
		ClassObject_Utility.getTest().log(Status.INFO, "Select org name from child window");
		String pwid = driver.getWindowHandle();
		newcon.getOrgplusicon();

		w_util.switchToTabUsingUrl(driver, "module=Accounts&action");

		newcon.getOrgsearchTF(orgname);
		newcon.getOrgsearchBtn();
		driver.findElement(By.xpath("//a[text()='" + orgname + "']")).click();

		w_util.switchBackToParentWindow(driver, pwid);

		newcon.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying contact name with org name");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
        boolean exp_res_con = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res_con, true);

		ClassObject_Utility.getTest().log(Status.INFO, "navigate to contact tab and delete the contact");
		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);
		ClassObject_Utility.getTest().log(Status.INFO, "Handling delete popup");
		w_util.HandleAlertAndAccept(driver);
	
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org tab and delete org");
		home.getOrg_tab();
		soft.assertAll();
	}
	
	@Test(groups ="regression", retryAnalyzer = ListenersUtility.RetryAnalyser_Utility.class)
	public void CreateContactwithsupportdate() throws IOException, InterruptedException {

		WebDriver_Utility w_util = new WebDriver_Utility();
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch data from Excel file");
		
		// Fetch the data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String lastname = excel_util.FetchDataFromExcelFile("Contact", 9, 3) + random;

		// Identigy contacts tab in home page and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Home page verification");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to contact tab");
		home.getCont_tab();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Identify create new contact plus icon");
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		//driver.findElement(By.name("lastname")).sendKeys(lastname);
		ClassObject_Utility.getTest().log(Status.INFO, "Create new conatct");
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);

		// specify start and end support date
		ClassObject_Utility.getTest().log(Status.INFO, "Identify date");
		WebElement start_dateTF = newcon.getConStartDate_TF();
		start_dateTF.clear();
		String startdate = j_util.getCurrentDate();
		start_dateTF.sendKeys(startdate);

		WebElement end_dateTF = newcon.getConEndDate_TF();
		end_dateTF.clear();
		String enddate = j_util.getDateAftergivendays(30);
		end_dateTF.sendKeys(enddate);
		
		newcon.getSaveBtn();

		

		// verify actual org name with expected org name
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying the org");
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
	
		boolean exp_res_con = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res_con,true);
		
		
		// Verify start sup date and end support date
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying the date");
		boolean exp_actstartdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(exp_actstartdate, true);
		

		boolean exp_actenddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(exp_actenddate, true);
		

		// click on org tab and delete the created org
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to org and delete the org");
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(
				By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
//		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		ClassObject_Utility.getTest().log(Status.INFO, "Handle the popup");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();
	}
}
