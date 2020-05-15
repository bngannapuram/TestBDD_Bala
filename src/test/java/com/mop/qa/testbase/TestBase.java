package com.mop.qa.testbase;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.mop.qa.Utilities.ReadDataSheet;
import io.appium.java_client.AppiumDriver;

/**
 * The TestBase class implements the methods related to the driver objects.
 */
public class TestBase {

	public static String osType = System.getProperty("os.name");
	public static String toolName = "";
	public static String localGrid = "";
	@SuppressWarnings("rawtypes")
	public static RemoteWebDriver remoteDriver;
	public static HSSFSheet ExcelWSheet;
	public static HSSFWorkbook ExcelWBook;

	/**
	 * This method is used to call the driver initiating method.
	 * @param list
	 *            This is the first parameter to initDriver method
	 * @return String This returns tool name used for execution.
	 * @exception IOException
	 *                On input error.
	 */
	public String initDriver(List<String> list) throws IOException {
		try {
			toolName = initiateDriver(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toolName;
	}

	/**
	 * This method is used to close the driver.
	 * @param toolName
	 *            This is the first parameter to closeDriver method
	 * @return Nothing.
	 */
	public static void closeDriver(String toolName) {
		if (toolName.equalsIgnoreCase("Selenium")) {
			remoteDriver.close();
		}
	}

	/**
	 * This method is used to initiate the driver for execution.
	 * @param list
	 *            This is the first parameter to initiateDriver method
	 * @return String This returns tool name used for execution.
	 */
	public String initiateDriver(List<String> list) {
		try {
			toolName = list.get(0);
			if (toolName.equalsIgnoreCase("Selenium")) {
				String browser = list.get(1);
				PageBase pagebaseclass = new PageBase(remoteDriver, toolName);
				remoteDriver = pagebaseclass.launchSite(browser);
			} else {
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("toolname testbase-" + toolName);
		return toolName;
	}

	/**
	 * This method is used to get the value from data.properties file.
	 * @param key
	 *            This is the first parameter to getPropertyValue method
	 * @return String This returns the corresponding value for the key.
	 * @exception IOException
	 *                On input error.
	 */
	public static String getPropertyValue(String key) throws IOException {
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

	public static String getEnvName(){
		String env = "api_test";
		return env;
	}

	public static String getCellData(String SheetName, String column) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream("./DataSheet.xls");
			// Code To Access the required test data sheet
			// return CellData;
		} catch (Exception e) {
			return "";
		}
		return column;
	}
}