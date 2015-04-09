package hvz.server;

public class Player extends User {

    public boolean isZombie;

    public Player(String username, String uniqueID){
            super(username, uniqueID, false);
    }


}