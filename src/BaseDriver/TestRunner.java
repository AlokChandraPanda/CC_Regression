package BaseDriver;

import SeleniumHelper.GlobalVariables;
import SeleniumHelper.Reports;


public class TestRunner {
	private static String browserToSet= null;

	public static void configRunner(String clsName, String methodName, String browser, String tcName){
		browserToSet = browser;
		GlobalVariables.setBrowser(browserToSet);
		run(clsName,methodName);
		
	}
	private static void run(String clsName, String methodName){

		try {
			Class cls =Class.forName("BusinessComponent."+clsName);
			Object obj = cls.newInstance();
			cls.getDeclaredMethod(methodName,null).invoke(obj, null);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
