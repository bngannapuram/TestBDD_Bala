package com.mop.qa.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.mop.qa.testbase.TestBase;
import com.cucumber.listener.Reporter;
import com.mop.qa.testbase.PageBase;

public class ATOPage extends PageBase{

	public static String toolName = TestBase.toolName;
	public static boolean completion_flag = true;
	public static boolean error_flag=false;
	public String estimated_tax_value = null;
	public ATOPage() {
		super(toolName);
	}
	 
	// ---------Page objects-------------------//
	
	@FindBy(xpath = "//h1[text()='Simple tax calculator']")
	public WebElement website_launch_page_verify;
	
	@FindBy(xpath = "//select[@id='ddl-financialYear']/option[@value='2017']")
	public WebElement income_yr_2017;

	@FindBy(xpath = "//select[@id='ddl-financialYear']/option[@value='2018']")
	public WebElement income_yr_2018;

	@FindBy(xpath = "//select[@id='ddl-financialYear']/option[@value='2019']")
	public WebElement income_yr_2019;

	@FindBy(id = "texttaxIncomeAmt")
	public WebElement income_amount;

	@FindBy(xpath = "//span[text()='Resident for full year']")
	public WebElement residency_status_2017;

	@FindBy(xpath = "//span[text()='Non-resident for full year']")
	public WebElement residency_status_2018;

	@FindBy(xpath = "//span[text()='Part-year resident']")
	public WebElement residency_status_2019;
	
	@FindBy(xpath = "//button[@type='button'][text()='Submit']")
	public WebElement submit_button;

	@FindBy(xpath = "//div[@class='white-block']")
	public WebElement result_page_verify;

	@FindBy(xpath = "//div[@class='white-block']//span")
	public WebElement estimated_tax;

	@FindBy(xpath = "//button[@type='button'][text()='Restart']")
	public WebElement restart_button;

	
	// ---------Methods--------------//

	public void launchATO(String URL) throws Exception {
		enterUrl(URL, "ATO Calculators");
		WebDriverWait wait = new WebDriverWait(remoteDriver, 30, 3000);
		wait.until(ExpectedConditions.visibilityOf(website_launch_page_verify));
		Reporter.addStepLog("ATO tax calculator url launched");
		System.out.println("ATO tax calculator url launched");
	}
	
	public void calculateTax(String income_year, String income, String residancy_status) throws Exception {
		switch (income_year) {
		case "2017":
			enterText(income_yr_2017, income_year,"Income Year");
			enterText(residency_status_2017, residancy_status,"Residancy Status");
			break;
		case "2018":
			enterText(income_yr_2018, income_year,"Income Year");
			enterText(residency_status_2018, residancy_status,"Residancy Status");
			break;
		case "2019":
			enterText(income_yr_2019, income_year,"Income Year");
			enterText(residency_status_2019, residancy_status,"Residancy Status");
			break;
		}

		enterText(income_amount, income,"Income Amount");

		click(submit_button, "Calculate Tax");
		WebDriverWait wait = new WebDriverWait(remoteDriver, 30, 3000);
		wait.until(ExpectedConditions.visibilityOf(result_page_verify));

		Reporter.addStepLog("Income_year: "+income_year+"Income: "+income+"Residancy_status: "+residancy_status);
		System.out.println("Income_year: "+income_year+"Income: "+income+"Residancy_status: "+residancy_status);

		estimated_tax_value = getAttributeValue(estimated_tax, "value", "Estimated Tax Amount");
		Reporter.addStepLog("Estimated Tax Amount :"+ estimated_tax_value);
		System.out.println("Estimated Tax Amount :" + estimated_tax_value);

		clickRestartCalculateTaxButton();
	}

	public void clickRestartCalculateTaxButton() throws Exception {
		click(restart_button, "Restart Calculate Tax");
		WebDriverWait wait = new WebDriverWait(remoteDriver, 30, 3000);
		wait.until(ExpectedConditions.visibilityOf(income_amount));
	}
}
