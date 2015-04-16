package edu.purdue.cs.hvzmasterapp;

/**
 * Created by Wells on 4/16/2015.
 */
public class Stats {
    String winner;
    int numHumans;
    int numZombies;
    int hTags;
    int zTags;

    public Stats(String winner, int numHumans, int numZombies, int hTags, int zTags) {
        this.winner = winner;
        this.numHumans = numHumans;
        this.numZombies = numZombies;
        this.hTags = hTags;
        this.zTags = zTags;
    }
}
