package BaseDriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import SeleniumHelper.GlobalVariables;

public class BaseDriver {
	public static WebDriver driver;
	
	public static WebDriver openBrowser(String browser) throws Exception{
		  if(browser.equalsIgnoreCase("IE")){
			  driver= new InternetExplorerDriver();
			  }
			  else if(browser.equalsIgnoreCase("chrome")){
				  System.setProperty("webdriver.chrome.driver", GlobalVariables.getProjConPath()+"\\Server\\chromedriver.exe");
				  driver= new ChromeDriver();
				  System.out.println("Chrome Browser launched...");
			  } else{
				  throw new Exception("invalid browser type");
			  }
		  if(driver!=null){
			  GlobalVariables.setWebDriver(driver);	  
		  }
		return driver;
	}
	
}
