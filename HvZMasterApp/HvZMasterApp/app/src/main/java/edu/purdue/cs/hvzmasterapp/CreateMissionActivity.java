package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by manasigoel on 4/7/15.
 */
public class CreateMissionActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mission);
    }

    public void createMission(View view){
        String name = ((EditText)findViewById(R.id.missionName)).getText().toString();
        String humanObjective = ((EditText)findViewById(R.id.humanObjectives)).getText().toString();
        String zombieObjective = ((EditText)findViewById(R.id.zombieObjectives)).getText().toString();
        String gamecode = g.getGameCode();
        int status = server.addMission(gamecode, name, humanObjective, zombieObjective);
        if (status == 0) {
            TextView msg = (TextView) findViewById(R.id.create_mission_msg);
            msg.setText("Success!");
            msg.setTextColor(Color.GREEN);
            msg.setVisibility(View.VISIBLE);
            //Intent intent = new Intent();
        }
        else {
            TextView msg = (TextView) findViewById(R.id.create_mission_msg);
            msg.setText("Mission could not be added");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
    }
}

