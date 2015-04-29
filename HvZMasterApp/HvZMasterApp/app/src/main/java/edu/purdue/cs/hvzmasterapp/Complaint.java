package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/28/2015.
 */
public class Complaint {
    String id;
    String sender;
    String message;

    public Complaint(String id, String message, String sender) {
        this.id = id;
        this.message = message;
        this.sender = sender;
    }
}
