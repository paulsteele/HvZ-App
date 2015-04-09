package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Wells on 4/7/2015.
 */
public class CreateGameActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategame);
    }

    public void createGame(View view) {
        String username = g.getSelf().username;
        String name = ((EditText)findViewById(R.id.nameinput)).getText().toString();
        Game game = server.createGame(name, username);
        if (game != null) {
            server.addPlayerToGame(game.getCode(), username);
            g.setSelf(server.getPlayer(username));
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else {
            // error handling
        }
    }
}
