package SeleniumHelper;

import org.openqa.selenium.WebDriver;

public class GlobalVariables {

	private static String projConPath =null;
	private static String broserType =null;
	private static WebDriver driver = null;
	private static String testDataFilename =null;
	private static String testIDindex =null;
	private static String testcaseName =null;
	
	public static void setProjConPath(String path){
		projConPath = path;
	}
	
	public static String getProjConPath(){
		return projConPath;
	}
	
	public static void setBrowser(String browser){
		broserType = browser;
	}
	
	public static String getBrowser(){
		return broserType;
	}
	public static void setWebDriver(WebDriver drv){
		driver = drv;
	}
	
	public static WebDriver getWebDriver(){
		return driver;
	}
	
	public static void setTestDataFile(String file){
		testDataFilename = file;
	}
	
	public static String getTestDataFile(){
		return testDataFilename;
	}	
	public static void setTestID(String tcID){
		testIDindex = tcID;
	}
	
	public static String getTestID(){
		return testIDindex;
	}
	
	public static void setTestCaseName(String tcname){
		testcaseName = tcname;
	}
	
	public static String getTesCaseName(){
		return testcaseName;
	}
}
