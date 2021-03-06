package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ScoreScreenActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        Stats stats = server.getStats(g.getGameCode());
        setStats(stats);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SaveSharedPreference.clearUserName(this);
            g.setSelf(null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.action_leave_game) {
            server.leaveGame(g.getUsername());
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }

    public void setStats(Stats stats) {
        TextView text = (TextView) findViewById(R.id.score_winner);
        text.setText(stats.winner);
        text = (TextView) findViewById(R.id.score_humans);
        text.setText(Integer.toString(stats.numHumans));
        text = (TextView) findViewById(R.id.score_zombies);
        text.setText(Integer.toString(stats.numZombies));
        text = (TextView) findViewById(R.id.score_htags);
        text.setText(Integer.toString(stats.hTags));
        text = (TextView) findViewById(R.id.score_ztags);
        text.setText(Integer.toString(stats.zTags));
    }
}
