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
	public static final char gamePrefix = 'G'; //prefix for game codes
	public static final char revivePrefix = 'R'; //prefix for revive codes
	public static final char complaintPrefix = 'C'; //prefix for complaint
	public static final String success = "success"; //success message
	public static final String dummyCode = "00000000"; //value for a not assigned player.
	public static final int alphaZombieCount = 4; //number of alpha zombies to spawn
	private static boolean logging = true; // whether or not to show console logs
	/**
	 * Component function to actually handle customizations.
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
    /**
     * Sets whether or not logging is enabled
     * @param logging
     */
    public static void setLogging(boolean logging){
    	ServerConfiguration.logging = logging;
    }
    
    /**
     * returns whether or not logging is enabled
     * @return
     */
    public static boolean getLogging(){
    	return logging;
    }

}
