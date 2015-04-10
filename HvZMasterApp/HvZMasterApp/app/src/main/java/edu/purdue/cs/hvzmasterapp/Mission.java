package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/9/2015.
 */
public class Mission {
    private String title;
    private String human_objective;
    private String zombie_objective;

    public Mission(String title, String h_obj, String z_obj) {
        this.title = title;
        this.human_objective = h_obj;
        this.zombie_objective = z_obj;
    }

    public String getTitle() {
        return title;
    }

    public String getHumanObjective() {
        return human_objective;
    }

    public String getZombieObjective() {
        return zombie_objective;
    }
}
