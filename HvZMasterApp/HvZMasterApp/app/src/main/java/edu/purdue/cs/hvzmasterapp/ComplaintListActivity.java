package edu.purdue.cs.hvzmasterapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ComplaintListActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);
    }

    public void deleteComplaint(String id) {
        String gamecode = g.getGameCode();
        server.deleteComplaint(id, gamecode);
    }
}
