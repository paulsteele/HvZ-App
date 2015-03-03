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
	    	DBHandler.setPassword(user.feedcode, password, c);
	    	DBHandler.disconnect(c);
    	}
    	catch (SQLException e){
    		return false;
    	}
    	System.out.println("registering " + user.username + " with password " +  password);
    	return true;
    }
    
    public static void loginUser(User user, String password){
    	
    }
    
    public static User getUser(String feedcode){
    	return null;
    }
    
    public static User[] getAllUsers(){
    	return null;
    }
}

