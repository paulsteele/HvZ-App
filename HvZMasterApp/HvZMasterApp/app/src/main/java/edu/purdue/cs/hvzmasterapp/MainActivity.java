package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private Server server = Server.getInstance();
    private Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_player);
        // log in player
        String username = SaveSharedPreference.getUserName(MainActivity.this);
        if(username.length() == 0) {
            Log.d("Main", "Starting login activity");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        else {
            setUser(username);
            setupLayout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SaveSharedPreference.clearUserName(this);
            g.setSelf(null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUser(String username) {
        User self = server.getPlayer(username);
        g.setSelf(self);
    }

    public void setupLayout() {
        User self = g.getSelf();

        if (self == null) {
            return;
        }

        // get player to join game
        Log.d("Main", "Self gameid: " + self.gameID);
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
                CardView revive = (CardView) findViewById(R.id.card4);
                revive.setVisibility(View.VISIBLE);
            }
            else {
                text.setText("Human: " + self.username);
            }
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        }

        if (self.gameID.equals("00000000")) {
            Log.d("Main", "Starting game list activity");
            Intent intent = new Intent(this, GameListActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    /* Create user from login credentials */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                this.recreate();
                Log.d("Main", "Login success");
            }
        }
    }

    /* View list of players */
    public void viewPlayerList(View view) {
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }

    /* Tag players */
    public void tag(View view) {
        User self = g.getSelf();
        Intent intent = new Intent(this, TagActivity.class);
        startActivity(intent);
        intent.putExtra("feedcode", self.uniqueID);
    }

    public void revive(View view) {
        User self = g.getSelf();
        if (self.isZombie) {
            Intent intent = new Intent(this, ReviveActivity.class);
            startActivity(intent);
        }
    }

    public void reviveCodes(View view) {
        /*Intent intent = new Intent(this, ReviveCodeActivity.class);
        startActivity(intent);*/
    }
}
