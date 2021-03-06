package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.content.Intent;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by manasigoel on 3/4/15.
 */
public class LoginActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    public void login(View view){
        String username = ((EditText)findViewById(R.id.username)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String hash = hash(password);
        int status = server.login(username, hash);
        if (status == 0) {
            TextView msg = (TextView) findViewById(R.id.login_msg);
            msg.setText("Success!");
            msg.setTextColor(Color.GREEN);
            msg.setVisibility(View.VISIBLE);

            SaveSharedPreference.setUserName(LoginActivity.this, username);

            g.setSelf(server.getPlayer(username));

            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);

            finish();
        }
        else if (status == 1) {
            TextView msg = (TextView) findViewById(R.id.login_msg);
            msg.setText("Username/password does not match");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
        else if (status == -1) {
            TextView msg = (TextView) findViewById(R.id.login_msg);
            msg.setText("Unable to connect to server");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
        else {
            TextView msg = (TextView) findViewById(R.id.login_msg);
            msg.setText("Unknown error");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public String hash(String string)
    {
        MessageDigest sha1 = null;
        try
        {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            sha1.update(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
        }

        byte[] hash = sha1.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
