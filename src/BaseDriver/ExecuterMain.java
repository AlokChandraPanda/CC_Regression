package BaseDriver;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import SeleniumHelper.GlobalVariables;
import SeleniumHelper.Reports;

public class ExecuterMain {

	public static String excSuiteFile = null;
	public static void main(String[] args) throws Exception {
		
		 String projectPath = new File(".").getCanonicalPath().toString();
		 System.out.println(projectPath);
		 excSuiteFile = projectPath+"\\MasterRegressionSuite.xlsx";
		 GlobalVariables.setProjConPath(projectPath);
		 
		 FileInputStream fis = new FileInputStream(excSuiteFile);
		 XSSFWorkbook masterSuiteWB = new XSSFWorkbook(fis);
		 try{
			 XSSFSheet msSheet = masterSuiteWB.getSheetAt(0);
			 int runSuiteFlagIndex =0;
			 Iterator cellIterator =msSheet.getRow(0).cellIterator();
			 while(cellIterator.hasNext()){
				 XSSFCell cell =(XSSFCell) cellIterator.next();
				 if(cell.getStringCellValue().equals("RunSuiteFlag")){
					 break;
				 } runSuiteFlagIndex++;
				 
			 }
			 
			 for(int i=0; i<msSheet.getLastRowNum();i++){
				 if(msSheet.getRow(i).getCell(runSuiteFlagIndex).getStringCellValue().equals("Yes")){
					XSSFSheet runAppModule= masterSuiteWB.getSheet(msSheet.getRow(i).getCell(0).getStringCellValue());
					Iterator moduleCellItr = runAppModule.getRow(0).cellIterator();
					int moduleSuiteFlagIndex=0;
					while(moduleCellItr.hasNext()){
						 XSSFCell cell =(XSSFCell) moduleCellItr.next();
						 if(cell.getStringCellValue().equals("TCexeFlag")){
							 break;
						 } moduleSuiteFlagIndex++;
					}
					
					for(int j=0;j<= runAppModule.getLastRowNum(); j++){
						if(runAppModule.getRow(j).getCell(moduleSuiteFlagIndex).getStringCellValue().equals("Yes")){
							String tcIndex= runAppModule.getRow(j).getCell(0).getStringCellValue();
							GlobalVariables.setTestID(tcIndex);
							String classname= runAppModule.getRow(j).getCell(1).getStringCellValue();
							String methodname= runAppModule.getRow(j).getCell(2).getStringCellValue();
							String browser= runAppModule.getRow(j).getCell(5).getStringCellValue();
							String testDatafile= runAppModule.getRow(j).getCell(4).getStringCellValue();
							GlobalVariables.setTestDataFile(projectPath+"\\src\\DataSheet\\"+testDatafile+".xlsx");
							String testCaseName= runAppModule.getRow(j).getCell(6).getStringCellValue();
							GlobalVariables.setTestCaseName(testCaseName);	
							System.out.println("Class name="+classname);
							System.out.println("Method name="+methodname);
							System.out.println("Browser type="+browser);
							Reports.startReport();
							TestRunner.configRunner(classname, methodname, browser,testCaseName);
							GlobalVariables.getWebDriver().quit();
							Reports.endReport();
							break;
						}
					}
				 } 
				 
			 }
			 
		 } catch(Exception e){
			e.printStackTrace();
		 } finally{
			 masterSuiteWB.close();
			 if(GlobalVariables.getWebDriver()!=null){
				 GlobalVariables.getWebDriver().quit();	 
			 }
		 }
	}

}
