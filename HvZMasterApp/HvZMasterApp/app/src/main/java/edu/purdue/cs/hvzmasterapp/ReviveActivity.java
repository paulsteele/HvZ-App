package edu.purdue.cs.hvzmasterapp;

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
        server.revive(true);
        finish();
    }
}
