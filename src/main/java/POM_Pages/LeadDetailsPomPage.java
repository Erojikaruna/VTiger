package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadDetailsPomPage {

	//Declare
		@FindBy(xpath = "//span[contains(text(),'Lead Information')]")
		private WebElement header;
		
		@FindBy(id = "mouseArea_Last Name")
		private WebElement verifyLastname;
		
		@FindBy(id = "mouseArea_Company")
		private WebElement verifyCompany;
		
		
		
		//Initialization
		public LeadDetailsPomPage(WebDriver driver) {
			PageFactory.initElements(driver, this);	
		}
		
		//Utilization
		public String getHeader() {
			return header.getText();
		}
		
		public String getVerifyLastname() {
			return verifyLastname.getText();
		}
		
		public String getVerigyCompany() {
			return verifyCompany.getText();
		}
		
		
}
