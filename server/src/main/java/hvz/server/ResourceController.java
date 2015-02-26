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
    								@RequestParam(value = "password", required = false) String password) {
		//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//verify that all parameters are valid
    		if (username == null || feedcode == null || password == null){
    			response.put("success", false);
    		}
    		else {
    			response.put("success", true);
    			response.put("username", username);
    			response.put("feedcode", feedcode);
    			response.put("password", password);
    		}
    		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return response.toString();
    }
}