package edu.purdue.cs.hvzmasterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

    PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_main) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_login) {
            return true;
        }
        else if (id == R.id.action_plist) {
            Intent intent = new Intent(this, PlayerListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            setResult(1);
            finish();
        }
        else {
            TextView msg = (TextView) findViewById(R.id.login_msg);
            msg.setText("Username/password does not match");
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
