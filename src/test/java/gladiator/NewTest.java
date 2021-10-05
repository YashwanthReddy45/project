package gladiator;
 

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NewTest {
	WebDriver driver; // intialisation  of webdriver
	Properties prop=new Properties(); //creation of object of an property file
	
	@Parameters("browser")
	 @BeforeTest 
	  public void beforeTest(String browser) throws Exception{
		prop.load(new FileInputStream("src/data.property"));  // to read data from property file
		if(browser.equals("chrome")) //verifying for chrome browser 
		  {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe"); // path of chrome.exe file
		  
		  driver=new ChromeDriver(); // intilisation of chrome driver
		  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS); //impilicit wait for 30sec
		  driver.manage().window().maximize(); //to maximize window
		  driver.manage().deleteAllCookies();  //to delete all cookies
		 }
		 else if(browser.equals("firefox")) //for firefox browser
		 {
			 System.setProperty("webdriver.gecko.driver", "src/test/resources/driver/geckodriver.exe"); //path of geckodriver.exe
			 driver=new FirefoxDriver(); //intilisation of firefox driver
			  driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS); //impilicit wait for 30sec
			  driver.manage().window().maximize();  //to maximize window
			  driver.manage().deleteAllCookies();  //to delete all cookies
		 }
		 else
		 {
			 System.out.println("Invalid browser"); // if user gives any other browser
		 }
	}
	 

	@BeforeMethod
	public void before() throws Exception // common method for all 
	{
		driver.get(prop.getProperty("url"));      //for opening the website
		driver.findElement(By.linkText("HOTELS")).click(); //for going to hotels section
		Thread.sleep(2000);		// to wait for 2sec
		driver.findElement(By.xpath("//div[@class='htl_location']/span[1]")).click(); //area where to enter location or hotel
		
		driver.findElement(By.xpath("//input[@name='txtCity']")).sendKeys(prop.getProperty("location")); //enter the required location
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='txtCity']")).sendKeys(Keys.ARROW_DOWN); //selection of city from drop down
		Thread.sleep(3000);
		Actions actions = new Actions(driver);
		driver.findElement(By.xpath("//input[@name='txtCity']")).sendKeys(Keys.ENTER);// after selecting press enter
		Thread.sleep(3000);
		driver.findElement(By.id("htl_dates")).click();// for selecting the check-in and checkout dates
		Thread.sleep(3000);
	}
	@Test(priority=8, enabled=true,description="Hotel Booking Succesful") //user able to succefully book the hotel goes to payment page
  public void TC51() throws Exception {
		
		//driver.findElement(By.xpath("//body[1]/div[12]/div[1]/table[1]/tbody[1]/tr[3]/td[2]/a[1]")).click();
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); // to select check-in date  
		Thread.sleep(3000);
		driver.findElement(By.xpath(prop.getProperty("checkoutdate"))).click(); // to select check out date
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("a#Adults_room_1_1_plus")).click();  // Add extra member if needed
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[normalize-space()='Done']")).click();  //confirmation of no. of guests
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value='Search']")).click();  // searching for hotel as per our requirements
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[1]//div[2]//div[1]//div[1]//a[1]//div[5]")).click(); //selection of the hotel from the list
		Thread.sleep(3000);
	
	Set<String>s=driver.getWindowHandles(); //a new tab opens this is to handle those tabs it gives out a set
	int n = s.size();
    List<String> l = new ArrayList<String>(n); // conversion of set to list
	  for(String i:s)
	  {
		  l.add(i);  //adding elements into list
		 
	  }
	  driver.switchTo().window(l.get(1)); // to switch to new opened tab
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div[@class='btnhcol']//a[@class='fill-btn'][normalize-space()='Book Now']")).click();//for booking room
		Thread.sleep(3000);
		driver.findElement(By.name("ddlGender")).click();  //user needs to select the gender 
		driver.findElement(By.xpath("//option[@value='Mrs.']")).click();  //select the title from drop down
		Thread.sleep(3000);
		driver.findElement(By.id("txtFirstName1")).sendKeys(prop.getProperty("fname")); //entering firstname
        Thread.sleep(3000);	
        driver.findElement(By.id("txtLastName1")).sendKeys(prop.getProperty("lname")); //entering lastname
        Thread.sleep(3000);	
        driver.findElement(By.xpath("//input[@id='txtEmailId']")).sendKeys(prop.getProperty("email")); //entering valid email id
        Thread.sleep(3000);
        driver.findElement(By.xpath("//input[@id='txtCPhone']")).sendKeys(prop.getProperty("ph.no"));  //entering valid ph. no.
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[@class='coonpayment']")).click(); //clicking on continue payment
        Thread.sleep(3000);
//driver.findElement(By.id("chkAgree1")).clear();
        String d=driver.findElement(By.xpath("//div[@ng-click='CardValidation(RoomDetails.Rooms.engine)']")).getText();  //getting text from the display
       Thread.sleep(3000);
         Assert.assertEquals(d,"Make Payment"); //verifying payments page is avilable or not
         


  }
  

  @AfterTest
  public void afterTest() {
	  driver.quit(); // to close the browser
  }
  
  
  @Test(priority=2,enabled=true,description="Invalid location") // user enters the invalid location
  public void TC45() throws Exception
  
  {
	  driver.get(prop.getProperty("url"));  //for opening the website
	  driver.findElement(By.linkText("HOTELS")).click();  //for going to hotels section
		Thread.sleep(2000);		
		driver.findElement(By.xpath("//div[@class='htl_location']/span[1]")).click(); //area where to enter location or hotel
		
		driver.findElement(By.xpath("//input[@name='txtCity']")).sendKeys("eqwedarfetgw4gw");//enter the a invalid location
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='txtCity']")).sendKeys(Keys.ARROW_DOWN); //to select from drop down 
		Thread.sleep(3000);
		Actions actions = new Actions(driver);
		driver.findElement(By.xpath("//input[@name='txtCity']")).sendKeys(Keys.ENTER); // to select enter
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value='Search']")).click(); // to search for hotel
		Thread.sleep(3000);
		String k=driver.switchTo().alert().getText();   // to get the text from the alert
		driver.switchTo().alert().accept();  // to continue we need to press ok in alert
		Assert.assertEquals(k,"Please select city location name"); //verifying with browser
		Thread.sleep(3000);
  }
  @Test(priority=3,enabled=true,description="Check-in & Check-out dates same") //if check-in and checkout dates are same
  public void TC46() throws Exception
  {
	 
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); //entering the checkin date
		Thread.sleep(3000);
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); //entering the checkout date
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value='Search']")).click(); //searching for hotels
		Thread.sleep(3000);
		String k=driver.switchTo().alert().getText(); //getting text from alert  
		driver.switchTo().alert().accept(); // accept the alert
		Assert.assertEquals(k,"checkin and checkout cannot be same"); //verifying with alert message displayed
		Thread.sleep(3000);
	  
  }
  @Test(priority=1,enabled=true,description="User able to select a room") // user is able to select a room
  public void TC44() throws Exception
  {
	
	driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); //entering checkin date
	Thread.sleep(3000);
	driver.findElement(By.xpath(prop.getProperty("checkoutdate"))).click(); //entering checkout date
	Thread.sleep(3000);
	
	driver.findElement(By.xpath("//input[@value='Search']")).click(); //searching hotel
	
	Thread.sleep(3000);
	String a=driver.getTitle(); // get title from the page 
	Assert.assertEquals(a,"Hotel List");//verifying the page is displayed or not
  }
  @Test(priority=4,enabled=true,description="User didnot enter first name")  //if user didnot enter first name
  public void TC47() throws Exception
  {
	 
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); //enter checkin date
		Thread.sleep(3000);
		driver.findElement(By.xpath(prop.getProperty("checkoutdate"))).click(); //enter checkout date
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@value='Search']")).click(); //searching for hotel
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[1]//div[2]//div[1]//div[1]//a[1]//div[5]")).click(); //selecting a hotel from the list
		Thread.sleep(3000);
	
	Set<String>s=driver.getWindowHandles(); // handling multiple tabs
	int n = s.size();
  List<String> l = new ArrayList<String>(n); //creating a new list
	  for(String i:s)
	  {
		  l.add(i); //adding elements into list
		 
	  }
	  driver.switchTo().window(l.get(1)); // switching to window 
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div[@class='btnhcol']//a[@class='fill-btn'][normalize-space()='Book Now']")).click(); //booking a hotel
		Thread.sleep(3000);
		driver.findElement(By.name("ddlGender")).click(); //clicking on gender
		driver.findElement(By.xpath("//option[@value='Mrs.']")).click(); // selecting title from drop down
		Thread.sleep(3000);
		driver.findElement(By.id("txtLastName1")).sendKeys(prop.getProperty("lname"));// entering the last name
		Thread.sleep(3000);	
		driver.findElement(By.xpath("//input[@id='txtEmailId']")).sendKeys(prop.getProperty("email")); //entering the valid mail id
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='txtCPhone']")).sendKeys(prop.getProperty("ph.no")); // entering the ph.no. 
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='coonpayment']")).click(); // clicking to continue payment
		Thread.sleep(3000);
		String k=driver.switchTo().alert().getText();//getting text from alert
		driver.switchTo().alert().accept(); // accept the alert to continue
		Assert.assertEquals(k,"please enter the first name of guest 1"); //verifying the title
		Thread.sleep(3000);
		  
  }
  @Test(priority=5,enabled=true,description="User didnot enter a mail or proper mail id") //user didnot enter mail id
  public void TC48() throws Exception
  {
	  
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); //enter checkin date
		Thread.sleep(3000); 
		driver.findElement(By.xpath(prop.getProperty("checkoutdate"))).click(); //enter checkout date
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@value='Search']")).click(); //searching for hotel
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[1]//div[2]//div[1]//div[1]//a[1]//div[5]")).click(); //selecting a hotel from the list
		Thread.sleep(3000);
	
	Set<String>s=driver.getWindowHandles(); // handling multiple tabs
	int n = s.size();
  List<String> l = new ArrayList<String>(n);  //creating a new list
	  for(String i:s)
	  {
		  l.add(i);
		 
	  }
	  driver.switchTo().window(l.get(1));
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div[@class='btnhcol']//a[@class='fill-btn'][normalize-space()='Book Now']")).click();  //booking a hotel
		Thread.sleep(3000);
		driver.findElement(By.name("ddlGender")).click(); //clicking on gender
		driver.findElement(By.xpath("//option[@value='Mrs.']")).click(); // selecting title from drop down
		Thread.sleep(3000);
		driver.findElement(By.id("txtFirstName1")).sendKeys(prop.getProperty("fname"));   // entering the first name
Thread.sleep(3000);	
driver.findElement(By.id("txtLastName1")).sendKeys(prop.getProperty("lname")); // entering the last name
Thread.sleep(3000);	  
driver.findElement(By.xpath("//input[@id='txtCPhone']")).sendKeys(prop.getProperty("ph.no"));    // entering the ph.no.
Thread.sleep(3000);
driver.findElement(By.xpath("//div[@class='coonpayment']")).click();   // clicking to continue payment
Thread.sleep(3000);
String k=driver.switchTo().alert().getText();   //getting text from alert
driver.switchTo().alert().accept();     // accept the alert to continue
Assert.assertEquals(k,"please enter the valid emailid"); //verifying the displayed message
Thread.sleep(3000);
  }
  
  @Test(priority=6,enabled=true,description="User didnot enter last name") //user did not enter the last name
  public void TC49() throws Exception
  {
	 
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click(); //enter checkin date
		Thread.sleep(3000);
		driver.findElement(By.xpath(prop.getProperty("checkoutdate"))).click();  //enter checkout date
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@value='Search']")).click();  //searching for hotel
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[1]//div[2]//div[1]//div[1]//a[1]//div[5]")).click(); //selecting a hotel from the list
		Thread.sleep(3000);
	
	Set<String>s=driver.getWindowHandles(); // handling multiple tabs
	int n = s.size();
  List<String> l = new ArrayList<String>(n); //creating a new list
	  for(String i:s)
	  {
		  l.add(i);
		 
	  }
	  driver.switchTo().window(l.get(1));
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div[@class='btnhcol']//a[@class='fill-btn'][normalize-space()='Book Now']")).click();  //booking a hotel
		Thread.sleep(3000);
		driver.findElement(By.name("ddlGender")).click();  //clicking on gender
		driver.findElement(By.xpath("//option[@value='Mrs.']")).click();   // selecting title from drop down
		Thread.sleep(3000);
		driver.findElement(By.id("txtFirstName1")).sendKeys(prop.getProperty("fname")); // entering the first name
Thread.sleep(3000);	
driver.findElement(By.xpath("//input[@id='txtEmailId']")).sendKeys(prop.getProperty("email")); // entering the email
Thread.sleep(3000);
driver.findElement(By.xpath("//input[@id='txtCPhone']")).sendKeys(prop.getProperty("ph.no")); // entering the ph.no.
Thread.sleep(3000);
driver.findElement(By.xpath("//div[@class='coonpayment']")).click();  // clicking to continue payment
Thread.sleep(3000);
String k=driver.switchTo().alert().getText();   //getting text from alert
driver.switchTo().alert().accept();           // accept the alert to continue
Assert.assertEquals(k,"please enter the last name of guest 1"); //verifying the displayed message
Thread.sleep(3000);
	  
  }
  @Test(priority=7,enabled=true,description="User didnot enter mobile number") //user didnot enter the mobile number
  public void TC50() throws Exception
  {
	 
		driver.findElement(By.xpath(prop.getProperty("checkindate"))).click();  //enter checkin date 
		Thread.sleep(3000);
		driver.findElement(By.xpath(prop.getProperty("checkoutdate"))).click();  //enter checkout date
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@value='Search']")).click();   //searching for hotel
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[1]//div[2]//div[1]//div[1]//a[1]//div[5]")).click();   //selecting a hotel from the list
		Thread.sleep(3000);
	
	Set<String>s=driver.getWindowHandles();   // handling multiple tabs
	int n = s.size();
  List<String> l = new ArrayList<String>(n);   //creating a new list
	  for(String i:s)
	  {
		  l.add(i);
		 
	  }
	  driver.switchTo().window(l.get(1));
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//div[@class='btnhcol']//a[@class='fill-btn'][normalize-space()='Book Now']")).click();  //booking a hotel
		Thread.sleep(3000);
		driver.findElement(By.name("ddlGender")).click();   //clicking on gender
		driver.findElement(By.xpath("//option[@value='Mrs.']")).click();   // selecting title from drop down
		Thread.sleep(3000);
		driver.findElement(By.id("txtFirstName1")).sendKeys(prop.getProperty("fname"));  // entering the first name
Thread.sleep(3000);	
driver.findElement(By.id("txtLastName1")).sendKeys(prop.getProperty("lname"));  // entering the last name
Thread.sleep(3000);	
driver.findElement(By.xpath("//input[@id='txtEmailId']")).sendKeys(prop.getProperty("email"));  // entering the email
Thread.sleep(3000);
driver.findElement(By.xpath("//div[@class='coonpayment']")).click(); // clicking to continue payment
Thread.sleep(3000);
String k=driver.switchTo().alert().getText();     //getting text from alert
driver.switchTo().alert().accept();                 // accept the alert to continue
Assert.assertEquals(k,"please enter the  valid phone number");  //verifying the displayed message
Thread.sleep(3000);
  }
  @AfterMethod
  
  public void after()
  {
	  Set<String>s=driver.getWindowHandles(); //to handle mutliple tabs
		int n = s.size();
		List<String> l = new ArrayList<String>(s);    //creating a new list 
		
		if(n>1)                   
		{
			driver.switchTo().window(l.get(1)); //switch to new tab
			driver.close();                 // close the tab
			driver.switchTo().window(l.get(0));  //switch to tab
			driver.manage().deleteAllCookies();   // delete all cookies
		}
		
}

}




