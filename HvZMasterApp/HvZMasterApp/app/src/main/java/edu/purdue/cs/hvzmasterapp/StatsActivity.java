package edu.purdue.cs.hvzmasterapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class StatsActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Stats stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        TextView text = (TextView) findViewById(R.id.score_humans);
        text.setText(Integer.toString(stats.numHumans));
        text = (TextView) findViewById(R.id.score_zombies);
        text.setText(Integer.toString(stats.numZombies));
        text = (TextView) findViewById(R.id.score_htags);
        text.setText(Integer.toString(stats.hTags));
        text = (TextView) findViewById(R.id.score_ztags);
        text.setText(Integer.toString(stats.zTags));stats = server.getStats(Globals.getInstance().getGameCode());

    }


}
