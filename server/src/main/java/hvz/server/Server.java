package hvz.server;

import java.sql.Connection;
import java.sql.SQLException;

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
    public static void main( String[] args ) {
    	ServerConfiguration.setPortNumber(8080);
    	DBHandler.init();
    	SpringApplication.run(Server.class, args);
    }
    
    public static boolean checkRegistered(String feedcode){
    	boolean found = false;
    	Connection c = DBHandler.connect();
    	//if either a user is found in the admin database or the player database return false
    	try {
			if (DBHandler.getPlayer(feedcode, c) != null)
				found = true;
			else if (DBHandler.getAdmin(feedcode, c) != null)
				found = true;
	    	DBHandler.disconnect(c);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return found;
    }
    
    public static boolean registerUser(User user, String password) {
    	Connection c = DBHandler.connect();
    	try {
    		//Add admin or player
	    	if (user.isAdmin){
	    		DBHandler.addAdmin(user.username, user.feedcode, c);
	    	}
	    	else{
	    		Player p = (Player) user;
	    		int zombie = 0;
	    		if (p.isZombie)
	    			zombie = 1;
	    		DBHandler.addPlayer(p.username, zombie , p.feedcode, c);
	    	}
	    	//Add password for user
	    	DBHandler.setPassword(user.feedcode, password, c);
	    	DBHandler.disconnect(c);
    	}
    	catch (SQLException e){
    		return false;
    	}
    	return true;
    }
    
    public static User loginUser(User user, String password){
    	try{
    		Connection c = DBHandler.connect();
    		if (DBHandler.getPassword(user.feedcode, c).equals(password)){
    			DBHandler.disconnect(c);
    			return user; //successful
    		}
    		else {
    			DBHandler.disconnect(c);
    			return null;
    		}
    	}
    	catch (SQLException e){
    		
    	}
    	return null;
    }
    
    public static User getUser(String feedcode){
    	Connection c = DBHandler.connect();
    	User user = null;
    	try {
        	if (DBHandler.getPlayer(feedcode, c) != null){
        		user = DBHandler.getPlayer(feedcode, c);
        	}
    		else if (DBHandler.getAdmin(feedcode, c) != null){
    			user = DBHandler.getAdmin(feedcode, c);
    		}
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	DBHandler.disconnect(c);
    	return user;

    	
    }
    
    public static User[] getAllUsers(){
    	return null;
    }
    
    public static void begin(){
    	System.out.println("GAME BEGUN");
    }
}

