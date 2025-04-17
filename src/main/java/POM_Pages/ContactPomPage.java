package POM_Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactPomPage {

	// Declaration
	@FindBy(linkText = "Contacts")
	private WebElement header;

	@FindBy(xpath = "//img[@alt='Create Contact...']")
	private WebElement plusicon;

	// Initialize
	public ContactPomPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Utilize
	public String getHeader() {
		return header.getText();
	}

	public void getPlusicon() {
		plusicon.click();
	}
}
