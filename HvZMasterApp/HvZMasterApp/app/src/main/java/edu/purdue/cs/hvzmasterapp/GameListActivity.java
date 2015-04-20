package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameListActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals global = Globals.getInstance();
    User self = global.getSelf();

    ArrayList<Game> gameList;
    ArrayList<String> itemList = new ArrayList<>();
    ListView list;

    int numGames = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            SaveSharedPreference.clearUserName(this);
            Globals.getInstance().setSelf(null);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }

    private class GameAdapter extends ArrayAdapter<Game> {
        public GameAdapter(Context context, ArrayList<Game> games) {
            super(context, 0, games);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Game game = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.gameitem, parent, false);
            }
            // Lookup view for data population
            TextView name = (TextView) convertView.findViewById(R.id.gameName);
            TextView code = (TextView) convertView.findViewById(R.id.gameCode);
            TextView creator = (TextView) convertView.findViewById(R.id.gameCreator);
            // Populate the data into the template view using the data object
            name.setText(game.getName() + "\t");
            code.setText(game.getCode());
            creator.setText("Created by: " + game.getCreator());
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void refresh() {
        gameList = server.getGameList();

        list = (ListView) findViewById(R.id.gamelistview);

        if (gameList == null || gameList.isEmpty()) {
            list.setVisibility(View.GONE);
            TextView text = new TextView(this);
            text.setText("There are currently no games");
        }
        else {
            GameAdapter adapter = new GameAdapter(this, gameList);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                    // select game to join and show confirmation menu
                    Log.d("GameList", "Game name: " + gameList.get(position).getCode());
                    String gamecode = gameList.get(position).getCode();
                    if (gamecode.equals("00000000")) {
                        //do nothing
                    }

                    int status = server.addPlayerToGame(gamecode, self.username);
                    Log.d("Game List", "Adding player to game: " + gamecode);
                    if (status == 0) {
                        finish();
                    }
                    else {
                        Log.e("Game List", "Error adding");
                    }
                }
            });
        }

        if (global.getSelf().isAdmin) {
            View seperator = findViewById(R.id.listseperator);
            seperator.setVisibility(View.VISIBLE);
            TextView footer =  (TextView) findViewById(R.id.footer);
            footer.setVisibility(View.VISIBLE);

            footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* start create game view here */
                    Log.d("Game list", "Create game pressed");
                    createGame();
                }
            });
        }
    }

    public void createGame() {
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                refresh();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }
}
