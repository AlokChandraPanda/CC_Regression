package ObjectRepository;

public class OR_Abhibus {
	
	//LoginPage
	public static String redBustooltip = "//img[contains(@title,'Fastest Online bus ticket booking site')]";
	public static String txtLeavingFrom = "//input[@id='source']";
	public static String txtGoingTo = "//input[@id='destination']";
	public static String txtDateOfJourney = "//input[@value='Date of Journey']";
	public static String txtDateOfReturn = "//input[@value='Date of Return (optional)']";
	public static String btnSearch = "//a[@class='btnab icosearch']";
	public static String btnLater = "//button[text()='Later']";
	public static String frmDatePicker = "//div[@id='ui-datepicker-div']";
	public static String lnkDynamicDatelink = "//td[@data-month='<m>']/a[text()='<d>']";
	public static String txtDynamicJourneyselect ="//li[contains(@class,'ui-menu-item')][text()='<Journey>']";
	
	//HomePage
	public static String btnSelectSeats = "//a/span[text()='Select Seat']";
	public static String btnAvailableSeat1 = "(//a[@class='tooltip tooltipstered'])[1]";
	public static String btnBookReturn = "//input[@value='Book Return ' and @type='submit']";
	public static String drpBoardingPoint = "//select[@name='boardingpoint_id']";
	public static String txtTotalFare = "//span[@id='totalfare']";
	public static String btnContinuePayment = "//input[@value='Continue to Payment ' and @type='submit']";
	
	//payment detail page
	public static String txtOnwordJourney = "//*[text()='Onward Journey Details']";
	public static String txtNetPayable = "//*[@id='NetAmountmsg']";
	public static String boxNotification = "//*[@id='gmg_wp_notif']";
	

}
