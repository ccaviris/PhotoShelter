
/**
 * This program is designed to test the login functionality
 * for the website: http://the-internet.herokuapp.com/login
 * It will login and log out with valid credentials
 * Then it will attempt to login with invalid  credentials
 * 
 * @author Constantine Caviris
 *
 */

//this variable is needed in order to use Mocha, Chai assert
var assert = require('chai').assert

//These are valid login credentials
var goodUserName = 'tomsmith';
var goodPassword = 'SuperSecretPassword!';

//These are invalid loging credentials
var badUserName = 'badUserName';
var badPassword = 'myBirthday';

//These are the values for the 'h2' header when the user is logged in or out
var loggedIn = 'Secure Area';
var loggedOut = 'Login Page';

//The URL for the application under test
var loginURL = "http://the-internet.herokuapp.com/login";

//A loggin attempt with valid credentials
login(goodUserName, goodPassword, loggedIn);

//A loggin attempt with invalid credentials
login(badUserName, badPassword, loggedOut);


/*
 * This method will perform the following actions:
 * Launch a browser to the loginURL and verify that the user is not logged in
 * It will enter the given user name and password
 * and submit them.
* It will check to see if the user got in or not based on the expected result.
* If the user is expected to be logged in, the user will log out and
* it will verified if they logged out or not.
* Finally, the window will be closed.
* 
* @param user - the user name to be used on login
* @param pass - the password to be used on login
* @param result - the expected value for the h2 tag after attempting to log in 
 * 						with user and pass credentials provided
*/
function login(user, pass, result){
	//Initialize a message depening on if the user's credentials are expected to be valid or not
	var description = 'Verify that a valid user may log in';
	if(result.valueOf() == loggedOut.valueOf()){
		description = 'Verify that an invalid user may not log in';
	}
	
	describe('Verify that a user login works as expected: ', function(){
	it(description, function(){
		//Launch the browser to the desired URL
		browser.url(loginURL);
		
		//Get the text for the h2 tag and assert that the value shows that the user is not logged in
		var title = browser.getText('h2');
		assert.equal(title, loggedOut);
		
		//Enter username and password credentials
		browser.setValue('#username', user);
		browser.setValue('#password', pass);
		
		//Click on the submit button
		browser.click('button=Login');
		
		//Get the text for the h2 tag and assert that the value shows that it matches the var result that was passed in
		var title = browser.getText('h2');
		assert.equal(title, result);
	});
	
	//If the var result value passed in matches the loggedIn value, the script will attempt a loggout
	if(result.valueOf() == loggedIn.valueOf()){
		it('Verify that a valid user may log out', function(){
			//Click the loggout button
			browser.click('a=Logout');
			
			//Get the text for the h2 tag and assert that the value shows that the user is not logged in
			var title = browser.getText('h2');
			assert.equal(title, loggedOut);
		});
	}
});
};