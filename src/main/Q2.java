package main;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This program is designed to test the sort functionality
 * for the table in the website: http://the-internet.herokuapp.com/tables
 * It will load the page and click on each sort field and verify that
 * the text is sorted as desired.
 * 
 * @author Constantine Caviris
 *
 */
public class Q2 {

static String dataTables = "http://the-internet.herokuapp.com/tables";
static WebDriver driver;

//These variables store the desired tags used in the lookups for the headers on each table.
final static String[] table1ExpectedColumns = {"//span[.='Last Name']", "//span[.='First Name']", "//span[.='Email']", "//span[.='Due']", "//span[.='Web Site']", "//span[.='Action']"};
final static String[] table2ExpectedColumns = {"last-name", "first-name", "email", "dues", "web-site", "action"};

    public static void main(String[] args) {
        // declaration and instantiation of objects/variables
    	System.setProperty("webdriver.gecko.driver","C:\\\\geckodriver.exe");
    	// launch Fire fox and direct it to the Base URL
    	driver = new FirefoxDriver();
        driver.get(dataTables);
        
        //call checkTable for table 1
        checkTable("table1", table1ExpectedColumns);
        
      //call checkTable for table 2
        checkTable("table2", table2ExpectedColumns);
        
        //Close browser
        driver.close();
    }
    
    /**
     * This method will find the given table. It will then iterate through all of the expected columns.
     * Each column will be clicked on one.
     * It will be verified that the values in the column are sorted as desired.
     * 
     * @param tableName - a string representing the id for the table to be tested
     * @param expectedColumns - values used to look up each of the expected columns in the table
     */
    public static void checkTable(String tableName, String[] expectedColumns) {
    	
    	//lookup the table elemet
    	WebElement table = driver.findElement(By.id(tableName));
    	//System.out.println(tableName);
    	//System.out.println(table.getText());
    	
    	//Go through all expected columns, click them, and verify that they are sorted
        for(int i = 0; i < expectedColumns.length; i++) {
        	WebElement columnHeader;
        	
        	//The lookup method for the headers is different depending on which table is used
        	if(expectedColumns[i].contains("span")) {
        		columnHeader = table.findElement(By.xpath(expectedColumns[i]));
        	} else {
        		columnHeader = table.findElement(By.className(expectedColumns[i]));
        	}
        	
        	columnHeader.click();
        	
        	//A two second sleep to allow the columns to update
        	sleep(2);
        	
        	System.out.println();
        	//System.out.println(table.getText());
        	
        	//load all of the rows of the table
        	List<WebElement> rows = table.findElements(By.tagName("tr"));
        	if (rows.isEmpty()) {
                System.out.println("No rows present in the table.");
            } 
        	//set the array size to one less than the number of rows, because we will not be looking at the header row
        	String [] sortedCol = new String[rows.size()-1];
        	int rowCount = 0;
        	
        	//iterate through all rows and find the desired cell for the column that was clicked
        	for (WebElement row : rows) {
        		//find all cell values in the row
            	List<WebElement> cells = row.findElements(By.tagName("td"));
            	if (rows.isEmpty()) {
                    System.out.println("No cells present in the row.");
                } 
            	int cellCount = 0;
            	//add the desired cell's text to the sortedCol array
            	for(WebElement cell: cells) {
            		if(cellCount%expectedColumns.length == i) {
            			sortedCol[rowCount]=cell.getText();
            			
            			//increment row counter
            			rowCount++;
            			//System.out.println("Parsed cell: " + cell.getText());
            		}
            		//increment cell counter.
            		cellCount++;
            	}
            	
            }
        	//Check to make sure that the desired column is sorted
        	checkArray(sortedCol, expectedColumns[i], tableName);
        }	
    }
    
    /**
     * This method will verify that the array passed in is sorted in increasing order
     * 
     * @param orderedArray - the array to check
     * @param colName - the column name for use in reporting
     * @param tableName - the table name for use in reporting
     */
    public static void checkArray(String[] orderedArray, String colName, String tableName) {
    	//Store the array, as it was passed in, to a string
    	String inputValues = Arrays.toString(orderedArray);
    	
    	boolean pass = true;
    	
    	//If the array is of money, the sort rule will be different
    	if(orderedArray[0].contains("$")) {
    		//iterate through all values of the string, except the first one, and verify that they are >= the one they proceded
    		for(int i = 1; i < orderedArray.length; i++) {
    			pass = pass && compareDollars(orderedArray[i-1], orderedArray[i]);
    		}
    		//print fail message if it did not pass
    		if(!pass) {
    			System.out.println("ERROR: The sorted lists for the column " + colName + " in the table " + tableName + " are out of order after being clicked once:");
    			System.out.println("Observed: " + inputValues);
    			System.out.println();
    		}
    	//to handle non-money columns
    	} else {
    		//Sort the array that was input
    		Arrays.sort(orderedArray);
    		//Verify that the sorted array, when converted to a string, matches the original array as a string. If not, print fail message
    		if(!Arrays.toString(orderedArray).equals(inputValues)) {
    			pass = false;
    			System.out.println("ERROR: The sorted lists for the column " + colName + " in the table " + tableName + " are out of order after being clicked once:");
    			System.out.println("Expected: " + Arrays.toString(orderedArray));
    			System.out.println("Observed: " + inputValues);
    			System.out.println();
    		}
    		
    	}
    	//If it did not fail, print a pass message
    	if(pass) {
    		System.out.println("The sorted lists for the column " + colName + " in the table " + tableName + " are in of order after being clicked once.");
    	}
    }
    
    /**
     * This method will take in two Strings, remove all $'s and .'s from them
     * and compare their numerical value.
     * 
     * Note: It is assumed that the strings passed in will have only
     * integers, $'s, and .'s. It is assumed that the decimal value will be to the nearest hundredth dollar / cent
     * 
     * @param smaller - the expected smaller value
     * @param bigger - the expected greater value
     * @return
     */
    public static boolean compareDollars(String smaller, String bigger) {
    	smaller = smaller.replace("$", "").replace(".", "");
    	bigger = bigger.replace("$", "").replace(".", "");

    	if(Integer.parseInt(smaller) > Integer.parseInt(smaller))
    		return false;
    	return true;
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