package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/2/2015.
 */
public class Game {
    private String name;
    private String code;
    private String creator;

    public Game(String code) {
        this.name = null;
        this.code = code;
        this.creator = null;
    }

    public Game(String name, String code, String creator) {
        this.name = name;
        this.code = code;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCreator() {
        return creator;
    }
}
