package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewLeadPomPage {
	
    //Declaration
	@FindBy(xpath="//span[text()='Creating New Lead']")
	private WebElement header;
	
	@FindBy(name="lastname")
	private WebElement lastname_TF;
	
	@FindBy(name="company")
	private WebElement company_TF;
	
	@FindBy(xpath="//input[@title='Save [Alt+S]']")
	private WebElement saveBtn;
	
	//Initialization
	
	public CreateNewLeadPomPage(WebDriver driver) {
		  PageFactory.initElements(driver, this);
		 }
	
	//Utilization
	
	public String getHeader() {
		return header.getText();
	}
	
	public void getlastname_TF(String lastname) {
		lastname_TF.sendKeys(lastname);
	}
	
	public void getcompany_TF(String company) {
		company_TF.sendKeys(company);
	}
	
	public void getsave_Btn() {
		saveBtn.click();
	}
}
