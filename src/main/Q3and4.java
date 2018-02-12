package main;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This program is designed to scroll for a set period of time on the
 * http://the-internet.herokuapp.com/infinite_scroll website and to test the
 * ckeck boxes on the http://the-internet.herokuapp.com/checkboxes website.
 * 
 * @author Constantine Caviris
 *
 */
public class Q3and4 {

static String scrollURL = "http://the-internet.herokuapp.com/infinite_scroll";
static String checkboxURL = "http://the-internet.herokuapp.com/checkboxes";

static Random rand = new Random();


    public static void main(String[] args) {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.gecko.driver","C:\\\\geckodriver.exe");

    	scroll(15);
    	checkbox();


    	
    }
    
    
    
    public static void checkbox() {
    	// launch Fire fox and direct it to the Base URL
    	WebDriver driver = new FirefoxDriver();
        driver.get(checkboxURL);
        
        //Find all checkbox elements on the page
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type=checkbox]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Print a message if no checkboxes are found
        if (checkboxes.isEmpty()) {
            System.out.println("No Checkbox present in the page");
        } else {
        	//Itterate through all checkboxes
            for (WebElement checkbox : checkboxes) {
            	//Print if the checkbox is initially selected or not
            	if(checkbox.isSelected()) {
                    String text=(String) js.executeScript("return arguments[0].nextSibling.textContent.trim();", checkbox);
                    System.out.println(text + " is selected initially.");
            	} else {
                    String text=(String) js.executeScript("return arguments[0].nextSibling.textContent.trim();", checkbox);
                    System.out.println(text + " is not selected initially.");
            	}
            	
            	//create a random intenger between 1 and 10 to determine how many times to click the checkbox
            	int count = rand.nextInt(10)+1;
            	
            	//Click the checkbox count times. After each click, print if the text box is selected or not and how many times it was clicked.
            	for(int i = 1; i <= count; i++) {
            		checkbox.click();
            		if(checkbox.isSelected()) {
            			String text=(String) js.executeScript("return arguments[0].nextSibling.textContent.trim();", checkbox);
            			System.out.println("Click number " + i + ": " + text + " is selected.");
            		} else {
            			String text=(String) js.executeScript("return arguments[0].nextSibling.textContent.trim();", checkbox);
            			System.out.println("Click number " + i + ": " + text + " is not selected.");
            		}
            	}

            }
        }
        //Close the wndow
        driver.close();
    }
    
    /**
     * This method will open the scrollURL page and scroll for the desired time
     * 
     * @param time - the time desired to scroll, in seconds
     */
    public static void scroll(int time) {
    	
    	// launch Fire fox and direct it to the Base URL
    	WebDriver driver = new FirefoxDriver();
        driver.get(scrollURL);
        
    	try {
    	//The System.CurrentTimeMillis and the time value are used to comput the end time
    	long endTime = System.currentTimeMillis() + (time * 1000);
    	
    	//A robot is used to press the down key and then release it after the set time expires
    	Robot robot = new Robot();
    	while(System.currentTimeMillis() < endTime){
    		robot.keyPress(KeyEvent.VK_DOWN);
    	}
    	robot.keyRelease(KeyEvent.VK_DOWN);
    	} catch (AWTException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    	}
    	
    	//Close page
    	driver.close();
    }
    
    /**
     * A simple sleep function that will busy wait for
     * a desired number of seconds
     * 
     * @param time - desired sleep time in seconds
     */
    public static void sleep(int time) {
    	long endTime = System.currentTimeMillis() + (time * 1000);
    	while(System.currentTimeMillis() < endTime) {}
    }
    
}