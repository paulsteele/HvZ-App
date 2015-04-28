package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ComplaintActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
    }

    public void sendComplaint(View view) {
        String gamecode = g.getGameCode();
        String sender = g.getUsername();
        String message = ((EditText) findViewById(R.id.complaint_message)).getText().toString();
        int status = server.sendComplaint(message, sender, gamecode);

        if (status == 0) {
            TextView msg = (TextView) findViewById(R.id.send_complaint_msg);
            msg.setText("Success!");
            msg.setTextColor(Color.GREEN);
            msg.setVisibility(View.VISIBLE);
        }
        else {
            TextView msg = (TextView) findViewById(R.id.send_complaint_msg);
            msg.setText("Complaint could not be sent");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
    }
}
