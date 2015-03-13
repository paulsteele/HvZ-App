package edu.purdue.cs.hvzmasterapp;

public class Player extends User {

        public boolean isZombie;

        public Player(String username, String uniqueID){
                super(username, uniqueID, false, false);
        }

        public void isZombie(){
                isZombie = true;
        }

        public void isHuman(){
                isZombie = false;
        }

}
