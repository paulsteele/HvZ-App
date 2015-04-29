package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/28/2015.
 */
public class Complaint {
    private String id;
    private String sender;
    private String message;

    public Complaint(String id, String message, String sender) {
        this.id = id;
        this.message = message;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getID() {
        return id;
    }
}
