package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/2/2015.
 */
public class Game {
    private String name;
    private String id;


    public Game(String id) {
        this.name = null;
        this.id = id;
    }

    public Game(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
