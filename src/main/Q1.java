package main;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This program is designed to test the login functionality
 * for the website: http://the-internet.herokuapp.com/login
 * It will login and log out with valid credentials
 * Then it will attempt to login with invalid  credentials
 * Finally it will login, close the window without logging out,
 * and reopen the window to check that the user i no longer
 * logged in.
 * 
 * @author Constantine Caviris
 *
 */
public class Q1 {

static String loginURL = "http://the-internet.herokuapp.com/login";

static String goodUserName = "tomsmith";
static String goodPassword = "SuperSecretPassword!";

static String badUserName = "badUserName";
static String badPassword = "myBirthday";


    public static void main(String[] args) {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.gecko.driver","C:\\\\geckodriver.exe");

    	loginAndLogout(goodUserName, goodPassword, true);
    	loginAndLogout(badUserName, badPassword, false);
    	loginAndCloseWindow(goodUserName, goodPassword);
    }
    
    
    /**
     * This method will perform the following actions:
     * Launch a browser to the loginURL and verify that the user is not logged in
     * It will enter the given user name and password
     * and submit them.
     * It will check to see if the user got in or not based on the expected result.
     * If the user is logged in, the user will log out and
     * it will be verified if they logged out or not.
     * Finally, the window will be closed.
     * 
     * @param username - the user name to be used on login
     * @param password - the password to be used on login
     * @param activeUser - the expected result for if the user name 
     * 						and password credentials able to login
     */
    public static void loginAndLogout(String username, String password, boolean activeUser) {
    	// launch Fire fox and direct it to the Base URL
    	WebDriver driver = new FirefoxDriver();
    	
        driver.get(loginURL);
        
        //Before logging in, verify that the user is not logged in
        checkLoggedIn(driver, false);
        
        //Find username field and enter user name text
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        
        //Find password field and enter password text
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        
        
        element.submit();
        
        //After attempting to logging in, verify that the user logged in.
        boolean logedIn = checkLoggedIn(driver, activeUser);
        
        //If the user was expected to log in, log out
        if(logedIn) {
        	//find and click the logout button
        	element = driver.findElement(By.className("button"));
        	element.click();
        	
        	//verify that the user is logged out
        	checkLoggedIn(driver, false);
        }
        
        //close the browser window
        driver.close();

    }
    
    /**
     * This method will attempt to log in
     * After verifying that the user was logged in, it will close the browser window
     * while the user is still logged in.
     * Afterwards, it will reopen the browser window to the login page
     * and verify that the user is not logged in.
     * 
     * Note: Only use username and password values that are expected to
     * be able to log in.
     * 
     * @param username - the user name to be used on login
     * @param password - the password to be used on login
     */
    public static void loginAndCloseWindow(String username, String password) {
    	// launch Fire fox and direct it to the Base URL
    	WebDriver driver = new FirefoxDriver();
        driver.get(loginURL);
        
        //check that the user is not logged in
        checkLoggedIn(driver, false);
        
        //Find username field and enter user name text
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        
        //Find password field and enter password  text
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        
        //Submit the form
        element.submit();
        
        //Check that the user is logged
        checkLoggedIn(driver, true);
        
        //Close the browser window
        driver.close();
        

    	// launch Fire fox and direct it to the Base URL
    	driver = new FirefoxDriver();
        driver.get(loginURL);
        
        //check that the user is not logged in
        checkLoggedIn(driver, false);

        //Close the browser window
        driver.close();

    }
    
    /**
     * This program checks if the user is logged in and
     * prints out a message to the console to note if the
     * user is logged in or not and if this is expected.
     * 
     * @param driver - the driver object for the page to be tested
     * @param expected - if the user is expected to be logged in or not
     * @return - true if the user is logged in and false otherwise
     */
    public static boolean checkLoggedIn(WebDriver driver, boolean expected) {
    	
    	//A sleep time of 2 seconds will ensure that the browser has enough time to update.
    	sleep(2);
    	
    	
    	boolean logedIn = true;
    	
    	//find the subheader text and store to a string. This will be used to verify
    	WebElement element = driver.findElement(By.className("subheader"));
    	String text = element.getText();
    	
    	String message = "";
    	
    	//If the text matches the expected login string
    	if(text.equals("This is where you can log into the secure area. Enter tomsmith for the username and SuperSecretPassword! for the password. If the information is wrong you should see error messages.")) {
    		message = "The user is not logged into the Secure Area.";
    		logedIn = false;
    		if(expected) {
    			message = "Script Failure: " + message + " The user is expected to be logged in.";
    		} else {
    			message = "Pass checkpoint: " + message + " This is expected.";
    		}
    	//If the text matches the expected not logged in string
    	} else if(text.equals("Welcome to the Secure Area. When you are done click logout below.")) {
    		message = "The user is logged into the Secure Area.";
    		if(!expected) {
    			message = "Script Failure: " + message + " The user is expected to not be logged in.";
    		} else {
    			message = "Pass checkpoint: " + message + " This is expected.";
    		}
    	//If the text is unknown, it will be printed to the console for manual verification.
    	} else {
    		logedIn = false;
    		message = "Unexpected Value: The script is unable to authenticate if the user is logged in or not. The value 'subheader' had changed and requires manual verification. ";
    		if(expected) {
    			message = message + " The user is expected to be logged in. The verification text reads: " + text;
    		} else {
    			message = message + " The user is not expected to be logged in. The verification text reads: " + text;
    		}
    	}
    	
    	//print the message and return
    	System.out.println(message);
    	return logedIn;
    }

    /**
     * A simple sleep function that will busy wait for
     * a desired number of seconds
     * 
     * @param time - desired sleep time in seconds
     */
    public static void sleep(int time) {
    	
    	long endTime = System.currentTimeMillis() + (time * 1000);
    	
    	while(System.currentTimeMillis() < endTime) {
    	}
    }
    
}