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
//import org.openqa.selenium.interactions.Actions;
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
import POM_Pages.HomePomPage;
import POM_Pages.LoginPomPage;

public class CreateContactWithSupportDateTest extends Baseclass {
	//@Parameters("browser")
	@Test(groups ="regression")
	public void CreateContact() throws IOException, InterruptedException {

		// Fetch data from property file
//		Property_Utility pro = new Property_Utility();
//		//String browser = (pro.FetchDataFromPropertyFile("Browser"));
//		String url = (pro.FetchDataFromPropertyFile("url"));
//		String username = (pro.FetchDataFromPropertyFile("username"));
//		String pwd = (pro.FetchDataFromPropertyFile("password"));
//		String timeouts = (pro.FetchDataFromPropertyFile("timeouts"));
		
		WebDriver_Utility w_util = new WebDriver_Utility();
		// Fetch the data from Excel
		Excel_Utility excel_util = new Excel_Utility();
		Java_Utility j_util = new Java_Utility();
		int random = j_util.getRandomNumber();
		String lastname = excel_util.FetchDataFromExcelFile("Contact", 9, 3) + random;

//		WebDriver driver = null;
//		if (browser.equals("chrome")) {
//			driver = new ChromeDriver();
//		} else if (browser.equals("edge")) {
//			driver = new EdgeDriver();
//		}

		

		// maximize the window
//		w_util.maximizeTheWindow(driver);
//
//		// Implicitly wait
//		w_util.waitTillElementFound(timeouts, driver);
//
//		// navigate to vtiger appln
//		w_util.navigateToAnAppl(driver, url);
//		
//		//login to vtiger appln
//		LoginPomPage l = new LoginPomPage(driver);
//		l.login(username, pwd);

		// Identify user name text field
		//driver.findElement(By.name("user_name")).sendKeys(username);

		// Identify PSWD textfield and pass the text
		//driver.findElement(By.name("user_password")).sendKeys(pwd);

		// Identify login button and click on it
		//driver.findElement(By.id("submitButton")).click();

		// Identigy contacts tab in home page and click on it
		//driver.findElement(By.linkText("Contacts")).click();
		HomePomPage home = new HomePomPage(driver);
		boolean exp_res = home.getHeader().contains("Home");
		SoftAssert soft = new SoftAssert();
		soft.assertEquals(exp_res, true);
		
		home.getCont_tab();

		// Identify plus button and click on it
		//driver.findElement(By.xpath("//img[@src='themes/softed/images/btnL3Add.gif']")).click();
		ContactPomPage con = new ContactPomPage(driver);
		con.getPlusicon();

		//driver.findElement(By.name("lastname")).sendKeys(lastname);
		CreateNewContactPomPage newcon = new CreateNewContactPomPage(driver);
		newcon.getLastname_TF(lastname);

		// specify start and end support date
		WebElement start_dateTF = newcon.getConStartDate_TF();
		start_dateTF.clear();
		String startdate = j_util.getCurrentDate();
		start_dateTF.sendKeys(startdate);

		WebElement end_dateTF = newcon.getConEndDate_TF();
		end_dateTF.clear();
		String enddate = j_util.getDateAftergivendays(30);
		end_dateTF.sendKeys(enddate);
		
		newcon.getSaveBtn();

		//driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();

		// verify actual org name with expected org name
		//WebElement header = driver.findElement(By.xpath("//span[contains(text(),' Contact Information')]"));
		ContactDetailPomPage condetail = new ContactDetailPomPage(driver);
	
		boolean exp_res_con = condetail.getHeader().contains(lastname);
		Assert.assertEquals(exp_res_con,true);
		
		
//		if (header.contains(lastname)) {
//			Reporter.log(lastname + "Test pass",true);
//		} else {
//			Reporter.log("org not created",true);
//		}

		// Verify start sup date and end support date

		//WebElement actstartdate = driver.findElement(By.id("dtlview_Support Start Date"));
		boolean exp_actstartdate = condetail.getVerifyStartdate().contains(startdate);
		Assert.assertEquals(exp_actstartdate, true);
		
//		if (actstartdate.contains(startdate)) {
//			Reporter.log("contact created with start date",true);
//		} else {
//			Reporter.log("contact not created with start date",true);
//		}
		//WebElement actenddate = driver.findElement(By.id("dtlview_Support End Date"));
		boolean exp_actenddate = condetail.getVerifyEnddate().contains(enddate);
		Assert.assertEquals(exp_actenddate, true);
		
//		if (actenddate.contains(enddate)) {
//			System.out.println("conatct created with end date");
//		} else {
//			System.out.println("conatct not created with end date");
//		}

		// click on org tab and delete the created org
		driver.findElement(By.linkText("Contacts")).click();
		driver.findElement(
				By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		// Handle the popup
//		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		w_util.HandleAlertAndAccept(driver);
		soft.assertAll();
		
		// Logout of the appln
//		WebElement admin = home.getAdmin_icon();
//
//		//Actions act = new Actions(driver);
//		//act.moveToElement(admin).perform();
//		w_util.Action_MouseHovering(driver, admin);
//		home.getSignout();
//
//		//driver.findElement(By.linkText("Sign Out")).click();
//
//		// close the browser
//		w_util.QuitTheBrowser(driver);

	}
}

























/*
 * // convert the file into object FileInputStream fis = new
 * FileInputStream("./src/test/resources/CommonData.properties");
 * 
 * // Create an object of properties Properties p = new Properties(); // Load
 * the property object p.load(fis);
 * 
 * String browser = (p.getProperty("Browser")); String url =
 * (p.getProperty("url")); String username = (p.getProperty("username")); String
 * pwd = (p.getProperty("password")); String timeouts =
 * (p.getProperty("timeouts")); long time = Long.parseLong(timeouts);
 */

//FETCH data from Excel
/*
 * FileInputStream efis = new
 * FileInputStream("./src/test/resources/Oranization.xlsx"); Workbook wb =
 * WorkbookFactory.create(efis); Sheet sh = wb.getSheet("Contact"); Row r =
 * sh.getRow(9); Cell c = r.getCell(3); String lastname =
 * c.getStringCellValue();
 */