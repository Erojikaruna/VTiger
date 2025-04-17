package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewOrganization {
	    //Declaration
		@FindBy(xpath="//span[text()=Creating New Organization']")
		private WebElement header;
		
		@FindBy(name="accountname")
		private WebElement Orgname_TF;
		
		@FindBy(name="industry")
		private WebElement OrgIndustryDD;
		
		@FindBy(name="accounttype")
		private WebElement OrgTypeDD;

		@FindBy(id="phone")
		private WebElement Orgphone_TF;
		
		@FindBy(xpath="//input[@title='Save [Alt+S]']")
		private WebElement saveBtn;
		
		
		//Initialize
		public CreateNewOrganization(WebDriver driver) {
			PageFactory.initElements(driver,this);
		}
		//Utilization

		public String getHeader() {
			return header.getText();
		}

		public void getOrgname_TF(String orgname) {
			 Orgname_TF.sendKeys(orgname);;
		}

		public WebElement getOrgIndustryDD() {
			return OrgIndustryDD;
		}

		public WebElement getOrgTypeDD() {
			return OrgTypeDD;
		}

		public void getOrgphone_TF(String orgphno) {
			 Orgphone_TF.sendKeys(orgphno);;
		}

		public void getSaveBtn() {
			 saveBtn.click();
		}
	
}
