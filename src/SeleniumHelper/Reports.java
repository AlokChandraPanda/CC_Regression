package SeleniumHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.impl.util.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports {

	public static byte[] strImg;
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	public static void startReport(){
		extent = new ExtentReports (System.getProperty("user.dir") +"/TestReports/"+GlobalVariables.getTesCaseName()+".html", true);
		extent  .addSystemInfo("Host Name", "SoftwareTestingMaterial")
                .addSystemInfo("Environment", "Automation Testing")
                .addSystemInfo("User Name", "Rajkumar SM");
                extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
                logger = extent.startTest("Test One");
	}
	
	public static void endReport(){
		extent.endTest(logger);
                extent.flush();
                extent.close();
    }
	
	public static void logPASS(String msg){
		logger.log(LogStatus.PASS, msg);
	}
	public static void logFAIL(String msg) {
		try{
			logger.log(LogStatus.FAIL, msg+logger.addBase64ScreenShot(getScreenShot()));	
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void logFAILWithOutScreenPrint(String msg) {
		try{
			logger.log(LogStatus.FAIL, msg);	
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void logINFO(String msg){
		logger.log(LogStatus.INFO, msg);
	}
	
	
	
	private static String getScreenShot() throws IOException {
		File src = (File)((TakesScreenshot)GlobalVariables.getWebDriver()).getScreenshotAs(OutputType.FILE);
		FileInputStream fis = new FileInputStream(src);
		byte[] encrept = new byte[(int)src.length()];
		fis.read(encrept);
		String str = new String(Base64.encode(encrept),"UTF-8");
/*		String path = System.getProperty("user.dir")+"/Screenshots/"+System.currentTimeMillis()+".png";
		
		try{
			FileUtils.copyFile(src, new File(path));	
		} catch(Exception e){
			e.printStackTrace();
		}
		return path;
*/		return str;
		}
}
