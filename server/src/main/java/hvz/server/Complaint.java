package hvz.server;

public class Complaint {
	public String ccode;
    public String sender;
	public String message;
    public String gamecode;

        public Complaint(String ccode, String sender, String message, String gamecode){
        	this.ccode = ccode;
        	this.sender = sender;
            this.message = message;
            this.gamecode = gamecode;
        }

}
