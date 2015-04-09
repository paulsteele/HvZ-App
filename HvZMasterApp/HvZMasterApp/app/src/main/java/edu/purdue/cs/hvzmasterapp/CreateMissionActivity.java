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

    PopupWindow popup;

    public void createMission(View view){
        //String name = ((EditText)findViewById(R.id.missionName)).getText().toString();
        //String objectives = ((EditText)findViewById(R.id.missionObjectives)).getText().toString();
        /*int status = server.addMission(name, ojbectives);
        if (status == 0) {
            msg.setText("Success!");
            msg.setTextColor(Color.GREEN);
            msg.setVisibility(View.VISIBLE);
            Intent intent = new Intent();
        }
        else {
            msg.setText("Mission could not be added");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }*/
    }
}

