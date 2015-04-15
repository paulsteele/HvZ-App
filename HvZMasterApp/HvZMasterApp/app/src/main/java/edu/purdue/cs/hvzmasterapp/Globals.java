package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/2/2015.
 */
public class Globals {
    private static Globals instance;
    private User self;

    final public static int NOT_STARTED = 0;
    final public static int STARTED = 1;
    final public static int ENDED = 2;


    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }

    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    public String getUsername() {
        return self.username;
    }

    public Boolean isAdmin() {
        return self.isAdmin;
    }

    public Boolean isZombie() {
        return self.isZombie;
    }

    public String getFeedCode() {
        return self.uniqueID;
    }

    public String getGameCode() {
        return self.gameID;
    }
}
