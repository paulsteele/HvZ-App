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
        setContentView(R.layout.activity_register);
    }

    public void submit(View view) {
        String name = ((EditText)findViewById(R.id.nameinput)).getText().toString();
        int status = server.createGame(name, g.getSelf().username);
    }
}
