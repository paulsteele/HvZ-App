package edu.purdue.cs.hvzmasterapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

/**
 * Created by Wells on 4/9/2015.
 */
public class ReviveCodeActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    ArrayList<String> rcodes = server.getAllReviveCodes(g.getGameCode());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revive_code_list);
    }
}
