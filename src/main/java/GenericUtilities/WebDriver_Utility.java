package GenericUtilities;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


/**
 * @author E.Karuna
 * This is webDriver Utility
 */
public class WebDriver_Utility {

	public WebDriver driver = null;
	
	/**
	 * This method is used to maximize the window
	 * @param driver
	 */

	public void maximizeTheWindow(WebDriver driver) {

		driver.manage().window().maximize();
	}

	/**
	 * This method is used to wait till element found on the webpage
	 * @param timeOuts
	 * @param driver
	 */
	public void waitTillElementFound(String timeOuts, WebDriver driver) {
		long time = Long.parseLong(timeOuts);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(time));

	}
	
	/**
	 * This method is used to navigate to an application
	 * @param driver
	 * @param url
	 */

	public void navigateToAnAppl(WebDriver driver, String url) {

		driver.get(url);
	}
	
	/**
	 * This method is used to handle the popup
	 * @param driver
	 */

	public void HandleAlertAndAccept(WebDriver driver) {

		driver.switchTo().alert().accept();
	}

	public void HandleAlertAndCancel(WebDriver driver) {

		driver.switchTo().alert().dismiss();

	}

	/**
	 * This method is used to handle the browser alert,Fetch the alert's text, and returns it.
	 * @param driver
	 * @return
	 */
	public String HandleAlertAndFetchText(WebDriver driver) {

		String text = driver.switchTo().alert().getText();
		return text;
	}

	public void HandleAlertAndPassText(WebDriver driver, String text) {

		driver.switchTo().alert().sendKeys(text);
	}

	public void Action_MouseHovering(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
	}
/**
 * This method is used to quit the browser
 * @param driver
 */
	public void QuitTheBrowser(WebDriver driver) {
		driver.quit();
	}
//This method is used to close the current window
	public void CloseTheBrowser(WebDriver driver) {
		driver.close();
	}
	
	public void HandleDropdownUsingIndex(WebElement dropdown, int index) {
		Select s = new Select(dropdown);
		s.selectByIndex(index);
	}
	
	public void HandleDropdownUsingValue(WebElement dropdown, String value) {
		Select s = new Select(dropdown);
		s.selectByValue(value);
	}
	
	public void HandleDropdownUsingVisibleText(WebElement dropdown, String text) {
		Select s = new Select(dropdown);
		s.selectByVisibleText(text);
	}
	
	public void switchToTabUsingUrl(WebDriver driver, String url) {
		Set<String> wids = driver.getWindowHandles();
		for (String s : wids) {
			driver.switchTo().window(s);
			if(driver.getCurrentUrl().contains(url)) {
			break;
			}
	}
		
	}
	
	public void switchToTabUsingTitle(WebDriver driver, String title) {
		Set<String> wids = driver.getWindowHandles();
		for (String s : wids) {
			driver.switchTo().window(s);
			if(driver.getCurrentUrl().contains(title)) {
			break;
			}
	}
		
	}
	
	public void switchBackToParentWindow(WebDriver driver, String pwid) {
		driver.switchTo().window(pwid);
	}
}
