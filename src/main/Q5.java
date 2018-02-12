package main;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class will load the http://the-internet.herokuapp.com/dropdown page
 * print all drop down menu options available
 * and the select one at random.
 * 
 * @author Constantine Caviris
 *
 */
public class Q5 {
	static String dropdown = "http://the-internet.herokuapp.com/dropdown";
	
	static Random rand = new Random();
	
    public static void main(String[] args) {
    	selectDropdown();
    }
    
    /**
     * This method  will call selectDropdown(int index) with a negative value
     * Resulting in the selectDropdown selecting a random option
     */
    public static void selectDropdown() {
    	selectDropdown(-1);
    	
    }
    
    /**
     * This method will select the menu item determined by the index value.
     * If the index value is negative, a random one will be selected.
     * If the index value is within the desired range, that item will be selected.
     * If the index value is too large, a message will be printed to state that this is unexpect
     * and a random item will be selected.
     * 
     * @param index - The menu item to be selected. If negative, a random value will be selected. if too large, a messioge will be printed and a random value will be selected.
     */
    public static void selectDropdown(int index) {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.gecko.driver","C:\\\\geckodriver.exe");
    	
    	// launch Fire fox and direct it to the Base URL
    	WebDriver driver = new FirefoxDriver();
        driver.get(dropdown);
        
        //WebElement dropdown = driver.findElement(By.name("dropdown"));
        
        List<WebElement> dropdownItems = driver.findElements(By.cssSelector("option"));
        
        int selection;
        
        if (dropdownItems.isEmpty()) {
            System.out.println("No Checkbox present in the page.");
        } else {
        	//Print out how many items are shown in the dropdown
        	int size = dropdownItems.size();
        	System.out.println("Dropdown size = " + size);
        	
        	//Selector to either use a valid index or select a random one.
        	if(index < 0) {
        		System.out.println("A random dropdown selection will be made.");
        		//Because the 0th item is not selectable, we want to make sure it is omitted.
        		selection = rand.nextInt(size - 1) + 1;
        	} else if(index < size) {
        		System.out.println("The " + index + " item on the dropdown menu will be selected.");
        		selection = index;
        	} else {
        		System.out.println("Intex selection of " + index + " exceeds the observed number of entries. A random entry will be used instead.");
        		//Because the 0th item is not selectable, we want to make sure it is omitted.
        		selection = rand.nextInt(size - 1) + 1;
        	}
        	
        	//Itterate through all items in the dropdon and select the one that matches the desired index
            for (WebElement item : dropdownItems) {
            	System.out.println(item.getText());
            	if(dropdownItems.indexOf(item) == selection) {
            		item.click();
            		System.out.println("Clicked on item " + item.getText());
            	}
            	
            }
        }
        
        //Close page
        driver.close();
    }

}
