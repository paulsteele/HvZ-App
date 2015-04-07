package edu.purdue.cs.hvzmasterapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameListActivity extends ActionBarActivity {
    ArrayList<Game> gameList;
    ArrayList<String> itemList = new ArrayList<>();
    Server server = Server.getInstance();
    private Globals global = Globals.getInstance();
    ListView list;

    int numGames = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        gameList = server.getGameList();
        if (gameList == null) {
            gameList = new ArrayList<>();
            gameList.add(new Game("There are currently no games available.", "000000", null));
        }

        list = (ListView) findViewById(R.id.gamelistview);

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
                }
            });
        }

        GameAdapter adapter = new GameAdapter(this, gameList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // select game to join and show confirmation menu
                Log.d("GameList", "Game name: " + gameList.get(position).getName());
                if (gameList.get(position).getCode().equals("000000")) {
                    //do nothing
                }
            }
        });
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
            //TextView name = (TextView) convertView.findViewById(R.id.gameName);
            TextView id = (TextView) convertView.findViewById(R.id.gameId);
            // Populate the data into the template view using the data object
            //name.setText(game.getName());
            id.setText(game.getCode());
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void createGame(View view) {
        Intent intent = new Intent(this, CreateGameActivity.class);
        startActivity(intent);
        overridePendingTransition(R.animator.slide_left, R.animator.slide_right);
    }
}
