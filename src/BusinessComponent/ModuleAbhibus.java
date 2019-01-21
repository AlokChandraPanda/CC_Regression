package BusinessComponent;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import ObjectRepository.OR_Abhibus;
import SeleniumHelper.DataTable;
import SeleniumHelper.KeywordOperation;
import SeleniumHelper.Reports;

public class ModuleAbhibus {
	
	public static void fnTC001() throws Exception{
		String strTotalFare =null;
		String strJourneyDate = null;
		//Read Test data from data file
		Map<String,String> testData =DataTable.recordData("Data1");	
		
		ModuleAbhibusHelper.launchApplication();
		
		//verifying tooltip
		KeywordOperation.mouseOverTo("xpath", OR_Abhibus.redBustooltip, "RedBus Immage");
		if(KeywordOperation.getAttributeValue("xpath", OR_Abhibus.redBustooltip, "title", "ToolTip").contains(testData.get("toolTip"))){
			Reports.logPASS("ToolTip Displayed on mouse over "+testData.get("toolTip"));
		} else{
			Reports.logFAIL("ToolTip Displayed on mouse over "+testData.get("toolTip"));
		}
		
		KeywordOperation.setText("xpath", OR_Abhibus.txtLeavingFrom, testData.get("LeavingFrom"), "Living From");
		KeywordOperation.dowait(1000);
		KeywordOperation.click("xpath", OR_Abhibus.txtDynamicJourneyselect.replace("<Journey>", testData.get("LeavingFrom")), "Living From");
		KeywordOperation.setText("xpath", OR_Abhibus.txtGoingTo, testData.get("GoingTo"), "Going To");
		KeywordOperation.dowait(1000);
		KeywordOperation.click("xpath", OR_Abhibus.txtDynamicJourneyselect.replace("<Journey>", testData.get("GoingTo").toString()), "Going to");
		
//		KeywordOperation.click("xpath", OR_Abhibus.txtDateOfJourney, "Date Of Journey");
		KeywordOperation.setText("xpath", OR_Abhibus.txtDateOfJourney, testData.get("DateOfjourney"), "Date Of Journey");
		if(KeywordOperation.assertDisplayed("xpath", OR_Abhibus.frmDatePicker, "Date Of Journey")){
			strJourneyDate = ModuleAbhibusHelper.getDatePickerDatelinXpath(testData.get("DateOfjourney").toString(), OR_Abhibus.lnkDynamicDatelink);
			KeywordOperation.dowait(400);
			KeywordOperation.click("xpath", strJourneyDate, "Date From date picker as "+testData.get("DateOfjourney").toString());
		}
		KeywordOperation.dowait(400);
	//	
	//	KeywordOperation.setText("xpath", OR_Abhibus.txtDateOfReturn, testData.get("DateOfReturn"), "Date Of Return");
		KeywordOperation.click("xpath", OR_Abhibus.btnSearch, "Search Button");
		KeywordOperation.dowait(200);
		List<WebElement> allSeats = KeywordOperation.findElements("xpath", OR_Abhibus.btnSelectSeats);
		if(allSeats.size()>0){
			allSeats.get(0).click();
			Reports.logPASS("Verified Home Page displayed and clicked on Select Seats");
		} else{
			Reports.logFAIL("Fail to Verified Home Page displayed and clicked on Select Seats");
		}
			
		if(KeywordOperation.assertDisplayed("xpath", OR_Abhibus.btnAvailableSeat1, "Available Seats")){
			KeywordOperation.click("xpath", OR_Abhibus.btnAvailableSeat1, "Available Seat");
		}
		
		KeywordOperation.selectFromDropdown("xpath", OR_Abhibus.drpBoardingPoint, testData.get("BoardingPoint"), "Boarding Point");
		strTotalFare = KeywordOperation.getText("xpath", OR_Abhibus.txtTotalFare, "Total Fare");
		if(!strTotalFare.equals("")){
			Reports.logPASS("Total Fare Displayed.."+strTotalFare);
		} else{
			Reports.logPASS("Fail to verify total fare displayed.."+strTotalFare);
		}
		
		KeywordOperation.click("xpath", OR_Abhibus.btnContinuePayment, "Continue Payment");
		KeywordOperation.dowait(200);
		KeywordOperation.assertDisplayed("xpath", OR_Abhibus.txtOnwordJourney, "Onword Journey");
		
		if(KeywordOperation.getText("xpath",OR_Abhibus.txtNetPayable, "Net Payable").trim().equalsIgnoreCase(strTotalFare.trim())){
			Reports.logPASS("Varified Net Payable amount - "+strTotalFare);
		}
	}
	

}
