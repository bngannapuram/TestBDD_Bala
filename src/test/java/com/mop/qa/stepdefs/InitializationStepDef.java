package com.mop.qa.stepdefs;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.AfterClass;

import com.mop.qa.testbase.TestBase;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * The InitializationStepDef class implements the @before and @after methods for
 * each scenario to be executed.
 */
public class InitializationStepDef extends TestBase {

	public static int k;
	public static int i;
	public static String toolName;
	public static String feature = "";

	/**
	 * The @Before method fetches the data provided in the TestRunnerBDD.xls and
	 * initiates the corresponding drivers to trigger the execution.
	 * @return Nothing.
	 * @exception Exception
	 *                On error.
	 */
	@Before
	public void driverStart(Scenario scenario) throws Exception {
		System.out.println(scenario.getId());

		if (feature.equalsIgnoreCase(scenario.getId().split(";")[0]))
			toolName = initDriver(Input_List(featureList().get(i)).get(i));
		else {
			for (i = k; i < featureList().size();) {
				toolName = initDriver(Input_List(featureList().get(i)).get(i));
				System.out.println("toolname initclass-" + toolName);
				feature = scenario.getId().split(";")[0];
				k++;
				break;
			}
		}
	}

	@After
	public void driverStop(Scenario scenario) throws Exception {
		System.out.println("After");
		remoteDriver.quit();			
	}


	/**
	 * This method is used to get a list of all features to be executed.
	 * 
	 * @param featureName
	 *            This is the first parameter to Input_List method
	 * @return List<List<String>> This returns list of a collection of features to
	 *         be executed.
	 * @exception Exception
	 *                On input error.
	 */
	public static List<List<String>> Input_List(String featureName) throws Exception {

		List<List<String>> list1 = new ArrayList<List<String>>();
		List<String> list = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("./TestRunnerBDD.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheet("Features");

		String userDir = System.getProperty("user.dir");
		userDir = userDir.concat("\\src\\test\\java\\com\\mop\\qa\\features\\");

		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
			HSSFRow row = sheet.getRow(count);
			if (row.getCell(1).toString().equalsIgnoreCase("yes")) {
				if (row.getCell(2).toString().equalsIgnoreCase("Selenium")) {
					list.add(row.getCell(2).toString());
					list.add(row.getCell(3).toString());
					list1.add(list);
				} 
				else {
					list.add(row.getCell(2).toString());
					list1.add(list);
				}
			}
		}
		return list1;
	}

	/**
	 * This method is used to get a list of all features to be executed from the
	 * TestRunnerBDD.xls file.
	 * 
	 * @return List<String> This returns list of all features to be executed.
	 * @exception Exception
	 *                On input error.
	 */
	public static List<String> featureList() throws Exception {

		List<String> featuresList = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("./TestRunnerBDD.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheet("Features");

		String userDir = System.getProperty("user.dir");
		userDir = userDir.concat("\\src\\test\\java\\com\\mop\\qa\\features\\");
		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
			HSSFRow row = sheet.getRow(count);
			if (row.getCell(1).toString().equalsIgnoreCase("Yes")) {
				featuresList.add(userDir + row.getCell(0).toString());
			}
		}
		return featuresList;
	}

	/**
	 * This method is used to get a list of all browsers for each feature file from
	 * TestRunnerBDD.xls file.
	 * 
	 * @return List<String> This returns list of all browsers to be used for
	 *         execution.
	 * @exception Exception
	 *                On input error.
	 */
	public static List<String> browserList() throws Exception {

		List<String> browserList = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("TestRunnerBDD.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheet("Features");

		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
			HSSFRow row = sheet.getRow(count);
			if (row.getCell(1).toString().equalsIgnoreCase("Yes")) {
				browserList.add(row.getCell(3).toString());
			}
		}

		return browserList;
	}

	/**
	 * This method is used to get a list of all tools for each feature file from
	 * TestRunnerBDD.xls file.
	 * 
	 * @return List<String> This returns list of all tools to be used for execution.
	 * @exception Exception
	 *                On input error.
	 */
	public static List<String> toolNameList() throws Exception {

		List<String> toolNameList = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("TestRunnerBDD.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheet("Features");

		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
			HSSFRow row = sheet.getRow(count);
			if (row.getCell(1).toString().equalsIgnoreCase("Yes")) {
				toolNameList.add(row.getCell(2).toString());
			}
		}
		return toolNameList;
	}
}
