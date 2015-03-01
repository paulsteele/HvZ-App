package hvz.server;

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
    	SpringApplication.run(Server.class, args);
    }
    
    public static boolean checkRegistered(String feedcode){
    	return false;
    }
    
    public static void registerUser(User user, String password){
    	System.out.println("registering " + user.username + " with password " +  password);
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

