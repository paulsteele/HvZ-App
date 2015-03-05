package edu.purdue.cs.hvzmasterapp;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
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
        StringBuilder url = new StringBuilder(serviceURL);
        url.append("/user/register");
        url.append("?username="+username);
        url.append("&feedcode="+feedcode);
        url.append("&password="+password);
        url.append("&admin="+admin);

        System.err.println(url.toString());

        PostTask post = new PostTask(url.toString(), client);

        JSONObject response = null;
        try {
             response = post.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

}

class PostTask extends AsyncTask<Void, Void, JSONObject> {
    String url;
    HttpClient client;

    PostTask(String url, HttpClient client) {
        this.url = url;
        this.client = client;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        HttpPost post = new HttpPost(url.toString());

        HttpResponse response = null;
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            System.err.println("Response error");
            return null;
        }

        String responseString = null;
        try {
            responseString = inputStreamToString(response.getEntity().getContent()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(responseString.toString());
            return responseObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total;
    }
}

class GetTask extends AsyncTask<Void, Void, JSONObject> {
    String url;
    HttpClient client;

    GetTask(String url, HttpClient client) {
        this.url = url;
        this.client = client;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        HttpGet get = new HttpGet(url.toString());

        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            System.err.println("Response error");
            return null;
        }

        String responseString = null;
        try {
            responseString = inputStreamToString(response.getEntity().getContent()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject responseObj = null;
        try {
            responseObj = new JSONObject(responseString.toString());
            return responseObj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return full string
        return total;
    }
}