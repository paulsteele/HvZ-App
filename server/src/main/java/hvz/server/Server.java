package hvz.server;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@ComponentScan
@EnableAutoConfiguration
public class Server {

    public static void main( String[] args ) {
    	ServerConfiguration.setPortNumber(8080);
    	SpringApplication.run(Server.class, args);
    }
    
    public static String generateFeedcode(boolean admin){
    	//generate a unique string code
    	String feedcode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
    	//add prefix based on player or admin
    	char prefix;
    	if (admin)
    		prefix = ServerConfiguration.adminPrefix;
    	else
    		 prefix = ServerConfiguration.playerPrefix;
    	
    	feedcode = prefix + feedcode.toUpperCase();
    	//verify that feedcode isn't taken
    	
    	return feedcode;
    }
}
