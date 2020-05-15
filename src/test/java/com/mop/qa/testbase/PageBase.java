package com.mop.qa.testbase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * The PageBase class implements the methods related to the browser actions.
 */
public class PageBase {

	public static RemoteWebDriver remoteDriver;
	private static String toolName;
	public String webBrowser = null, chromeDriverPath = null;
	
	public PageBase(RemoteWebDriver driver, String tool) {
		PageBase.remoteDriver = driver;
		PageFactory.initElements(remoteDriver, this);
		toolName = tool;
	}

	public PageBase(String toolName) {
		if (toolName.equalsIgnoreCase("Selenium")) {
			PageFactory.initElements(remoteDriver, this);

		}
	}

	/**
	 * This method is used to launch the application.
	 * 
	 * @param browser
	 *            This is the first parameter to RemoteWebDriver method
	 * @return RemoteWebDriver This returns webdriver for execution.
	 * @exception Exception
	 *                On error.
	 */
	public RemoteWebDriver launchSite(String browser) throws Exception {
		webBrowser = browser;

		if (webBrowser.equalsIgnoreCase("chrome")) {
			chromeDriverPath = getAppProperties("chromeDriverPath");
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
	        
	        //----------------------------------New Logging----------------//
	        ChromeOptions options= new ChromeOptions();
	        options.addArguments("--test-type");
			options.addArguments("start-maximized");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
	        DesiredCapabilities caps = DesiredCapabilities.chrome();
	        caps.setCapability(ChromeOptions.CAPABILITY, options);
	        LoggingPreferences logPrefs = new LoggingPreferences();
	        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
	        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			remoteDriver = new ChromeDriver(caps);
			remoteDriver.manage().window().maximize();
		}
		return remoteDriver;
	}

	/**
	 * This method is used to get the value from data.properties file.
	 * 
	 * @param key
	 *            This is the first parameter to getAppProperties method
	 * @return String This returns the corresponding value for the key.
	 * @exception IOException
	 *                On input error.
	 */
	public String getAppProperties(String key) throws IOException {
		String value = "";
		try {

			FileInputStream fileInputStream = new FileInputStream("data.properties");
			Properties property = new Properties();
			property.load(fileInputStream);
			value = property.getProperty(key);

			fileInputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	/**
	 * This method is used to launch the application with pageTitle verification.
	 * 
	 * @param url
	 *            This is the first parameter to enterUrl method
	 * @param pageTitle
	 *            This is the second parameter to enterUrl method
	 * @return Nothing.
	 * @exception Exception
	 *                On error.
	 */
	public void enterUrl(String url, String pageTitle) throws Exception {
		try {
			switch (toolName) {
			case "Selenium":
				remoteDriver.get(url);
				break;
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	/**
	 * This method is used to perform CLICK action in the application.
	 * 
	 * @param e
	 *            This is the first parameter to click method
	 * @param elementName
	 *            This is the second parameter to click method
	 * @return Nothing.
	 * @exception Exception
	 *                On error.
	 */
	public void click(WebElement e, String elementName) throws Exception {
		try {
			switch (toolName) {
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				waitSelenium.until(ExpectedConditions.elementToBeClickable(e));
				break;
			}
			e.click();
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new Exception();
		}

	}

	public void clickbyid(String id, String elementName) throws Exception {
		try {
			switch (toolName) {
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 100, 1000);
				waitSelenium.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
				remoteDriver.findElementById(id).click();
				break;
			}
		} catch (Exception exc) {
			Assert.assertTrue(false, "Exception on clicking webelement" + elementName);
			throw new Exception();
		}
	}


	public void assertTrue(String message) throws Exception {
		Assert.assertTrue(true, message);
	}

	public void assertFalse(String message) throws Exception {
		Assert.assertFalse(false, message);
	}

	/**
	 * This method is used to enter text for an element in the application.
	 * 
	 * @param element
	 *            This is the first parameter to enterText method
	 * @param cred
	 *            This is the second parameter to enterText method
	 * @param elementName
	 *            This is the third parameter to enterText method
	 * @return Nothing.
	 * @exception Exception
	 *                On error.
	 */
	public void enterText(WebElement element, String text, String elementName) throws Exception {
		try {
			switch (toolName) {
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 120, 30000);
				waitSelenium.until(ExpectedConditions.visibilityOf(element));
				break;
			}
			element.clear();
			element.sendKeys(text);
		} catch (Exception exc) {
			Assert.assertTrue(false, "Exception on Get Text from webelement" + elementName);
			throw new Exception();
		}
	}

	public static void scrollViewToElement(WebElement webElement) throws InterruptedException {
		try {
        JavascriptExecutor js = (JavascriptExecutor) remoteDriver;
        WebDriverWait wait = new WebDriverWait(remoteDriver, 30);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", webElement);
        Thread.sleep(500);
		}
		catch(Exception e) {
			System.out.println("Exception caught..");
			 JavascriptExecutor js = (JavascriptExecutor) remoteDriver;
		        WebDriverWait wait = new WebDriverWait(remoteDriver, 30);
		        wait.until(ExpectedConditions.visibilityOf(webElement));
		        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", webElement);
		        Thread.sleep(500);
		}
    }
	
	public static void ScrolltoPageEnd() {
    	Actions builder = new Actions(remoteDriver);
    	//builder.keyDown(Keys.END).build().perform();
    	builder.sendKeys(Keys.END).perform();
    }

	public String getAttributeValue(WebElement e, String value,String elementName) throws Exception {
		String text = "";
		try {
			switch (toolName) {
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				break;
			}

			text = e.getAttribute(value);
			return text;
		} catch (Exception exc) {
			exc.printStackTrace();
			Assert.assertTrue(false, "Exception on Get Text from webelement" + elementName);
			throw new Exception();
		}
	}	
}
