package SeleniumHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataTable {

	private static String testDataFilename=null;
	private static XSSFWorkbook dataBook= null;
	private static Map<String,String> dataMap =null;
	public static Map<String,String> recordData(String colvalue) throws IOException{
		dataMap = new HashMap<>();
		testDataFilename = GlobalVariables.getTestDataFile();
		try{
			FileInputStream fis2 = new FileInputStream(testDataFilename);
			dataBook = new XSSFWorkbook(fis2);
			int rowIndex= getRowIndex(dataBook,GlobalVariables.getTestID());
			String testData =dataBook.getSheetAt(0).getRow(rowIndex).getCell(1).getStringCellValue();
			String[] var0 = testData.split(";");
			for(String var1: var0){
				String[] var2=var1.split("=");
				dataMap.put(var2[0], var2[1]);
			}
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			dataBook.close();
		}
		return dataMap;
	}
	
	private static int getRowIndex(XSSFWorkbook wb, String data){
		int index=0;
		Iterator<Row> rowIterator = wb.getSheetAt(0).iterator();
		loop:
		while(rowIterator.hasNext()){
			XSSFRow row = (XSSFRow) rowIterator.next();
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase(data)){
				break loop;
			}index++;
		}
		return index;
	}
	
	public static Map<String, String> readInI(File file) throws IOException{
		BufferedReader br = null ;
		String line;
		Map<String, String> hmap = new HashMap<>();
		try{
			br= new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null){
					if(line.contains("=")){
						String[] var0=line.split("=");
						hmap.put(var0[0], var0[1]);
					}
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			br.close();
		}
		return hmap;
	}
}
