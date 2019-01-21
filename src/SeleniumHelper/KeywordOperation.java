package SeleniumHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseDriver.BaseDriver;

import com.google.common.collect.Table.Cell;

public class KeywordOperation extends BaseDriver {
//	private static WebDriver driver = null;
	private static WebElement mainquery = null;
	
	public static void dowait(long seconds ) throws Exception{
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void navigateTo(String url, String browserType) throws Exception{
		WebDriver drv = openBrowser(browserType);
		driver = drv;
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Reports.logPASS("Application launched-"+url);
		dowait(300);
		waitForpageLoad();
	}
	
	public static void handleAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 300 /*timeout in seconds*/);
		try{
			if(wait.until(ExpectedConditions.alertIsPresent())!=null){
				driver.switchTo().alert().accept();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	public static boolean waitForpageLoad(){
		boolean flag=false;
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(60, TimeUnit.SECONDS)
						.pollingEvery(200, TimeUnit.MICROSECONDS)
						.ignoring(ClassNotFoundException.class);
		Object status =wait.until(ExpectedConditions.jsReturnsValue("return document.readyState"));
		System.out.println(status);
		
		if(wait.until(ExpectedConditions.jsReturnsValue("return document.readyState")).equals("Complete")){
			flag=true;
			
		}
		return flag;
	}
	public static List<WebElement> getAlloptionsFromList(WebElement element){
		List<WebElement> options= null;
		try{
			if(element.getTagName().equals("select")){
				Select sel = new Select(element);
				options=sel.getOptions();
			}
		} catch(Exception e){
			
		}
		
		return options;
	}
	
	public static boolean switchwindow(String windowname){
		boolean window= false;
		try{
			Set<String> allwindow = driver.getWindowHandles();
			for (String win : allwindow) {
				driver.switchTo().window(win);
				if(driver.getTitle().contains(windowname))
					window = true;
					Reports.logPASS("Switched to Window-"+windowname);
				break;
			}
		} catch(Exception e){
			
		}
		return window;
	}
	
	public static boolean isElementPresent(WebElement element){
		boolean status = false;
		WebDriverWait wait = new WebDriverWait(driver, 60);
		if(wait.until(ExpectedConditions.visibilityOf(element)) != null){
			status = true;
		}
		return status;
	}
	
	public static void doubleClick(WebElement element){
		try{
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(200, TimeUnit.MICROSECONDS)
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			Actions act = new Actions(driver);
			act.doubleClick(element).build().perform();
			Reports.logPASS("Double Cliecked ");
		} catch(Exception e){
			
		}
	}
	
	public static String getSystemDateAndTime(String inFormat){
		String value = null;
		SimpleDateFormat sf = new SimpleDateFormat(inFormat);
		Calendar cal = Calendar.getInstance();
		
		value = sf.format(cal.getTime());
		return value;
		
	}
	
	public static String getValueFromFile(String filePath, String key) throws IOException{
		String value =null;
		BufferedReader bf=null;
		try{
			File file = new File(filePath);
			bf = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = bf.readLine())!=null){
				if(line.contains(key)){
					String[] split = line.split("=");
					value= split[1];
					break;
				}
			}
		} catch(Exception e){
			
		} finally{
			bf.close();
		}
		return value;
	}
	
	public static int getIndex(XSSFSheet sheet, String value){
		int index =0;
		try{
			Iterator<Row> rowIterator = sheet.iterator();
			mainloop:	
			while(rowIterator.hasNext()){
				int count =0;
				Row row = rowIterator.next();
				Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator= row.cellIterator();
				while(cellIterator.hasNext()){
					count++;
					Cell cel = (Cell) cellIterator.next();
					if(((org.apache.poi.ss.usermodel.Cell) cel).getStringCellValue().equalsIgnoreCase(value)){
						index = count-1;
						break mainloop;
					}
				}
			}
		} catch(Exception e){
			
		}
		return index;
	}

	public static WebElement getQuery(String locatorType, String locator)
	{
		WebElement query = null;
//		waitForpageLoad();
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(200, TimeUnit.MICROSECONDS)
				.ignoring(NoSuchElementException.class);
		
		try{
			if(locatorType.trim().equalsIgnoreCase("id"))
			{
				query = driver.findElement(By.id(locator));
				wait.until(ExpectedConditions.visibilityOf(query));
			}
			else if(locatorType.trim().equalsIgnoreCase("name"))
			{
				query = driver.findElement(By.name(locator));
				wait.until(ExpectedConditions.visibilityOf(query));
			}
			else if(locatorType.trim().equalsIgnoreCase("cssSelector"))
			{
				query = driver.findElement(By.cssSelector(locator));
				wait.until(ExpectedConditions.visibilityOf(query));
			}
			else if(locatorType.trim().equalsIgnoreCase("xpath"))
			{
				query = driver.findElement(By.xpath(locator));
				wait.until(ExpectedConditions.visibilityOf(query));
			} else {
				throw new NoSuchElementException();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return query;
	}
	
	public static void click(String locatorType, String locator, String msg){
		try{
			dowait(2000);
			mainquery = getQuery(locatorType,locator);
			mainquery.click();
			Reports.logPASS("Clicked on "+msg);
		} catch(Exception e){
			e.printStackTrace();
			Reports.logFAIL("Filed to Clicked on "+msg+e);
		}
	}
	
	public static String getAttributeValue(String locatorType, String locator, String attribute,String msg){
		String val =null;
		try{
			mainquery = getQuery(locatorType,locator);
			val = mainquery.getAttribute(attribute).toString();
			//Reports.logPASS("got attribute value as "+val);
		} catch(Exception e){
			e.printStackTrace();
			Reports.logFAIL("Filed to get attribute value ");
		}
		return val.toString();
	}
	
	public static void mouseOverTo(String locatorType, String locator, String msg){
		try{
			mainquery = getQuery(locatorType,locator);
			Actions act = new Actions(driver);
			act.moveToElement(mainquery).build().perform();
			Reports.logPASS("Mouse over to element "+msg);
		} catch(Exception e){
			e.printStackTrace();
			Reports.logFAIL("Filed Mouse over ");
		}
	}
	
	public static void setText(String locatorType, String locator, String text,String msg){
		try{
			dowait(2000);
			getQuery(locatorType,locator).sendKeys(text);
			Reports.logPASS("Entered text "+text+" for "+msg);
		} catch(Exception e){
			e.printStackTrace();
			Reports.logFAIL("Failed to Enter text "+text+" for "+msg);
		}
	}
	
	public static void javaScriptClick(String locatorType, String locator, String text){
		try{
			dowait(2000);
			mainquery = getQuery(locatorType,locator);
			JavascriptExecutor jexc =(JavascriptExecutor)driver;
			jexc.executeScript("arguments[0].click()", mainquery);
			Reports.logPASS("Clicked on "+text);
		} catch(Exception e){
			e.printStackTrace();
			Reports.logFAIL("Fail to click on "+text+e);
		}
	}
	
	public static String getText(String locatorType, String locator,String msg) throws Exception{
		String value=null;
		dowait(2000);
		try{
			 value=getQuery(locatorType,locator).getText();
		} catch(Exception e){
			e.printStackTrace();
			Reports.logFAIL("Failed get text for "+msg+e);
		}
		return value;
	} 
	
	public static List<WebElement> findElements(String locatorType, String locator){
		List<WebElement> elements =null;
		try{
			isElementPresent(driver.findElement(By.xpath(locator)));
			elements=driver.findElements(By.xpath(locator));
		} catch(Exception e){
			e.printStackTrace();
		}
		return elements;
	}
	public static void selectFromDropdown(String locatorType, String locator,String visibleText,String msg){
		try{
			dowait(2000);
			mainquery = getQuery(locatorType,locator);
			Select sel = new Select(mainquery);
			sel.selectByVisibleText(visibleText);
			Reports.logPASS("Selected option "+visibleText+" from "+msg);
			
		} catch(Exception e){
			Reports.logFAIL("Failed to select option "+visibleText+" from "+msg);
		}
	}
	
	public static void selectCheckBox(String locatorType, String locator,String msg){
		try{
			dowait(2000);
			if(!getQuery(locatorType,locator).isSelected()){
				getQuery(locatorType,locator).click();
				Reports.logPASS("Check box Selected "+msg);
			} 
		} catch(Exception e){
			Reports.logFAIL("Unable to select Check box "+msg);
		}
	}
	
	public static void assertEquals(Object obj1, Object obj2){
		try{
			assertEquals(obj1, obj2);
			Reports.logPASS("Verified objects are equal ");
		} catch(Exception e){
			Reports.logFAILWithOutScreenPrint("");
		}
		
	
	}
	public static boolean assertDisplayed(String locatorType, String locator, String msg) throws Exception{
		dowait(2000);
		boolean flag=false;
		try{
			if(getQuery(locatorType,locator).isDisplayed()){
				Reports.logPASS("Verified Displayed "+msg); flag=true;	
			}
			
		} catch(Exception e){
			Reports.logPASS("Fail to Verify Displayed "+msg);
		} return flag;
	}

}
