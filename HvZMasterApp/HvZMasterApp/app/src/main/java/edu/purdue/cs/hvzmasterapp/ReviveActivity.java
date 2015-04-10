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
 * Created by Wells on 4/9/2015.
 */
public class ReviveActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revive);
    }

    public void revive(View view) {
        String code = ((EditText) findViewById(R.id.reviveinput)).getText().toString();
        int status = server.revive(g.getGameCode(), code, g.getFeedCode());

        if (status == 0) {
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else {
            TextView text = (TextView) findViewById(R.id.revive_msg);
            text.setTextColor(Color.RED);
            if (status == -1) {
                text.setText("Server error");
            }
            else if (status == 1) {
                text.setText("Invalid revive code");
            }
            text.setVisibility(View.VISIBLE);
        }
    }
}
