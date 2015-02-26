package hvz.server;

import org.json.JSONException;
import org.json.JSONObject;
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
    			response.put("success", false);
    		}
    		else {
    			response.put("success", true);
    			response.put("username", username);
    			String feedcodeVal;
    			if (feedcode == null){
    				//need to generate a feedcode
    				feedcodeVal = Server.generateFeedcode(admin);
    			}
    			else {
    				//player supplied
    				feedcodeVal = feedcode;
    			}
    			response.put("feedcode", feedcodeVal);
    			response.put("password", password);
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
    			response.put("success", false);
    		}
    		else {
    			response.put("success", true);
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
    	try {
    			response.put("success", true);
    		
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
    			response.put("success", false);
    		}
    		else {
    			response.put("success", true);
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
    			response.put("success", true);
    		
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
    			response.put("success", false);
    		}
    		else {
    			response.put("success", true);
    		}
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
    
}