package edu.purdue.cs.hvzmasterapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.InterruptedException;
import java.lang.StringBuilder;
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

    //returns a user using its unique ID
    public User getPlayer(String username){
        GetTask task = new GetTask(serviceURL + "/user/" + username, client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("getUser", "Server reponse error");
            return null;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("getUser " + username, "Success");
                return new User(response.getString("username"), response.getString("feedcode"), response.getString("gamecode"),
                        response.getBoolean("isZombie"), response.getBoolean("isAdmin"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int addMission(String gamecode, String title, String humanobjective, String zombieobjective){
        JSONObject missionRequest = new JSONObject();

        try{
            missionRequest.put("title", title);
        } catch(JSONException e){
            e.printStackTrace();
        }

        try{
            missionRequest.put("humanobjective",humanobjective);
        } catch(JSONException e){
            e.printStackTrace();
        }

        try{
            missionRequest.put("zombieobjective",zombieobjective);
        } catch(JSONException e){
            e.printStackTrace();
        }

        Log.d("Mission", missionRequest.toString());

        PostTask task = new PostTask(serviceURL + "/" + gamecode + "/mission",client,missionRequest);

        JSONObject missionResponse = null;

        try{
            missionResponse = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if( missionResponse == null){
            Log.e ("Get mission","Server Response error");
            return 1;
        }

        return 0;

    }

    //generates and returns a new Feed Code
    public String getNewFeedcode(String gamecode, boolean admin){
        JSONObject request = new JSONObject();
        try {
            request.put("admin", admin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Feedcode", request.toString());

        PostTask task = new PostTask(serviceURL + "/" + gamecode + "/" + "feedcode", client, request);

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

    public ArrayList<String> getAllReviveCodes(String gamecode) {
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/revivecodes", client);

        JSONObject response = null;

        try {
            response = task.execute().get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        if (response == null){
            Log.e("Get revive code", "Server response error");
            return null;
        }

        ArrayList<String> list = new ArrayList<>();
        try {
            JSONArray codes = response.getJSONArray("revivecodes");
            for (int i = 0; i < codes.length(); i++) {
                String code = codes.getString(i);
                list.add(code);
            }
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    public String getNewReviveCode(String gamecode){
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/revivecode", client);

        JSONObject response = null;

        try {
            response = task.execute().get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        if (response == null){
            Log.e("Get revive code", "Server response error");
            return null;
        }

        try {
            return response.getString("revivecode");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //returns list of missions
    public ArrayList<Mission> getMissionList(String gamecode){
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/mission", client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Get mission list", "Server reponse error");
            return null;
        }

        // Parse JSON Object and place users into list
        ArrayList<Mission> list = new ArrayList<>();
        try {
            JSONArray missions = response.getJSONArray("missions");

            for (int i = 0; i < missions.length(); i++) {
                JSONObject mission = missions.getJSONObject(i);
                String title = mission.getString("title");
                String humanobjective = mission.getString("humanobjective");
                String zombieobjective = mission.getString("zombieobjective");
                Mission m = new Mission(title, humanobjective, zombieobjective);
                list.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    public Mission getMission(String gamecode, String title) {
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/mission/" + title, client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Get mission list", "Server reponse error");
            return null;
        }

        // Parse JSON Object and place users into list
        Mission mission;
        try {
            String humanobjective = response.getString("humanobjective");
            String zombieobjective = response.getString("zombieobjective");
            mission = new Mission(title, humanobjective, zombieobjective);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return mission;
    }

    public int getTagCooldownTime(String gamecode, String player_feedcode){

        return -1;
    }

    //returns a new list of users
    public ArrayList<User> getUserList(String gamecode) {
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/user", client);

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
                User u = new User(usr, code, null, isZombie, admin);
                list.add(u);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    //returns 0 if tagging was successful
    public int tagUsingFeedcodes(String tagger, String taggee, String gamecode){
        JSONObject tagRequest = new JSONObject();

        try{
            tagRequest.put("tagger", tagger);
            tagRequest.put("tagged", taggee);
        } catch(JSONException e){
            e.printStackTrace();
        }

        Log.d("Tag", tagRequest.toString());

        PostTask task = new PostTask(serviceURL + "/" + gamecode + "/tag",client,tagRequest);

        JSONObject tagResponse = null;

        try{
            tagResponse = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(tagResponse == null){
            Log.e ("Tag failed", "Server response error");
            return -1;
        }

        try {
            if (tagResponse.getBoolean("success")) {
                return 0;
            }
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    //returns 0 if user reverted to human successfully
    public int revive(String gamecode, String revivecode, String feedcode){

        JSONObject request = new JSONObject();
        try{
            request.put("revivecode", revivecode);
            request.put("feedcode", feedcode);
        }catch(JSONException e){
            e.printStackTrace();
        }
        Log.d("revive", request.toString());

        PostTask task = new PostTask(serviceURL + "/" + gamecode + "/revivecode", client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Turn Human", "Server response error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                return 0;
            }
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    //returns an image of a map
    public void getMap(){
    
    }

    //returns 0 if game is successfully started
    public int startGame(String gamecode) {
        PutTask task = new PutTask(serviceURL + "/" + gamecode, client, new JSONObject());

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Start game", "server response error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("Start game", "success");
                return 0;
            }
            else {

                Log.e("Start game", "success error");
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Start game", "error");
        return -1;
    }

    //returns 0 if game is successfully ended
    public int endGame(String gamecode) {
        PostTask task = new PostTask(serviceURL + "/" + gamecode + "/end", client, new JSONObject());

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Start game", "server response error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("End game", "success");
                return 0;
            }
            else {

                Log.e("End game", "success error");
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("End game", "error");
        return -1;
    }

    // check if game is started/ended
    public int getGameStatus(String gamecode) {
        if (gamecode.equals("00000000")) {
            Log.d("game status", "no game");
            return 0;
        }

        Log.d("game status", gamecode);

        GetTask task = new GetTask(serviceURL + "/" + gamecode, client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("Game status", "server response error");
            return -1;
        }

        try {
            if (response.getBoolean("started")) {
                if (isGameOver(gamecode)) {
                    return Globals.ENDED;
                }
                else {
                    return Globals.STARTED;
                }
            }
            else {
                Log.e("Game status", "not started");
                return Globals.NOT_STARTED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Game status", "error");
        return -1;
    }

    public boolean isGameOver(String gamecode) {
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/end", client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("getStats", "Server reponse error");
            return false;
        }

        try {
            return response.getBoolean("gameover");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Stats getStats(String gamecode) {
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/end", client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("getStats", "Server reponse error");
            return null;
        }

        try {
            String winner = response.getString("winner");
            int humans = response.getInt("humans");
            int zombies = response.getInt("zombies");
            int hTags = response.getInt("humantags");
            int zTags = response.getInt("zombietags");
            return new Stats(winner, humans, zombies, hTags, zTags);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // returns 0 if register was successful, else non-zero
    public Game createGame(String gamename, String creator) {
        JSONObject request = new JSONObject();

        try {
            request.put("gamename", gamename);
            request.put("creator", creator);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostTask task = new PostTask(serviceURL, client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            return null;
        }

        try {
            if (response.getBoolean("success")) {
                return new Game(gamename, response.getString("gamecode"), creator);
            }
            else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // returns list of all games
    public ArrayList<Game> getGameList() {
        GetTask task = new GetTask(serviceURL, client);

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
        ArrayList<Game> list = new ArrayList<>();
        try {
            JSONArray array = response.getJSONArray("games");

            for (int i = 0; i < array.length(); i++) {
                JSONObject games = array.getJSONObject(i);
                String gamename = games.getString("gamename");
                String gamecode = games.getString("gamecode");
                String creator = games.getString("creator");
                list.add(new Game(gamename, gamecode, creator));
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int addPlayerToGame(String gamecode, String username) {
        String feedcode = getNewFeedcode(gamecode, false);

        /* put request url */

        JSONObject request = new JSONObject();
        try {
            request.put("feedcode", feedcode);
            request.put("gamecode", gamecode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PutTask task = new PutTask(serviceURL + "/user/" + username, client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                return 0;
            }
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int leaveGame(String username) {

        /* put request url */

        JSONObject request = new JSONObject();
        try {
            request.put("feedcode", "00000000");
            request.put("gamecode", "00000000");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PutTask task = new PutTask(serviceURL + "/user/" + username, client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                return 0;
            }
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // returns 0 if login was successful, else non-zero
    public int login(String identifier, String password){
        StringBuilder url = new StringBuilder(serviceURL);
        url.append("/user/");
        url.append(identifier);

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
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Login", "Error");
        return -1;
    }

    // returns 0 if register was successful, else non-zero
    public int register(String username, String password, boolean admin) {
        StringBuilder url = new StringBuilder(serviceURL);
        url.append("/user");

        JSONObject request = new JSONObject();
        try {
            request.put("username", username);
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
            Log.e("Register", "Server response error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("Register", "success");
                return 0;
            }
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Register", "error");
        return -1;
    }

    /* get list of complaints */
    public String[] getComplaints(String gamecode) {
        GetTask task = new GetTask(serviceURL + "/" + gamecode + "/complaint", client);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            return null;
        }
        /*
        try {

        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        return null;
    }

    /*
     * sends complaint to server
     * 0 for success, non-zero for error
     */
    public int sendComplaint(String message, String sender, String gamecode) {
        JSONObject request = new JSONObject();
        try {
            request.put("sender", sender);
            request.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        PostTask task = new PostTask(serviceURL + "/" + gamecode + "/complaint", client, request);

        JSONObject response = null;
        try {
            response = task.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if (response == null) {
            Log.e("sendComplaint", "Server response error");
            return -1;
        }

        try {
            if (response.getBoolean("success")) {
                Log.e("sendComplaint", "success");
                return 0;
            }
            else {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("sendComplaint", "error");
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

class PutTask extends AsyncTask<Void, Void, JSONObject> {
    HttpClient client;
    String url;
    JSONObject request;

    PutTask(String url, HttpClient client, JSONObject request) {
        this.client = client;
        this.url = url;
        this.request = request;
    }

    @Override
    protected JSONObject doInBackground(Void... v) {
        // Create post method
        HttpPut put = new HttpPut(url);

        HttpResponse response = null;
        try {
            // Add JSON object to post
            StringEntity se = new StringEntity(request.toString());
            put.setEntity(se);
            put.setHeader("Accept", "application/json");
            put.setHeader("Content-type", "application/json");

            // Send request and get response
            response = client.execute(put);
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
            Log.e("HTTP Put", "Error getting JSON Object");
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