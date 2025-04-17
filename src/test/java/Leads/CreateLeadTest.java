package Leads;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

import GenericUtilities.Excel_Utility;
import GenericUtilities.Java_Utility;
import GenericUtilities.Property_Utility;
import GenericUtilities.WebDriver_Utility;
import POM_Pages.CreateNewLeadPomPage;
import POM_Pages.CreateNewOrganization;
import POM_Pages.HomePomPage;
import POM_Pages.LeadDetailsPomPage;
import POM_Pages.LeadPomPage;
import POM_Pages.LoginPomPage;
import POM_Pages.OrgDetailPomPage;

public class CreateLeadTest {

	@Test
	public void createLead() throws IOException, InterruptedException {

	// Fetch data from property file
	Property_Utility pro = new Property_Utility();
	String Browser = (pro.FetchDataFromPropertyFile("Browser"));
	String url = (pro.FetchDataFromPropertyFile("url"));
	String username = (pro.FetchDataFromPropertyFile("username"));
	String pwd = (pro.FetchDataFromPropertyFile("password"));
	String timeouts = (pro.FetchDataFromPropertyFile("timeouts"));
	long time = Long.parseLong(timeouts);
	
	Excel_Utility excel_util = new Excel_Utility();
	Java_Utility j_util = new Java_Utility();
	int random = j_util.getRandomNumber();

	String lastname = excel_util.FetchDataFromExcelFile("Lead", 1, 3) + random;
	String company = excel_util.FetchDataFromExcelFile("Lead", 1, 4) + random;
	
	// Launch the browser
			WebDriver driver = null;
			if (Browser.equals("chrome")) {
				driver = new ChromeDriver();
			}
			if (Browser.equals("edge")) {
				driver = new EdgeDriver();
			}

			WebDriver_Utility w_util = new WebDriver_Utility();

			// maximize the window
			w_util.maximizeTheWindow(driver);

			// Implicitly wait
			w_util.waitTillElementFound(timeouts, driver);

			// navigate to vtiger appln
			w_util.navigateToAnAppl(driver, url);
			
			//Login to Vtiger appln
			LoginPomPage l = new LoginPomPage(driver);
			l.login(username, pwd);
			
			HomePomPage home = new HomePomPage(driver);
	        home.getLead_tab();
	        
	        LeadPomPage pg = new LeadPomPage(driver);
	        pg.getplusicon();
	        
	        CreateNewLeadPomPage newlead = new CreateNewLeadPomPage (driver);
			newlead.getlastname_TF(lastname);
			newlead.getcompany_TF(company);
			newlead.getsave_Btn();
			
			// verify actual last name with expected last name
			LeadDetailsPomPage leaddetail = new LeadDetailsPomPage(driver);
			String header = leaddetail.getVerifyLastname();
			if (header.contains(lastname)) {
				System.out.println(lastname + "Test pass");
			} else {
				System.out.println("lastname not created");
			}
			
			home.getLead_tab();

			driver.findElement(
					By.xpath("//a[text()='" + lastname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
					.click();

			// driver.switchTo().alert().accept();
			Thread.sleep(2000);
			w_util.HandleAlertAndAccept(driver);

			// Logout of the appln
			WebElement admin = home.getAdmin_icon();//driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));

			w_util.Action_MouseHovering(driver, admin);

			//driver.findElement(By.linkText("Sign Out")).click();
			home.getSignout();

			// close the browser
			w_util.CloseTheBrowser(driver);
			
	}
	
	
}
