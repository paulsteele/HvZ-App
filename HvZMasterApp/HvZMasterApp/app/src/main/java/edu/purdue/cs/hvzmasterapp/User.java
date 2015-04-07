package edu.purdue.cs.hvzmasterapp;

public class User{
	public String username;
    public String uniqueID;
    public boolean isAdmin;
    public boolean isZombie;
    public String gameID;

    public User(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;

        this.isZombie = false;
        this.uniqueID = "000000";
        this.gameID = "000000";
    }

    public User(String username, String uniqueID, String gameID, boolean isZombie, boolean isAdmin){
        this.username = username;
        if (uniqueID != null) {
            this.uniqueID = uniqueID;
        }
        else {
            this.uniqueID = "000000";
        }
        if (gameID != null) {
            this.gameID = gameID;
        }
        else {
            this.gameID = "000000";
        }
        this.isZombie = isZombie;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: " + username);
        sb.append("\tisZombie: " + isZombie);
        return sb.toString();
    }
}
