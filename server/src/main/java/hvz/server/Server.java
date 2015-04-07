package hvz.server;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Server Starter
 * Also holds functions that talk to db handler.
 */

@ComponentScan
@EnableAutoConfiguration
public class Server {
	
	private static Connection c; //The connection to the database
	
	/**
	 * Sets up Server info and then starts the server
	 */
    public static void main( String[] args ) {
    	//setup basic server configuration values
    	ServerConfiguration.setPortNumber(8080);
    	//obtain connection to database
    	c = DBHandler.init();
    	//make sure connection was created correctly
    	try {
			if (!c.isClosed()){
				//actually begin the server
				SpringApplication.run(Server.class, args);
			}
				
		} catch (SQLException e) {
			System.out.println("Error encountered in database initilization");
			e.printStackTrace();
		}
    }
    
    /**
     * Checks in a game to see if a feedcode is taken
     * returns true if it is found
     */
    public static boolean checkRegisteredFeedcode(String feedcode, String gamecode){
    	boolean found = false;
    	//if either a user is found in the admin database or the player database return false
    	try {
			found = DBHandler.isFeedcodeTaken(feedcode, gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return found;
    }
    
    /**
     * Checks in the entire server to see if a username is take
     * returns true if it is found
     */
    public static boolean checkRegisteredUsername(String username){
    	boolean found = false;
    	try {
			found = DBHandler.isUsernameTaken(username, c); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return found;
    }
    
    /**
     * Takes a user and stores it the database
     */
    public static boolean registerUser(User user, String password) {
    	try {
    		//Add admin or player
	    	if (user.isAdmin){
	    		DBHandler.addAdmin(user.username, user.feedcode, user.gamecode, c);
	    	}
	    	else{
	    		Player p = (Player) user;
	    		int zombie = 0;
	    		if (p.isZombie)
	    			zombie = 1;
	    		DBHandler.addPlayer(p.username, zombie , p.feedcode, user.gamecode, c);
	    	}
	    	//Add password for user
	    	DBHandler.setPassword(user.username, password, c);
    	}
    	catch (SQLException e){
    		return false;
    	}
    	return true;
    }
    
    /**
     * Takes a user and a given password and checks to see if the password matches
     */
    public static User loginUser(User user, String password){
    	if (user == null)
    		return null;
    	try{
    		String dbpass = DBHandler.getPassword(user.username, c);
    		String apppass = password;
    		if (dbpass.equals(apppass) == true){
    			return user; //successful
    		}
    		else {
    			return null;
    		}
    	}
    	catch (SQLException e){
    		
    	}
    	return null;
    }
    
    /**
     * returns a user, via a username 
     */
    public static User getUser(String username){
    	User user = null;
    	try {
        	if (DBHandler.getPlayerU(username,  c) != null){
        		user = DBHandler.getPlayerU(username, c);
        	}
    		else if (DBHandler.getAdminU(username, c) != null){
    			user = DBHandler.getAdminU(username, c);
    		}
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return user;
    }
    
    /**
     * returns a user, via a gamecode and feedcode
     */
    public static User getUser(String feedcode, String gamecode){
    	User user = null;
    	try {
        	if (DBHandler.getPlayerFG(feedcode, gamecode, c) != null){
        		user = DBHandler.getPlayerFG(feedcode, gamecode, c);
        	}
    		else if (DBHandler.getAdminFG(feedcode, gamecode, c) != null){
    			user = DBHandler.getAdminFG(feedcode, gamecode, c);
    		}
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return user;
    }
    
    /**
     * Gets all users in a game, (Admins too!)
     */
    public static User[] getAllUsers(String gamecode) {
    	User[] rets = null;
    	try {
        	User[] users = DBHandler.getAllUsers(gamecode, c);
        	User[] admins = DBHandler.getAllAdmin(gamecode, c);
        	rets = new User[users.length + admins.length];
        	int i = 0; //rets index
        	for (int j = 0; j < users.length; j++, i++){
        		rets[i] = users[j];
        	}
        	for (int j = 0; j < admins.length; j++, i++){
        		rets[i] = admins[j];
        	}
        	
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return rets;
    }
    
    /**
     * starts a given game
     */
    public static void begin(String gamecode){
    	try {
			DBHandler.start(gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Checks to see if a given game has started
     */
    public static boolean checkBegun(String gamecode){
    	try {
			return DBHandler.isStarted(gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    /**
     * Creates a new game
     */
    public static void createGame(String gamecode){
    	try {
			DBHandler.newGame(gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * generates a new gamecode that is guaranteed to be unique
     * @return
     */
    public static String generateGamecode(){
    	boolean done = false;
    	String gamecode = null;
    	while (!done){
	    	gamecode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
	    	gamecode = ServerConfiguration.gamePrefix + gamecode.toUpperCase();
	    	//check if exists
	    	done = !checkGameExisits(gamecode);
    	}
    	return gamecode;
    }
    
    /**
     * Checks to see if a gamecode exists
     */
    public static boolean checkGameExisits(String game){
    	try {
			return DBHandler.isGamecodeTaken(game, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    /**
     * generate a revive code
     */
    public static String generateRevivecode(){
        String revivecode = null;
        revivecode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
        revivecode = ServerConfiguration.revivePrefix + revivecode.toUpperCase();
        return revivecode;
    }
    /**
     * retrieve list of all games
     */
    public static String[] getAllGames(){
    	String[] games = null;
    	try {
			games = DBHandler.getAllGames(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return games;
    }
    
    /**
     * given a username, change its feedcode and/or gamecode to the new values
     */
    public static void updateUser(String username, String feedcode, String gamecode, boolean admin) {
    	if (feedcode != null){
    		try {
				DBHandler.changeFeedCode(username, feedcode	, admin, c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if (gamecode != null){
    		try {
				DBHandler.changeGamecode(username, gamecode, admin, c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * perform a tag, returns false if cannibal tags, or users don't exist
     */
    public static boolean tag(String tagger, String tagged, String gamecode){
    	return false;
    }
}

