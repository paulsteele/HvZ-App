package edu.purdue.cs.hvzmasterapp;

import android.net.http.AndroidHttpClient;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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
    public User getPlayer(String uniqueID){
        return null;
    }
    
    //generates and returns a new Feed Code
    public int getNewFeedcode(){
        return 0;
    }
    
    //returns a new list of users
    public ArrayList<User> getUserList(){
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
    public void startGame(){
    
    }
    
    //returns time remaining in the game
    public void getTimeRemaining(){
    
    }
    
    //returns 0 if log in was successful
    //Only one of username or Feedcode are needed
    public int login(String username, String Feedcode, String password){
        /*if(username == NULL)
            //use Feedcode
        else
            //use username
        */
        return 0;
    }

    /* Function to add user to database */
    /* Verify username/feedcode are not taken */
    /* return non-zero if error occurs */
    public int register(String username, String feedcode, String password, boolean admin) {
        JSONObject regObj = new JSONObject();
        try {
            regObj.put("username", username);
            regObj.put("feedcode", feedcode);
            regObj.put("password", password);
            regObj.put("admin", admin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringBuilder url = new StringBuilder(serviceURL);
        url.append("/user/register");
        HttpPost post = new HttpPost(url.toString());
        StringEntity regString = null;
        try {
            regString = new StringEntity(regObj.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (regString == null) {
            return -1;
        }

        post.setEntity(regString);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        //ResponseHandler handler = new BasicResponseHandler();

        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            System.err.println("Reponse error");
            return -1;
        }
        System.out.println(response.getEntity().toString());

        return 0;
    }

}
