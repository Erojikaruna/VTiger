
package Organization;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
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
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.OrgDetailPomPage;
import POM_Pages.OrganizationPomPage;
@Listeners(ListenersUtility.Listeners.class)
public class createOrganizationScenarios extends Baseclass {

	@Test(groups = "smoke")

	public void createorganization() throws IOException, InterruptedException {

		WebDriver_Utility w_util = new WebDriver_Utility();
		
		ClassObject_Utility.getTest().log(Status.INFO, "Fetching the Data From Excel File");
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = excel_util.FetchDataFromExcelFile("Organization", 1, 3) + random;

		ClassObject_Utility.getTest().log(Status.INFO, "Verifying home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);

		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org page");
		home.getOrg_tab();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to Create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		ClassObject_Utility.getTest().log(Status.INFO, "Create new org");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getSaveBtn();

		// verify actual org name with expected org name
		ClassObject_Utility.getTest().log(Status.INFO, "Verify org name");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getVerifyOrgname().contains(orgname);

		Assert.assertEquals(exp_res_org, true);

		// click on org tab and delete the created the ornanization
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org tab and delete");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// driver.switchTo().alert().accept();
		Thread.sleep(2000);
		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup Handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();
	}
	
	@Test(groups = "regression")

	public void createorgaWithIndustry() throws IOException, InterruptedException {

		
		// FETCH data from Excel
		ClassObject_Utility.getTest().log(Status.INFO, "Fetch Data from Excel file");
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = excel_util.FetchDataFromExcelFile("Organization", 10, 3) + random;
		String Industry = excel_util.FetchDataFromExcelFile("Organization", 10, 4);
		String Type = excel_util.FetchDataFromExcelFile("Organization", 10, 5);


		WebDriver_Utility w_util = new WebDriver_Utility();

		
		// Identigy organization tab in home page and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Verify Home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org page");
		home.getOrg_tab();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save
		ClassObject_Utility.getTest().log(Status.INFO, "Create new org with industry and type ");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		WebElement ele = neworg.getOrgIndustryDD();// .findElement(By.name("industry"));
		WebElement Type1 = neworg.getOrgTypeDD();// .findElement(By.name("accounttype"));
		w_util.HandleDropdownUsingValue(ele, Industry);
		w_util.HandleDropdownUsingValue(Type1, Type);
		neworg.getSaveBtn();

		// verify actual org name with expected org name
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying org name");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res1 = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res1, true);
		
		// verify actual industry name with expected industry name
		ClassObject_Utility.getTest().log(Status.INFO, "Verify industry ");
		boolean exp_actind = orgdetail.getVerifyIndustry().contains(Industry);
		Assert.assertEquals(exp_actind, true);
		
		// verify actual type with expected type
		ClassObject_Utility.getTest().log(Status.INFO, "Verify Type");
		boolean exp_acttype = orgdetail.getVerifyType().contains(Type);
		Assert.assertEquals(exp_acttype, true);
		
	// click on org tab and delete the created the ornanization
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org tab and delete");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='" + orgname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
		Thread.sleep(2000);
		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

}
	@Test(groups = "reg")
	public void createorganizationwithphnno() throws EncryptedDocumentException, IOException, InterruptedException {

		ClassObject_Utility.getTest().log(Status.INFO, "Fetching the Data from Excel file");
		// FETCH data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String orgname = excel_util.FetchDataFromExcelFile("Organization", 6, 3) + random;
		String phone = excel_util.FetchDataFromExcelFile("Organization", 6, 4);

		WebDriver_Utility w_util = new WebDriver_Utility();

		
		// Identigy organization tab in home page and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "Verifying home page");
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org tab");
		home.getOrg_tab();

		// Identify plus button and click on it
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to create new org page");
		OrganizationPomPage org = new OrganizationPomPage(driver);
		org.getPlusicon();

		// Enter org name in create new org name page and save

		ClassObject_Utility.getTest().log(Status.INFO, "Creating new org with phn no");
		CreateNewOrganization neworg = new CreateNewOrganization(driver);
		neworg.getOrgname_TF(orgname);
		neworg.getOrgphone_TF(phone);
		neworg.getSaveBtn();

		ClassObject_Utility.getTest().log(Status.INFO, "verify org name and phn no");
		OrgDetailPomPage orgdetail = new OrgDetailPomPage(driver);
		boolean exp_res_org = orgdetail.getHeader().contains(orgname);
		Assert.assertEquals(exp_res_org, true);
	
		boolean exp_res_phnno =orgdetail.getVerigyOrgPhno().contains(phone);
		Assert.assertEquals(exp_res_phnno,true);
		
		
		// click on org tab and delete the created the ornanization
		ClassObject_Utility.getTest().log(Status.INFO, "navigate to org tab and delete org");
		home.getOrg_tab();

		driver.findElement(
				By.xpath("//a[text()='"+orgname+"']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
		Thread.sleep(2000);
		ClassObject_Utility.getTest().log(Status.INFO, "Delete popup handled");
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();
	}
}
