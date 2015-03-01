package hvz.server;

public class User{

	public String username;
    public String feedcode;
    public boolean isAdmin;

        public User(String username, String uniqueID, boolean isAdmin){
                this.username = username;
                this.feedcode = uniqueID;
                this.isAdmin = isAdmin;
        }

}