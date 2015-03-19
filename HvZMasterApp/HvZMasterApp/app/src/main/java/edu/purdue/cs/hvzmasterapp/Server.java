package edu.purdue.cs.hvzmasterapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Server{
    private static final Server INSTANCE = new Server();
    private static String serviceURL = "http://128.211.191.47:8080";
    private static final HttpClient client = new DefaultHttpClient();

    private Server() { }

    /* Only use one server object for entire app */
    public static Server getInstance() {
        return INSTANCE;
    }

    //returns 0 if successfully added player
    public int addPlayer(User user){
        return 0;
    }
    
    //returns 0 if successfully removed a player
    public int removePlayer(User user){
        return 0;
    }
    
    //returns a user using its unique ID
    public User getPlayer(String code){
		/*StringBuilder url = new StringBuilder(serviceURL);
		url.append("/user/get");
		url.append("?feedcode="+code);
		
		System.err.println(url.toString());
		PostTask post = new PostTask(url.toString(), client);
		JSONObject response = null;
		String username, feedcode;
		boolean isZombie, isAdmin;
		try {
			response = post.execute().get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();			}

		if (response == null) {
			return null;
		}
		try {
			username = response.getString("username");
			feedcode = response.getString("feedcode");
			isAdmin = response.getBoolean("isAdmin");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return new User(username, feedcode, isAdmin);*/
        return null;
    }
    
    //generates and returns a new Feed Code
    public String getNewFeedcode(boolean admin){
        JSONObject request = new JSONObject();
        try {
            request.put("admin", admin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Feedcode", request.toString());

        PostTask task = new PostTask(serviceURL + "/feedcode", client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Get new feedcode", "Server reponse error");
            return null;
        }

        try {
            return response.getString("feedcode");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    //returns a new list of users
    public ArrayList<User> getUserList() {
        GetTask task = new GetTask(serviceURL + "/user", client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Get user list", "Server reponse error");
            return null;
        }

        // Parse JSON Object and place users into list
        ArrayList<User> list = new ArrayList<>();
        try {
            JSONArray users = response.getJSONArray("users");

            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String usr = user.getString("username");
                String code = user.getString("feedcode");
                boolean isZombie = user.getBoolean("isZombie");
                boolean admin = user.getBoolean("isAdmin");
                User u = new User(usr, code, isZombie, admin);
                list.add(u);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    //returns 0 if tagging was successful
    public int tag(User tagger, User taggee){
        return 0;
    }
    
    //returns an image of a map
    public void getMap(){
    
    }
    
    //returns 0 if game is successfully started
    public void startGame() {
    }
    
    //returns time remaining in the game
    public void getTimeRemaining(){
    
    }
    
    // returns 0 if login was successful
    // can either use username or feedcode
    public int login(String identifier, String password){
        StringBuilder url = new StringBuilder(serviceURL);
        url.append("/user/");
        url.append(identifier);
        /*url.append("?feedcode="+feedcode);
        url.append("&password="+password);*/

        JSONObject request = new JSONObject();
        try {
            request.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Login url", url.toString());
        Log.d("Login JSONObject", request.toString());

        PostTask task = new PostTask(url.toString(), client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Login", "Server reponse error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("Login", "Success");
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Login", "Error");
        return -1;
    }

    /* Function to add user to database
     * Verify username/feedcode are not taken
     * return non-zero if error occurs
     */
    public int register(String username, String feedcode, String password, boolean admin) {
        StringBuilder url = new StringBuilder(serviceURL);
        url.append("/user");
        /* url.append("?username="+username);
        url.append("&feedcode="+feedcode);
        url.append("&password="+password);
        url.append("&admin="+admin);*/

        JSONObject request = new JSONObject();
        try {
            request.put("username", username);
            request.put("feedcode", feedcode);
            request.put("password", password);
            request.put("admin", admin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Register url", url.toString());
        Log.d("Register JSONObject", request.toString());

        PostTask post = new PostTask(url.toString(), client, request);

        JSONObject response = null;
        try {
             response = post.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Register", "Server reponse error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("Register", "success");
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Register", "error");
        return -1;
    }

}

class PostTask extends AsyncTask<Void, Void, JSONObject> {
    HttpClient client;
    String url;
    JSONObject request;

    PostTask(String url, HttpClient client, JSONObject request) {
        this.client = client;
        this.url = url;
        this.request = request;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        // Create post method
        HttpPost post = new HttpPost(url);

        HttpResponse response = null;
        try {
            // Add JSON object to post
            StringEntity se = new StringEntity(request.toString());
            post.setEntity(se);
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");

            // Send request and get response
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.e("HTTP Post", "No server response");
            return null;
        }

        // convert response to string
        String responseString = null;
        try {
            responseString = convertToString(response.getEntity().getContent()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert response string to JSON object
        JSONObject responseObj;
        try {
            responseObj = new JSONObject(responseString);
            return responseObj;
        } catch (JSONException e) {
            Log.e("HTTP Post", "Error getting JSON Object");
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder convertToString(InputStream is) {
        String line;

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder string = new StringBuilder();
        try {
            while ((line = rd.readLine()) != null) {
                string.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }
}

class GetTask extends AsyncTask<Void, Void, JSONObject> {
    HttpClient client;
    String url;

    GetTask(String url, HttpClient client) {
        this.client = client;
        this.url = url;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        // Create GET method
        HttpGet get = new HttpGet(url);

        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            Log.e("HTTP Get", "No server response");
            return null;
        }

        String responseString = null;
        try {
            responseString = convertToString(response.getEntity().getContent()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject responseObj;
        try {
            responseObj = new JSONObject(responseString);
            return responseObj;
        } catch (JSONException e) {
            Log.e("HTTP Get", "Error getting JSON Object");
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder convertToString(InputStream is) {
        String line;

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder string = new StringBuilder();
        try {
            while ((line = rd.readLine()) != null) {
                string.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }
}