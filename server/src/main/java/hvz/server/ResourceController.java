package hvz.server;


import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping("/user/register")
    public String registerPlayer (@RequestParam(value="username", required = false) String username, 
    								@RequestParam(value = "feedcode" , required = false) String feedcode , 
    								@RequestParam(value = "password", required = false) String password, 
    								@RequestParam(value = "admin", defaultValue= "false") boolean admin) {
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//verify that all parameters are valid
    		if (username == null || password == null){
    			response.put(ServerConfiguration.success, false);
    		}
    		else {

    			String feedcodeVal;
    			if (feedcode == null){
    				//need to generate a feedcode
    				JSONObject generated = new JSONObject(new JSONTokener(generateFeedcode(admin)));
    				feedcodeVal = generated.getString("feedcode");
    			}
    			else {
    				//player supplied
    				feedcodeVal = feedcode;
    			}

    			
        		//Create a user from info
        		User user;
        		if (admin){
        			user = new Admin(username, feedcodeVal);
        		}
        		else{
        			user = new Player(username, feedcodeVal);
        		}
        		if (Server.checkRegistered(user.feedcode) == false){
        			Server.registerUser(user, password);
        			response.put(ServerConfiguration.success, true);
        			response.put("username", username);
        			response.put("feedcode", feedcodeVal);
        			response.put("password", password);
        		}
        		else {
        			//already registered
        			response.put(ServerConfiguration.success, false);
        		}
        		
    		}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping("/user/get")
    public String getPlayer (@RequestParam(value="feedcode", required = false) String feedcode){
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//verify that all parameters are valid
    		if (feedcode == null){
    			response.put(ServerConfiguration.success, false);
    		}
    		else {
    			User user = Server.getUser(feedcode);
    			response.put(ServerConfiguration.success, true);
    			response.put("username", user.username);
    			response.put("feedcode", user.feedcode);
    		}
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping("/user/getall")
    public String getAll () {
		//Set up response object
		JSONObject response = new JSONObject();
		JSONArray users = new JSONArray();
    	try {
    			users.put(Server.getAllUsers());
    			response.put(ServerConfiguration.success, true);
    			response.put("users", users);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
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
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping("/game/begin")
    public String beginGame () {
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    			response.put(ServerConfiguration.success, true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
    
    @RequestMapping("/user/login")
    public String login(@RequestParam(value = "identifier", required = false) String identifier,
    					@RequestParam(value = "password", required = false) String password){
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//verify that all parameters are valid
    		if (identifier == null || password == null){
    			response.put(ServerConfiguration.success, false);
    		}
    		else {
    			response.put(ServerConfiguration.success, true);
    		}
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
	    	else {
				response.put(ServerConfiguration.success, false);
	    	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return response.toString();
    }
    
}