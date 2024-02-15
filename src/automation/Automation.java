package automation;

import java.util.ArrayList;

import java.util.Collections;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.openqa.selenium.Dimension;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.interactions.Actions;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.Test;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Automation {

	private WebDriver dr;

	private Map<String, Object> vars;
	JavascriptExecutor js;

	@BeforeMethod
	public void setUp() {
		dr = new FirefoxDriver();
		js = (JavascriptExecutor) dr;
		vars = new HashMap<String, Object>();
	}

	@AfterMethod
	public void tearDown() {
	    dr.quit();
	}

	
    @Test
    public void automation() throws Throwable {
    	dr.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(dr, Duration.ofSeconds(10));
		dr.get("https://www.makemytrip.com/flight/search?tripType=O&itinerary=DEL-BOM-20/02/2024&paxType=A-1_C-0_I-0&cabinClass=E&sTime=1707972341167&forwardFlowRequired=true&cmp=SEM|D|IF|G|Brand|IF_Brand_Exact-Ex_India|Brand_Exact_Ex_india|RSA|Regular|NewFunnel|645153719700&mpo=&semType=&intl=false");
		
		//get all the prices from the table
		
		// dr.findElement(By.cssSelector(".overlayCrossIcon")).click();
		
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[contains(@class,'blackText fontSize18 blackFont white-space-no-wrap clusterViewPrice')])")));

		List<WebElement> price = dr.findElements(By.xpath("(//div[contains(@class,'blackText fontSize18 blackFont white-space-no-wrap clusterViewPrice')])"));

		System.out.println(price.size());

		//put all the prices into array list and get the lowest prices

		ArrayList<Integer> prices=new ArrayList<Integer>();

		for(int i=0;i<price.size();i++){
			String priceText = price.get(i).getText();
		    
		    // Remove commas from the price text
		    String priceWithoutCommas = priceText.replaceAll(",", "");

		    try {
		        // Parse the modified price text as an integer and add it to the list
		        int priceInt = Integer.parseInt(priceWithoutCommas);
		        prices.add(priceInt);
		    } catch (NumberFormatException e) {
		        // Handle the case where parsing fails (e.g., non-numeric characters)
		        System.err.println("Failed to parse price: " + priceText);
		    }		
		}

		Integer minPrice = Collections.min(prices);

		System.out.println("Min Price is "+minPrice);


		//compare all the prices with lowest price and click the corresponding book button

		List<WebElement> allBookbtn = dr.findElements(By.xpath("//*[@id='content']/div/div[5]/div[5]/div[2]/div/div[2]/div[2]/div[7]/p/a"));

		Thread.sleep(3000);

		for(int i=0;i<price.size();i++){

			Integer priceInt1 = Integer.valueOf(price.get(i).getText().replace(",", ""));

			//String price1 = price.get(i).getText();

			System.out.println(priceInt1);

			if(priceInt1==minPrice){

				allBookbtn.get(i).click();

				break;

			}

		}

	 }

	
}


