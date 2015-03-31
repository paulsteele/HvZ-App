package hvz.server;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Server Starter
 *
 */
@ComponentScan
@EnableAutoConfiguration
public class Server {
	private static Connection c;
    public static void main( String[] args ) {
    	ServerConfiguration.setPortNumber(8080);
    	c = DBHandler.init();
    	SpringApplication.run(Server.class, args);
    }
    
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
    
    public static User getUser(String username){
    	User user = null;
    	try {
        	if (DBHandler.getPlayer(username,  c) != null){
        		user = DBHandler.getPlayer(username, c);
        	}
    		else if (DBHandler.getAdmin(username, c) != null){
    			user = DBHandler.getAdmin(username, c);
    		}
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return user;

    	
    }
    
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
    
    public static void begin(String gamecode){
    	try {
			DBHandler.start(gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static boolean checkBegun(String gamecode){
    	try {
			return DBHandler.isStarted(gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    public static void createGame(String gamecode){
    	try {
			DBHandler.newGame(gamecode, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static String generateGamecode(){
    	boolean done = false;
    	String gamecode = null;
    	while (!done){
	    	gamecode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
	    	gamecode = 'G'+ gamecode.toUpperCase();
	    	//check if exists
	    	done = !checkGameExisits(gamecode);
    	}
    	return gamecode;
    }
    
    public static boolean checkGameExisits(String game){
    	try {
			return DBHandler.isGamecodeTaken(game, c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
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
}

