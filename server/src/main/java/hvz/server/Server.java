package hvz.server;

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
    
}
