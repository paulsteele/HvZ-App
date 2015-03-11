package hvz.server;


import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	/**
	 * Registers a user and puts into the database
	 * Valid JSON {"username": "username", "password":"password", "feedcode": "feedcode", "admin": true,
	 */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String registerPlayer (@RequestBody String value) {
    	boolean failed = false;
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		String username = null;
		String feedcode = null;
		String password = null;
		boolean admin = false;
		try{
			username = input.getString("username");
			feedcode = input.getString("feedcode");
			password = input.getString("password");
			admin = input.getBoolean("admin");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		//check values are present
		if (username == null || password == null || feedcode == null){
			failed = true;
		}
		//check feedcode not taken
		if (!failed && Server.checkRegistered(feedcode) == true){
			failed = true;
		}
		//Create a user from info
		User user = null;
		if (!failed){
    		if (admin) //create an admin
    			user = new Admin(username, feedcode);
    		else //create a player
    			user = new Player(username, feedcode);
    		Server.registerUser(user, password);
		}
    	//Set up response object
		JSONObject output = new JSONObject();
    	try {
    		if (!failed){ //info only on success
    			output.put("username", username);
    			output.put("feedcode", feedcode);
    			output.put("password", password);
    		}
    		//info no matter what
			output.put(ServerConfiguration.success, !failed);
        }		
		catch (JSONException e) {//extreme error
			e.printStackTrace();
		}
    	return output.toString();
    }
    /**
     * Retrieves a user from the database and sends it to the client
     */
    @RequestMapping(value = "/user/get/{identifier}", method = RequestMethod.GET)
    public String getPlayer (@PathVariable("identifier") String identifier){
		boolean failed = false;
    	//Set up response object
		JSONObject output = new JSONObject();
		//grab the user
		User user = Server.getUser(identifier);
		failed = (user == null);
    	try {
			if (!failed) { //means user is found
    			output.put("username", user.username);
    			output.put("feedcode", user.feedcode);
    			output.put("isAdmin", user.isAdmin);
    			//add information about zombie status
    			if (!user.isAdmin){
    				output.put("isZombie", ((Player) user).isZombie);
    			}
    			else {
    				output.put("isZombie", false);
    			}
			}
			//info no matter what
			output.put(ServerConfiguration.success, !failed);

		} catch (JSONException e) {
			//big error
			e.printStackTrace();
		}
    	return output.toString();
    }
    
    @RequestMapping(value = "/user/get", method = RequestMethod.GET)
    public String getAll () {
		//Set up response object
		JSONObject output = new JSONObject();
		//set JSONArray and holder array
		JSONArray users = new JSONArray();
		User[] userObject = Server.getAllUsers();
    	try {
			for (int i = 0; i < userObject.length; i++){
				JSONObject user = new JSONObject();
				user = new JSONObject();
    			user.put("username", userObject[i].username);
    			user.put("feedcode", userObject[i].feedcode);
    			user.put("isAdmin", userObject[i].isAdmin);
    			//handle zombie
    			if (!userObject[i].isAdmin){
    				Player pl = (Player) userObject[i];
    				user.put("isZombie", pl.isZombie);
    			}
    			else {
    				user.put("isZombie", false);
    			}
    			users.put(i, user);
			}
			output.put(ServerConfiguration.success, true);
			output.put("users", users);
		} catch (JSONException e) {
			//big error
			e.printStackTrace();
		}
    	return output.toString();
    }
    
    @RequestMapping("/tag")
    public String tag(@RequestParam(value = "tagger", required = false) String tagger,
    					@RequestParam(value = "tagged", required = false) String tagged){
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//verify that all parameters are valid
    		if (tagger == null || tagged == null){
    			response.put(ServerConfiguration.success, false);
    		}
    		else {
    			response.put(ServerConfiguration.success, true);
    		}
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while verifying parameters for tagging");
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping("/game/begin")
    public String beginGame () {
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//start the game
    		Server.begin();
    		response.put(ServerConfiguration.success, true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while trying to begin game");
			e.printStackTrace();
		}
    	return response.toString();
    }
    @RequestMapping("/game/isstarted")
    	public String isStarted() {
		//Set up response object
		JSONObject response = new JSONObject();
    	try{
        	response.put(ServerConfiguration.success, true);
        	response.put("started", Server.checkBegun());
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    		return null;
    	}
    	return response.toString();

    	}
    
    @RequestMapping("/user/login")
    public String login(@RequestParam(value = "feedcode", required = false) String feedcode,
    					@RequestParam(value = "password", required = false) String password){
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//verify that all parameters are valid
    		if (feedcode == null || password == null){
    			response.put(ServerConfiguration.success, false);
    		}
    		else {
    			//if valid
    			User user = Server.getUser(feedcode.toUpperCase());
    			if (user != null){
        			user = Server.loginUser(user, password);
        			if (user != null){
            			return getPlayer(feedcode.toUpperCase());
        			}
        			else {
        				response.put(ServerConfiguration.success, false);
        			}

    			}
    			else {
    				response.put(ServerConfiguration.success, false);
    			}

    		}
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while attempting to login");
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping ("feedcode/generate")
    public String generateFeedcode(@RequestParam(value = "admin", defaultValue = "false")boolean admin){
    	//generate a unique string code
    	String feedcode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
    	//add prefix based on player or admin
    	char prefix;
    	if (admin)
    		prefix = ServerConfiguration.adminPrefix;
    	else
    		 prefix = ServerConfiguration.playerPrefix;
    	
    	feedcode = prefix + feedcode.toUpperCase();
    	
    	//Set up response object
    	JSONObject response = new JSONObject();
    	//verify that feedcode isn't taken
    	try {
	    	if (Server.checkRegistered(feedcode) == false){
				response.put(ServerConfiguration.success, true);
				response.put("feedcode", feedcode);
	    	}
	    	else { //if it is taken, regenrate one
				return generateFeedcode(admin);
	    	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while verifying generated feed code");
			e.printStackTrace();
		}
    	
    	return response.toString();
    }
    
}