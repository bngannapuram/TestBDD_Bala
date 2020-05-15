package com.mop.qa.testrunner;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.nio.file.CopyOption;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;

import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;

/**
 * The RunCukesTest class collectively executes the feature files and creates a
 * BDD report.
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"json:target/cucumber.json","com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" },
		glue = "com.mop.qa.stepdefs",
		monochrome = true)

public class RunCukesTest extends AbstractTestNGCucumberTests {

	public static String resultFolder;

	/**
	 * The @BeforeClass method creates the report folder and instance.
	 * @return Nothing.
	 */
	@BeforeClass
	public static void setup() {
		ExtentProperties extentProperties = ExtentProperties.INSTANCE;
		extentProperties.setReportPath(resultFolder + "/TestReport.html");
	}

	/**
	 * The @AfterClass method completes the report instance.
	 * @return Nothing.
	 */
	@After
	public static void teardown() throws IOException {
		
		Reporter.loadXMLConfig(new File("extent-config.xml"));
		File file = new File("lib/CMT.PNG");
		File file1 = new File(resultFolder);
		Path src = FileSystems.getDefault().getPath(file.getPath(), new String[0]);
		Path dest = FileSystems.getDefault().getPath((new File(file1, file.getName())).getPath(), new String[0]);
		Files.copy(src, dest, new CopyOption[0]);
		Reporter.setSystemInfo("user", System.getProperty("user.name"));
		Reporter.setSystemInfo("os", "Windows");
	}
	
	public static String[] featureList;
	static {
		try {
			final CucumberOptions old = (CucumberOptions) RunCukesTest.class.getAnnotation(CucumberOptions.class);
			Object handler = Proxy.getInvocationHandler(old);

			Field field = null;
			try {
				field = handler.getClass().getDeclaredField("memberValues");
			} catch (Exception e) {
				e.printStackTrace();
			}
			field.setAccessible(true);
			Map<String, String[]> memberValues;
			try {
				memberValues = (Map<String, String[]>) field.get(handler);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalStateException(e);
			}

			SimpleDateFormat sdfDateReport = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			Date now = new Date();

			resultFolder = "ReportGenerator/" + "BDD_HtmlReport_" + sdfDateReport.format(now);
			File file = new File(resultFolder);
			file.mkdirs();

			Map systemInfo = new HashMap();
			systemInfo.put("Cucumber version", "v1.2.3");

			List<String> featureLists = new ArrayList<String>();
			String tagsLists = "";
			try {
				featureLists = TestRunner.featureList();
				tagsLists = TestRunner.tagsList();

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			int x = featureLists.size();
			featureList = new String[x];
			for (int i = 0; i < featureLists.size(); i++) {
				featureList[i] = featureLists.get(i);
			}

			String[] tags = { tagsLists };

			memberValues.put("features", featureList);
			if (!tagsLists.isEmpty()) {
				memberValues.put("tags", tags);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
