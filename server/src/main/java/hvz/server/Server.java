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
    	String feedcode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
    	char prefix = ServerConfiguration.playerPrefix;
    	if (admin)
    		prefix = ServerConfiguration.adminPrefix;
    	return prefix + feedcode.toUpperCase();
    }
}
