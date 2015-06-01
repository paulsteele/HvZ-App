package hvz.server;


import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.MediaType;
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
    public String registerPlayer (HttpServletRequest request, @RequestBody String value) {
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
		catch (NullPointerException e){
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
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : REGISTER : " + username + ", " + password);
    	}
    	return output.toString();
    }
    
    /**
     * Updates a user 
     * Valid JSON {"feedcode": "feedcode", "gamecode": "gamecode"}
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.PUT)
    public String update(HttpServletRequest request, @PathVariable("username") String username, @RequestBody String value) {
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
		catch (NullPointerException e){
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
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : USER UPDATE : " + username + ", " + feedcode + ", " + gamecode);

    	}
    	return output.toString();
		
		
    }
    
    /**
     * trys to login a user
     * Valid JSON {"password": "password}
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @PathVariable("username") String username, @RequestBody String value) {
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
		catch (NullPointerException e){
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
    	//Set up response object
		JSONObject output = new JSONObject();
    	try {
    		if (!failed){
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
    			output.put("gameover", Server.checkGameEnded(user.gamecode));
    		}
    		//info no matter what
			output.put(ServerConfiguration.success, !failed);
		} catch (JSONException e) {
			//really bad error
			System.out.println("JSONException while attempting to login");
			e.printStackTrace();
		}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : LOGIN : " + username);
    	}
    	
    	return output.toString();
    }
    
    /**
     * Retrieves a user from the database and sends it to the client
     */
    @RequestMapping(value = "/{game}/user/{feedcode}", method = RequestMethod.GET)
    public String getPlayer (HttpServletRequest request, @PathVariable("feedcode") String feedcode, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//Set up response object
		JSONObject output = new JSONObject();
		//grab the user
		User user = Server.getUser(feedcode, game);
		failed = (user == null);
    	try {
			if (!failed) { //means user is found
    			output.put("username", user.username);
    			output.put("feedcode", user.feedcode);
    			output.put("isAdmin", user.isAdmin);
    			output.put("gamecode", user.gamecode);
    			//temp cooldown
    			output.put("cooldown", -Server.getCooldown(feedcode, game));
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
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET PLAYER : " + game + ", " + feedcode);
    	}
    	
    	return output.toString();
    }
    
    /**
     * Gets a user 
     */
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String getPlayer (HttpServletRequest request, @PathVariable("username") String username){
    	//Set up response object
		JSONObject output = new JSONObject();
		//grab the user
		User user = Server.getUser(username);
		boolean failed = (user == null);
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
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET USER : " + username);
    	}
    	
    	return output.toString();
    }
    
    /**
     * Returns all users in a current game
     */
    @RequestMapping(value = "/{game}/user", method = RequestMethod.GET)
    public String getAll (HttpServletRequest request, @PathVariable("game") String game) {
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
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET USERS : " + game);
    	}
    	
    	return output.toString();
    }
    
    /**
     * generates a feedcode
     * Valid JSON {"admin": false}
     */
    @RequestMapping (value = "{game}/feedcode", method  = RequestMethod.POST)
    public String generateFeedcode(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game){
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
		catch (NullPointerException e){
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
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET FEEDCODE : " + game);
    	}
    	
    	return output.toString();
    }
    
    /**
     * process a tag request fails if cannibal tags or users don't exist
     * Valid JSON {"tagger": feedcode, "tagged": feedcode}
     */
    @RequestMapping(value = "/{game}/tag", method = RequestMethod.POST)
    public String tag(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	if (!failed && Server.checkGameEnded(game)){//can't tag if game already ended
    		failed = true;
    	}
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
			taggerString = input.getString("tagger").toUpperCase();
			taggedString = input.getString("tagged").toUpperCase();
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		catch (NullPointerException e){
			failed = true;
		}
    	if (taggerString == null || taggedString == null){
    		failed = true;
    	}
    	//implement the tag
    	if (!failed){
    		failed = !Server.tag(taggerString, taggedString, game);
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
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : TAG : " + taggerString + ", " + taggedString);
    	}
    	
    	return response.toString();
    }
    
    /**
     * get all games
     */
    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String getGames(HttpServletRequest request){
    	//Set up response object
    	JSONObject output = new JSONObject();
    	//set JSONArray and holder array
    	JSONArray gameList = new JSONArray();
    	Game[] gameGameList = Server.getAllGames();
    	try {
    		for (int i = 0; i < gameGameList.length;i++){
    			if (Server.checkGameEnded(gameGameList[i].gameCode)){
    				continue; //only stop the current
    			}
    			JSONObject game = new JSONObject();
    			game.put("gamecode", gameGameList[i].gameCode);
    			game.put("gamename", gameGameList[i].name);
    			game.put("creator", gameGameList[i].creator);
    			gameList.put(game);
    		}
    	
			output.put("games", gameList);
			output.put(ServerConfiguration.success, true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET GAMES :");
    	}
    	
    	return output.toString();		
    }
    
    /**
     * Will create a new game, and add to the list
     * Valid JSON {"gamename": value, "creator": value}
     */
    @RequestMapping(value = "/", method  = RequestMethod.POST)
    public String createGame(HttpServletRequest request, @RequestBody String value) {
    	boolean failed = false; //immediately fail if game doesn't exist
    	//take in input and create variables for each entry
		JSONObject input = null;
		String gamename = null;
		String creator = null;
		try {
			input = new JSONObject(new JSONTokener(value));
			gamename = input.getString("gamename");
			creator = input.getString("creator");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		catch (NullPointerException e){
			failed = true;
		}
    	
    	String gamecode = Server.generateGamecode();
    	Server.createGame(gamecode, gamename, creator);
    	JSONObject response = new JSONObject();
    	try {
    		response.put(ServerConfiguration.success, !failed);
    		response.put("gamecode", gamecode);
    	}
    	catch(JSONException e){
    		e.printStackTrace();
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : CREATE GAME : " + gamename);
    	}
    	
    	return response.toString();
    }
    
    /**
     * sets a game to begin
     */
    @RequestMapping(value = "{game}", method = RequestMethod.PUT)
    public String beginGame (HttpServletRequest request, @PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	if (Server.checkBegun(game)){ //fail if started
    		failed = true;
    	}
    	//Set up response object
		JSONObject response = new JSONObject();
    	try {
    		//start the game
    		if (!failed)
    			Server.begin(game);
    		response.put(ServerConfiguration.success, !failed);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("JSONException while trying to begin game");
			e.printStackTrace();
		}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : START GAME: " + game);
    	}
    	
    	return response.toString();
    }
    
    /**
     * Checks to see if a game has begun
     */
    @RequestMapping(value = "{game}", method = RequestMethod.GET)
    	public String getGame(HttpServletRequest request, @PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//Set up response object
		JSONObject response = new JSONObject();
    	try{
        	response.put(ServerConfiguration.success, !failed);
        	response.put("started", Server.checkBegun(game));
        	response.put("gameover", Server.checkGameEnded(game));
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    		return null;
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : CHECK BEGUN : " + game);
    	}
    	
    	return response.toString();

    	}  
    /**
     * force zombification on a user
     */
    
    @RequestMapping(value = "{game}/forcezombie/{feedcode}", method = RequestMethod.GET)
    public String forcezombie(HttpServletRequest request, @PathVariable("game") String game, @PathVariable("feedcode") String feedcode){
    	Server.changeStatus(feedcode, game, true);
		JSONObject response = new JSONObject();
    	try{
        	response.put(ServerConfiguration.success, true);
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    		return null;
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : FORCE TAG : " + feedcode);
    	}
    	
    	return response.toString();

    	}  
    
    /**
     * gets all revivecodes
     */
    @RequestMapping(value = "{game}/revivecodes", method = RequestMethod.GET)
    public String getRevivecodes(HttpServletRequest request, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	JSONObject output = new JSONObject();
    	//set JSONArray and holder array
    	JSONArray reviveList = new JSONArray();
    	String[] reviveReviveList = Server.getAllReviveCodes(game);
    	try {
    		for (int i = 0; i < reviveReviveList.length;i++){
    			reviveList.put(reviveReviveList[i]);
    		}
    	
			output.put("revivecodes", reviveList);
			output.put(ServerConfiguration.success, !failed);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET REVIVE CODES: " + game);
    	}
    	
    	return output.toString();	
    }
    
    
    /**
     * returns a new revive code
     */
    @RequestMapping(value = "{game}/revivecode", method = RequestMethod.GET)
    public String getRevive(HttpServletRequest request, @PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	JSONObject response = new JSONObject();
    	try{
	    	if (!failed){
	    		response.put("revivecode", Server.generateRevivecode(game));
	    	}
        	response.put(ServerConfiguration.success, !failed);
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET REVIVE CODE: " + game);
    	}
    	
    	return response.toString();

    }
    
    /**
     * revives a player
     */
    @RequestMapping(value = "{game}/revivecode", method = RequestMethod.POST)
    public String revive(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		String revivecode = null;
		String feedcode = null;
		try{
			revivecode = input.getString("revivecode").toUpperCase();
			feedcode = input.getString("feedcode").toUpperCase();
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		catch (NullPointerException e){
			failed = true;
		}
		if (revivecode == null || feedcode == null){
			failed = true;
		}
		
		if (!failed){
			if (!Server.checkReviveCode(revivecode, game)){
				failed = true;
			}
		}
		
		if (!failed){
			Server.changeStatus(feedcode, game, false);
    		Server.deleteReviveCode(revivecode, game);
		}
		
    	JSONObject response = new JSONObject();
    	try{
        	response.put(ServerConfiguration.success, !failed);
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : PLAYER REVIVE: " + game);
    	}
    	
    	return response.toString();
    }
		
    
    
    /**
     * returns an array of all missions
     */
    @RequestMapping(value = "{game}/mission", method = RequestMethod.GET)
    public String getAllMissions(HttpServletRequest request, @PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//Set up response object
    	JSONObject output = new JSONObject();
    	//set JSONArray and holder array
    	JSONArray missionList = new JSONArray();
    	Mission[] missionMList = Server.getAllMissions(game);
    	try {
    		for (int i = 0; i < missionMList.length;i++){
    			JSONObject mission = new JSONObject();
    			mission.put("title", missionMList[i].title);
    			mission.put("humanobjective", missionMList[i].humanObj);
    			mission.put("zombieobjective", missionMList[i].zombieObj);
    			boolean complete = true;
    			if (missionMList[i].isCompleted == 0){
    				complete = false;
    			}
    			mission.put("completed", complete);
    			missionList.put(mission);
    		}
    	
			output.put("missions", missionList);
			output.put(ServerConfiguration.success, !failed);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET MISSIONS: " + game);
    	}
    	
    	return output.toString();	
    }
    
    /**
     * create a mission
     */
    @RequestMapping(value = "{game}/mission", method = RequestMethod.POST)
    public String createMission(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
		JSONObject input = null;
		String humanObj = null;
		String zombieObj = null;
		String title = null;
		try {
			input = new JSONObject(new JSONTokener(value));
			humanObj = input.getString("humanobjective");
			zombieObj = input.getString("zombieobjective");
			title = input.getString("title");
		} 
		catch(JSONException e){
			failed = true;
		}
		catch(NullPointerException e){
			failed = true;
		}
		
		if(humanObj == null || zombieObj == null || title == null){
			failed = true;
		}
		
		if(!failed){
			Server.addMission(game, humanObj, zombieObj, 0, title );
		}
		
		JSONObject response = new JSONObject();
		try{
        	response.put(ServerConfiguration.success, !failed);
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : MISSION CREATE : " + game);
    	}
	
		return response.toString();
    }
    
    /**
     * returns a specific misison
     */
    @RequestMapping(value = "{game}/mission/{title}", method = RequestMethod.GET)
    public String getMission(HttpServletRequest request, @PathVariable("game") String game, @PathVariable("title") String title) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	Mission mission = Server.getMission(game, title);
    	if (mission == null){
    		failed = true;
    	}
    	JSONObject response = new JSONObject();
    	try{
        	response.put(ServerConfiguration.success, !failed);
        	if (!failed){
        		response.put("title", mission.title);
        		response.put("humanobjective", mission.humanObj);
        		response.put("zombieobjective", mission.zombieObj);
        		boolean complete = true;
        		if (mission.isCompleted == 0){
        			complete = false;
        		}
        		response.put("completed", complete);
        	}
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET MISSION : " + game);
    	}
    	
    	return response.toString();
    }
    
    /**
     * updates a specific misison
     */
    @RequestMapping(value = "{game}/mission/{title}", method = RequestMethod.PUT)
    public String updateMission(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game, @PathVariable("title") String title) {
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		boolean complete = false;
		try{
			complete = input.getBoolean("complete");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		catch (NullPointerException e){
			failed = true;
		}
		if (!failed && complete){
			Server.updateMission(game, title);
		}
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : MISSION UPDATE : " + game);
    	}
	   	
	   	return response.toString();
    }
    
    /**
     * Ends the game
     */
    @RequestMapping(value = "{game}/end", method = RequestMethod.POST)
    public String endGame(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	if (!Server.checkBegun(game)){ //fail if not started
    		failed = true;
    	}
    	if (!failed){
    		Server.endGame(game);
    	}
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    	}
	   	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : END GAME: " + game);
    	}
	   	
    	return response.toString();
    	
    }
    
    /**
     * get end game stats / normal stats
     */
    @RequestMapping(value = "{game}/end", method = RequestMethod.GET)
    public String getEndStats(HttpServletRequest request, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	int numHumans = -1;
    	int numZombies = -1;
    	int numHumanTags = -1;
    	int numZombieTags = -1;
    	String winner = "Humans";
    	boolean ended = false;
    	if (!failed){
    		numHumans = Server.getPlayerCount(game, true);
    		numZombies = Server.getPlayerCount(game, false);
    		numHumanTags = Server.getTagCount(game, false);
    		numZombieTags = Server.getTagCount(game, true);
    		if (numHumans == 0){
    			winner = "Zombies";
    		}
    		ended = Server.checkGameEnded(game);
    	}
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
        	response.put("humans", numHumans);
        	response.put("zombies", numZombies);
        	response.put("zombietags", numHumanTags);
        	response.put("humantags", numZombieTags);
        	response.put("winner", winner);
        	response.put("gameover", ended);
    	}
    	catch (JSONException e){
    		e.printStackTrace();
    	}
	   	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET STATS: " + game);
    	}
	   	
    	return response.toString();
    }
    
    /**
     * Creates a complaint
     */
    @RequestMapping(value = "{game}/complaint", method = RequestMethod.POST)
    public String createComplaint(HttpServletRequest request, @RequestBody String value, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	//take in input and create variables for each entry
		JSONObject input = null;
		try {
			input = new JSONObject(new JSONTokener(value));
		} catch (JSONException e) {
			failed = true;
		}
		String sender = null;
		String message = null;
		try{
			sender = input.getString("sender");
			message = input.getString("message");
		}
		catch (JSONException e){
			//error 
			failed = true;
		}
		catch (NullPointerException e){
			failed = true;
		}
		
		if(sender == null || message == null){
			failed = true;
		}
		
		Complaint c = null;
		if (!failed){
    		c = Server.createComplaint(sender, message, game);
		}
		if (c == null){
			failed = true;
		}
		
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
        	if (!failed){
        		response.put("sender", c.sender);
        		response.put("message", c.message);
        		response.put("complaintcode", c.ccode);
        	}
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
	   	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : CREATE COMPLAINT : " + game);
    	}
	   	
    	return response.toString();
    }
    
    /**
     * returns a complaint
     */
    @RequestMapping(value = "{game}/complaint/{ccode}", method = RequestMethod.GET)
    public String getComplaint(HttpServletRequest request, @PathVariable("game") String game, @PathVariable("ccode") String ccode){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	Complaint c = null;
    	if (!failed){
    		c = Server.getComplaint(ccode, game);
    	}
    	if (c == null){
    		failed = true;
    	}
    	
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
        	if (!failed){
        		response.put("sender", c.sender);
        		response.put("message", c.message);
        		response.put("complaintcode", c.ccode);
        	}
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
	   	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET COMPLAINT : " + game);
    	}
	   	
    	return response.toString();
    }

    /**
     * Get all complaints
     */
    @RequestMapping(value = "{game}/complaint", method = RequestMethod.GET)
    public String getAllComplaints(HttpServletRequest request, @PathVariable("game") String game){
		//Set up response object
		JSONObject output = new JSONObject();
		//set JSONArray and holder array
		JSONArray complaints = new JSONArray();
		Complaint[] complaintObjects = Server.getAllComplaints(game);
    	try {
			for (int i = 0; i < complaintObjects.length; i++){
				JSONObject complaint = new JSONObject();
				complaint = new JSONObject();
    			complaint.put("sender", complaintObjects[i].sender);
    			complaint.put("message", complaintObjects[i].message);
    			complaint.put("complaintcode", complaintObjects[i].ccode);
    			complaints.put(complaint);
			}
			output.put(ServerConfiguration.success, true);
			output.put("complaints", complaints);
		} catch (JSONException e) {
			//big error
			e.printStackTrace();
		}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET COMPLAINTS : " + game);
    	}
    	
    	return output.toString();
    }
    
    /**
     * delete a complaint
     */
    @RequestMapping(value = "{game}/complaint/{ccode}", method = RequestMethod.DELETE)
    public String deleteComplaint(HttpServletRequest request, @PathVariable("game") String game, @PathVariable("ccode") String ccode){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	if (!failed){
    		failed = Server.deleteComplaint(ccode, game);
    	}
    	
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
	   	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : DELETE COMPLAINT : " + game);
    	}
	   	
    	return response.toString();
    }
    
    /**
     * Get a map
     */
    @RequestMapping(value = "{game}/map", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getMap(HttpServletRequest request, @PathVariable("game") String game){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	byte[] image = null;
    	if (!failed){
    		image = Server.getPicture(game);
    	}
    	if (image == null){
    		failed = true;
    	}
    	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : GET MAP : " + game);
    	}
    	
    	return image;
    }
    
    
    /**
     * create a map
     */
    @RequestMapping(value = "{game}/map", method = RequestMethod.POST)
    public String setMap(HttpServletRequest request, @PathVariable("game") String game, @RequestBody byte[] value){
    	boolean failed = !Server.checkGameExisits(game); //immediately fail if game doesn't exist
    	if (!failed){
    		failed = !Server.setPicture(value, game);
    	}
		JSONObject response = new JSONObject();
	   	try{
        	response.put(ServerConfiguration.success, !failed);
    	}
    	catch (JSONException e) {
    		e.printStackTrace();
    	}
	   	
    	if (ServerConfiguration.getLogging()){
    		System.out.println(request.getLocalAddr() + " : CREATE MAP : " + game);
    	}
	   	
    	return response.toString();
    }
}