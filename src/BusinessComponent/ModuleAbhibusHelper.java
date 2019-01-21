package BusinessComponent;

import java.io.File;
import java.util.Map;

import ObjectRepository.OR_Abhibus;
import SeleniumHelper.DataTable;
import SeleniumHelper.GlobalVariables;
import SeleniumHelper.KeywordOperation;

public class ModuleAbhibusHelper  {
	/*
	 * Launching the Application and verifying Home page
	 */
	public static void launchApplication() throws Exception{
		Map<String,String> dataMap =DataTable.readInI(new File(System.getProperty("user.dir")+"/Config.ini"));
		KeywordOperation.navigateTo(dataMap.get("URL"), GlobalVariables.getBrowser());
		KeywordOperation.waitForpageLoad();
		//KeywordImplementaion.dowait(50);
		if(KeywordOperation.findElements("xpath", OR_Abhibus.boxNotification).size()>0){
			KeywordOperation.click("xpath", OR_Abhibus.btnLater, "Notification Button Later");
			System.out.println("displayed notification frame");
		}
		KeywordOperation.assertDisplayed("xpath", OR_Abhibus.txtDateOfJourney, "Search Page");
		
	}
	
	public static String getDatePickerDatelinXpath(String data,String dynamiclocator){
		String[] var0 = data.split("-");
		String returnValue = null;
		String newXpathValue = null;
		switch(var0[1]){
		case "January":
			returnValue="0";
			break;
		case "February":
			returnValue="1";
			break;
		case "March":
			returnValue="2";
			break;	
		case "April":
			returnValue="3";
			break;
		case "May":
			returnValue="4";
			break;
		case "Jun":
			returnValue="5";
			break;
		case "July":
			returnValue="6";
			break;
		case "August":
			returnValue="7";
			break;
		case "September":
			returnValue="8";
			break;
		case "October":
			returnValue="9";
			break;
		case "November":
			returnValue="10";
			break;
		case "December":
			returnValue="11";
			break;
		default:
			break;
		}
		dynamiclocator=dynamiclocator.replace("<d>", var0[0]);
		newXpathValue=dynamiclocator.replace("<m>", returnValue);
		return newXpathValue;
	}
	

	public static void logoffApplication(){
		
	}
}
