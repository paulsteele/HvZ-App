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
    private final static int LOGIN = 1;
    private final static int REVIVE = 2;
    private Server server = Server.getInstance();
    private Globals g = Globals.getInstance();

    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_player);
        // log in player
        username = SaveSharedPreference.getUserName(MainActivity.this);
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
    public void onResume() {
        super.onResume();
        setUser(username);
        setupLayout();
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
            startActivityForResult(intent, 1);
        }
        else if (id == R.id.action_leave_game) {
            server.addPlayerToGame("00000000", "00000000");
            setUser(username);
            setupLayout();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUser(String username) {
        User self = server.getPlayer(username);
        g.setSelf(self);
    }

    public void setupLayout() {
        if (g.getSelf() == null) {
            return;
        }

        int status = server.getGameStatus(g.getGameCode());

        if (status == Globals.ENDED) {
            /* show end screen */
        }

        // get player to join game
        Log.d("Main", "Self gameid: " + g.getGameCode());
        // distringuish views for admin/players
        if (g.isAdmin()) {
            setContentView(R.layout.activity_main_admin);
            TextView text = (TextView) findViewById(R.id.adminlabel);
            text.setText("Admin: " + g.getUsername());
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

            CardView  toggleButton = (CardView) findViewById(R.id.card0);
            toggleButton.setVisibility(View.VISIBLE);
            TextView toggleText = (TextView) findViewById(R.id.toggle);
            if (status == Globals.NOT_STARTED) {
                toggleText.setText("Start Game");
            }
            else if (status == Globals.STARTED) {
                toggleText.setText("End Game");
            }
        }
        else {
            setContentView(R.layout.activity_main_player);
            TextView text = (TextView) findViewById(R.id.playerlabel);
            if (g.isZombie()) {
                text.setText("Zombie: " + g.getUsername());
                CardView revive = (CardView) findViewById(R.id.card4);
                revive.setVisibility(View.VISIBLE);
            }
            else {
                text.setText("Human: " + g.getUsername());
            }
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            TextView text2 = (TextView) findViewById(R.id.feedcodelabel);
            text2.setText("Feed code: " + g.getFeedCode());
            text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }

        if (g.getGameCode().equals("00000000")) {
            Log.d("Main", "Starting game list activity");
            Intent intent = new Intent(this, GameListActivity.class);
            startActivityForResult(intent, 1);
        }
        else {
            if (server.isGameOver(g.getGameCode())) {
                Intent intent = new Intent(this, ScoreScreenActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                this.recreate();
                Log.d("Main", "Login success");
            }
            return;
        }
        if (requestCode == REVIVE) {
            if (resultCode == Activity.RESULT_OK) {
                this.recreate();
                Log.d("Main", "revive success");
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
        Intent intent = new Intent(this, TagActivity.class);
        startActivity(intent);
    }

    public void createMission(View view) {
        Intent intent = new Intent(this, CreateMissionActivity.class);
        startActivity(intent);
    }

    public void listMissions(View view) {
        Intent intent = new Intent(this, MissionListActivity.class);
        startActivity(intent);
    }

    public void revive(View view) {
        if (g.isZombie()) {
            Intent intent = new Intent(this, ReviveActivity.class);
            startActivityForResult(intent, REVIVE);
        }
    }

    public void viewReviveCodes(View view) {
        Intent intent = new Intent(this, ReviveCodeActivity.class);
        startActivity(intent);
    }

    public void map(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void toggleGame(View view) {
        int status = server.getGameStatus(g.getGameCode());
        if (status == Globals.NOT_STARTED) {
            server.startGame(g.getGameCode());
        }
        else if (status == Globals.STARTED) {
            server.endGame(g.getGameCode());
        }
    }
}
