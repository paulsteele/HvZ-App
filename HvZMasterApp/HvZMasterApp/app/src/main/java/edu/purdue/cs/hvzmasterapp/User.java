package edu.purdue.cs.hvzmasterapp;

public class User{
	public String username;
    public String uniqueID;
    public boolean isAdmin;
    public boolean isZombie;

    public User(String username, String uniqueID, boolean isZombie, boolean isAdmin){
        this.username = username;
        this.uniqueID = uniqueID;
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
