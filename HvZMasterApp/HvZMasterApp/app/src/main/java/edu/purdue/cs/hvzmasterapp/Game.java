package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/2/2015.
 */
public class Game {
    private String name;
    private String id;
    private String creator;

    public Game(String id) {
        this.name = null;
        this.id = id;
        this.creator = null;
    }

    public Game(String name, String id, String creator) {
        this.name = name;
        this.id = id;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    public String getCreator() {
        return id;
    }
}
