package hvz.server;

import org.springframework.boot.context.embedded.*;
import org.springframework.stereotype.Component;

/**
 * Server Configuration Class
 * Static methods allow runtime configuration of spring boot parameters.
 * These functions must be called before the application is started to take effect.
 * Also static final variables are used for defining constants.
 *
 */
@Component
public class ServerConfiguration implements EmbeddedServletContainerCustomizer {
	static private int portNumber = 8080; //The port number to listen on
	static public final int feedcodeLength = 8; //length of feedcode length
	static public final char adminPrefix = 'A'; //prefix to add to admin feedcodes
	public static final char playerPrefix = 'P'; //prefix to add to player feedcodes
	public static final String success = "success"; //success message
	/**
	 * Component function to actually handle custimizations.
	 */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(ServerConfiguration.getPortNumber());
    }
    
    /**
     * Get the actively set port number to listen on.
     * @return port number
     */
    public static int getPortNumber(){
    	return portNumber;
    }
    
    /**
     * Set the port number to actively listen to.
     * @param portNumber
     */
    public static void setPortNumber(int portNumber){
    	ServerConfiguration.portNumber = portNumber;
    }

}