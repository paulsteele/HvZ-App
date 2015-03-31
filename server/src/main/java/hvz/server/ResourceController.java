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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	/**
	 * Registers a user and puts into the database
	 * Valid JSON {"username": "username", "password":"password", "admin": true}
	 */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
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
		String feedcode = ServerConfiguration.dummyCode; //empty feedcode for registration
		String password = null;
		boolean admin = false;
		try{
			username = input.getString("username");
			password = input.getString("password");
			admin = input.getBoolean("admin");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		//check values are present
		if (username == null || password == null ){
			failed = true;
		}
		
		//check username not taken
		if (!failed && Server.checkRegisteredUsername(username) == true){
			failed = true;
		}
		//Create a user from info
		User user = null;
		if (!failed){
    		if (admin) //create an admin
    			user = new Admin(username, feedcode);
    		else //create a player
    			user = new Player(username, feedcode);
    		user.gamecode = ServerConfiguration.dummyCode;
    		Server.registerUser(user, password);
		}
    	//Set up response object
		JSONObject output = new JSONObject();
    	try {
    		if (!failed){ //info only on success
    			output.put("username", username);
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
     * Updates a user 
     * Valid JSON {"feedcode": "feedocde", "gamecode": "gamecode"}
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.PUT)
    public String update(@PathVariable("username") String username, @RequestBody String value) {
    	boolean failed = false;
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		String feedcode = null;
		String gamecode = null;
		try{
			feedcode = input.getString("feedcode");
			gamecode = input.getString("gamecode");
			
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		
		User user = Server.getUser(username);
		if (user == null)
			failed = true;
		
		if (!failed){
			Server.updateUser(username, feedcode, gamecode, user.isAdmin);
		}
		
    	//Set up response object
		JSONObject output = new JSONObject();
    	try {
    		//info no matter what
			output.put(ServerConfiguration.success, !failed);
        }		
		catch (JSONException e) {//extreme error
			e.printStackTrace();
		}
    	return output.toString();
		
		
    }
    
    /**
     * trys to login a user
     * Valid JSON {"password": "password}
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
    public String login(@PathVariable("username") String username, @RequestBody String value) {
    	boolean failed = false;
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		String password = null;
		try{
			password = input.getString("password");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		//check values are present
		if (password == null){
			failed = true;
		}
		User user = Server.getUser(username);
    	user = Server.loginUser(user, password);
    	if (user == null)
    		failed = true;
		if (!failed){
			return getPlayer(username, user.gamecode);
    	}
    	//Set up response object
		JSONObject output = new JSONObject();
    	try {
    		//info no matter what
			output.put(ServerConfiguration.success, !failed);
		} catch (JSONException e) {
			//really bad error
			System.out.println("JSONException while attempting to login");
			e.printStackTrace();
		}
    	return output.toString();
    }
    
    /**
     * Retrieves a user from the database and sends it to the client
     */
    @RequestMapping(value = "/{game}/user/{username}", method = RequestMethod.GET)
    public String getPlayer (@PathVariable("username") String username, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//Set up response object
		JSONObject output = new JSONObject();
		//grab the user
		User user = Server.getUser(username);
		failed = (user == null);
    	try {
			if (!failed) { //means user is found
    			output.put("username", user.username);
    			output.put("feedcode", user.feedcode);
    			output.put("isAdmin", user.isAdmin);
    			output.put("gamecode", user.gamecode);
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
    
    /**
     * Returns all users in a current game
     */
    @RequestMapping(value = "/{game}/user", method = RequestMethod.GET)
    public String getAll (@PathVariable("game") String game) {
		//Set up response object
		JSONObject output = new JSONObject();
		//set JSONArray and holder array
		JSONArray users = new JSONArray();
		User[] userObject = Server.getAllUsers(game);
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
    
    /**
     * generates a feedcode
     * Valid JSON {"admin": false}
     */
    @RequestMapping (value = "{game}/feedcode", method  = RequestMethod.POST)
    public String generateFeedcode(@RequestBody String value, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		boolean admin = false;
		try{
			admin = input.getBoolean("admin");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}

		if (failed){
			admin = false;
		}
		boolean done = false;
		String feedcode = null;
		//loop through until unique feedcode has been generated
		while(!done && !failed){
	    	//generate a unique string code
	    	feedcode = RandomStringUtils.randomAlphanumeric(ServerConfiguration.feedcodeLength -1);
	    	//add prefix based on player or admin
	    	char prefix;
	    	if (admin)
	    		prefix = ServerConfiguration.adminPrefix;
	    	else
	    		 prefix = ServerConfiguration.playerPrefix;
	    	
	    	feedcode = prefix + feedcode.toUpperCase();
	    	done = !Server.checkRegisteredFeedcode(feedcode, game);
		}
    	//Set up response object
    	JSONObject output = new JSONObject();
    	//verify that feedcode isn't taken
    	try {
			output.put(ServerConfiguration.success, !failed);
			if (!failed){
				output.put("feedcode", feedcode);	
			}
	    }
		catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while verifying generated feed code");
			e.printStackTrace();
		}
    	
    	return output.toString();
    }
    
    /**
     * process a tag request fails if cannibal tags or users don't exist
     * Valid JSON {"tagger": feedcode, "tagged": feedcode}
     */
    @RequestMapping(value = "/{game}/tag", method = RequestMethod.POST)
    public String tag(@RequestBody String value, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		String taggerString = null;
		String taggedString = null;
		try{
			taggerString = input.getString("tagger");
			taggedString = input.getString("tagged");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
    	if (taggerString == null || taggedString == null){
    		failed = true;
    	}
    	//implement the tag
    	if (!failed){
    		failed = Server.tag(taggerString, taggedString, game);
    	}
		
    	//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		response.put(ServerConfiguration.success, !failed);
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while verifying parameters for tagging");
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping(value = "/newgame", method  = RequestMethod.GET)
    public String createGame() {
    	String gamecode = Server.generateGamecode();
    	boolean failed = false;
    	Server.createGame(gamecode);
    	JSONObject response = new JSONObject();
    	try {
    		response.put(ServerConfiguration.success, !failed);
    		response.put("gamecode", gamecode);
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    	return response.toString();
    }
    
    @RequestMapping("/{game}/begin")
    public String beginGame (@PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//start the game
    		Server.begin(game);
    		response.put(ServerConfiguration.success, !failed);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while trying to begin game");
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping("/{game}/isstarted")
    	public String isStarted(@PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//Set up response object
		JSONObject response = new JSONObject();
    	try{
        	response.put(ServerConfiguration.success, !failed);
        	response.put("started", Server.checkBegun(game));
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    		return null;
    	}
    	return response.toString();

    	}    
}