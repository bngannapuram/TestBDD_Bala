package com.mop.qa.testrunner;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

//import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.TestNG;

/**
 * The TestRunner class initiates the execution process.
 */
public class TestRunner {

	/**
	 * This method is used to trigger the execution.
	 * @return Nothing.
	 */
	public static void testRunner() {

		try {

			String log4jConfPath = "./log4j.properties";
			PropertyConfigurator.configure(log4jConfPath);
			TestNG testNG = new TestNG();

			testNG.setTestClasses(new Class[] { RunCukesTest.class });
			testNG.run();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * This method is used to get a list of all tags to be executed from the
	 * TestRunnerBDD.xls file.
	 * 
	 * @return List<String> This returns list of all tags to be executed.
	 * @exception Throws Exception On input error.
	 */
	public static String tagsList() throws Exception {
		String featuresList = "";
		FileInputStream fis = new FileInputStream("TestRunnerBDD.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fis);
		HSSFSheet sheet = wb.getSheet("Tags");

		for (int count = 1; count <= sheet.getLastRowNum(); count++) {
			HSSFRow row = sheet.getRow(count);
			if (row.getCell(1).toString().equalsIgnoreCase("Yes")) {

				featuresList = featuresList + (row.getCell(0).toString()) + ",";
			}
		}
		return featuresList;
	}
}
