package Contacts;

import java.io.IOException;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClassUtility.Baseclass;
import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;

import GenericUtilities.WebDriver_Utility;
import POM_Pages.ContactDetailPomPage;
import POM_Pages.ContactPomPage;
import POM_Pages.CreateNewContactPomPage;
import POM_Pages.HomePomPage;

//@Listeners(ListenersUtility.Listeners.class)
public class createContact extends Baseclass {
	// @Parameters("browser")
	@Test(groups = "smoke")
	public void CreateContact() throws IOException {

		// Fetch data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String lastname = excel_util.FetchDataFromExcelFile("Contact", 1, 3) + random;

		WebDriver_Utility w_util = new WebDriver_Utility();

		// Identigy contacts tab in home page and click on it
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		home.getCont_tab();
		// driver.findElement(By.linkText("Contacts")).click();

		// Identify plus button and click on it
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		// Enter lastname in create contact page
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);
		newcon.getSaveBtn();

		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
		//String header = condetail.getHeader();
		boolean exp_res1 = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res1,true);

//		if (header.contains(lastname)) {
//			Reporter.log(lastname + "Test pass", true);
//		} else {
//			Reporter.log("lastname not created", true);
//		}

		// click on contacts tab and delete the created the contact

		home.getCont_tab();

		driver.findElement(
				By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup

		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();

	}

}
