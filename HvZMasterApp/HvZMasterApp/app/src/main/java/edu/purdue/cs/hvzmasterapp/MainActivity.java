package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private Server server = Server.getInstance();
    private boolean isLoggedIn = false;
    private Globals g = Globals.getInstance();
    private User self = g.getSelf();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_player);
        // log in player
        if (!isLoggedIn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }
        // get player to join game
        if (g.getSelf().gameId.equals("000000")) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main) {
        }
        else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_plist) {
            Intent intent = new Intent(this, PlayerListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /* Create user from login credentials */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_OK) {
            isLoggedIn = true;
            String user = data.getStringExtra("username");
            g.setSelf(server.getPlayer(user));
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
