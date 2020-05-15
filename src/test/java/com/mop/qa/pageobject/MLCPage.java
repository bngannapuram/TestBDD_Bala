package com.mop.qa.pageobject;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.mop.qa.testbase.TestBase;
import com.mop.qa.testbase.PageBase;

public class MLCPage extends PageBase{

	public static String toolName = TestBase.toolName;
	public static boolean completion_flag = true;
	public static boolean error_flag=false;
	public MLCPage() {
		super(toolName);
	}
	 
	// ---------Page objects-------------------//
	
	@FindBy(xpath = "//h1[text()='Life Insurance']")
	public WebElement website_launch_page_verify;
	
	@FindBy(xpath = "//nav[@id='nav-onscreen']//button")
	public WebElement search_website;

	@FindBy(xpath = "//input[@id='q'][@type='search']")
	public WebElement search_bar;

	@FindBy(xpath = "//a[@role='button']/span[@class='autocomplete-results-item-title']/strong[text()='LifeView']")
	public WebElement search_string_match;

	@FindBy(xpath = "//ul[@itemprop='breadcrumb']")
	public WebElement breadcrumbs;

	@FindBy(xpath = "//span[@class='cta'][text()='Request a demo']")
	public WebElement request_demo_button;

	@FindBy(xpath = "//h1[text()='Request a LifeView demo']")
	public WebElement request_demo_page_verify;

	@FindAll({@FindBy(xpath = "//input[contains(@class,'text-box single-line')]")})
	public List<WebElement> edit_text;
	
	@FindBy(xpath = "//input[@type='radio'][@value='PM']")
	public WebElement am_pm;

	@FindBy(xpath = "//textarea[@class='form-control']")
	public WebElement description;

	@FindBy(xpath = "//input[@type='submit'][@value='Book a demo']")
	public WebElement book_demo_button;

	// ---------Methods--------------//

	public void launchMLC(String URL) throws Exception {
		enterUrl(URL, "MLC Life Insurance");
		WebDriverWait wait = new WebDriverWait(remoteDriver, 30, 3000);
		wait.until(ExpectedConditions.visibilityOf(website_launch_page_verify));
	}
	
	public void searchOnWebsite(String searchString) throws Exception {
		click(search_website, "Click Search");
		enterText(search_bar, searchString,"Search Website");
		click(search_string_match, "Click Match");
		WebDriverWait wait = new WebDriverWait(remoteDriver, 30, 3000);
		wait.until(ExpectedConditions.visibilityOf(breadcrumbs));

		scrollViewToElement(request_demo_button);
		click(request_demo_button, "Click Request Demo");
		wait.until(ExpectedConditions.visibilityOf(request_demo_page_verify));
	}
		
	public void submitRequestDemoForm(String name, String company, String email, String contact, String date) throws Exception {
		for (int i=0; i<=edit_text.size(); i++) {
			switch (i) {
				case 0:
					enterText(edit_text.get(i), name, "Name");
					break;
				case 1:
					enterText(edit_text.get(i), company, "Company");
					break;
				case 2:
					enterText(edit_text.get(i), email, "EMail");
					break;
				case 3:
					enterText(edit_text.get(i), contact, "Contact Number");
					break;
				case 4:
					enterText(edit_text.get(i), date, "Date");
					break;
			}
		}
		scrollViewToElement(request_demo_button);
//		click(book_demo_button, "Book Demo");
	}
}
