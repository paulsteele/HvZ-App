package edu.purdue.cs.hvzmasterapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
    ArrayList<Game> gameList;
    ArrayList<String> itemList = new ArrayList<>();
    Server server = Server.getInstance();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        gameList = server.getGameList();
        if (gameList == null) {
            gameList.add(new Game("There are currently no games available.", "000000"));
        }

        list = (ListView) findViewById(R.id.gamelistview);
        GameAdapter adapter = new GameAdapter(this, gameList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // select game to join and show confirmation menu
                Log.d("GameList", "Game name: " + gameList.get(position).getName());
                if (gameList.get(position).getId().equals("000000")) {
                    //do nothing
                }
            }
        });
    }

    private void addItems() {
        gameList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            gameList.add(new Game("Game #" + (i+1), Integer.toString(i*10)));
        }
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
            TextView id = (TextView) convertView.findViewById(R.id.gameId);
            // Populate the data into the template view using the data object
            name.setText(game.getName());
            id.setText(game.getId());
            // Return the completed view to render on screen
            return convertView;
        }
    }
}
