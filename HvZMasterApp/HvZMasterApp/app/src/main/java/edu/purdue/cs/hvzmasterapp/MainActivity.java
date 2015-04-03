package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private Server server = Server.getInstance();
    private boolean isLoggedIn = false;
    private Globals g = Globals.getInstance();
    private User self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_player);
        // log in player
        //SaveSharedPreference.clearUserName(MainActivity.this);
        String username = SaveSharedPreference.getUserName(MainActivity.this);
        if(username.length() == 0) {
            Log.d("Main", "Starting login activity");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        else {
            setupLayout();
        }
    }

    public void setupLayout() {
        /* temp user */
        g.setSelf(new User("wells", null, null, false, true));
        self = g.getSelf();

        // get player to join game
        Log.d("Main", "Self gameid: " + self.gameID);
        if (self.gameID.equals("000000")) {
            Log.d("Main", "Starting game list activity");
            Intent intent = new Intent(this, GameListActivity.class);
            startActivityForResult(intent, 1);
        }
        // distringuish views for admin/players
        if (self.isAdmin) {
            setContentView(R.layout.activity_main_admin);
            TextView text = (TextView) findViewById(R.id.adminlabel);
            text.setText("Admin: " + self.username);
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }
        else {
            setContentView(R.layout.activity_main_player);
            TextView text = (TextView) findViewById(R.id.playerlabel);
            if (self.isZombie) {
                text.setText("Zombie: " + self.username);
            }
            else {
                text.setText("Human: " + self.username);
            }
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }
    }

    /* Create user from login credentials */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_OK) {
            String user = data.getStringExtra("username");
            SaveSharedPreference.setUserName(MainActivity.this, user);
            //g.setSelf(server.getPlayer(user));
            setupLayout();
        }
    }

    /* View list of players */
    public void viewPlayerList(View view) {
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }

    /* Tag players */
    public void tag(View view) {
        Intent intent = new Intent(this, TagActivity.class);
        startActivity(intent);
        intent.putExtra("feedcode", self.uniqueID);
    }
}
