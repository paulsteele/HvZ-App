package edu.purdue.cs.hvzmasterapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.content.Intent;

/**
 * Created by manasigoel on 3/4/15.
 */
public class LoginActivity extends ActionBarActivity {
    Server server = Server.getInstance();

    PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login(View view){
        String feedcode = ((EditText)findViewById(R.id.feedcode)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        int status = server.login(feedcode, password);
        if (status == 0) {
            setResult(1);
        }

        finish();
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
