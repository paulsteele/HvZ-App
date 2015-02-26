package edu.purdue.cs.hvzmasterapp;

import java.util.ArrayList;

public class Server{

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


}
