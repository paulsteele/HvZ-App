package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegisterActivity extends ActionBarActivity {
    Server server = Server.getInstance();

    PopupWindow popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_main) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void register(View view) {
        /* Get user inputs */
        String user = ((EditText) findViewById(R.id.userinput)).getText().toString();
        String pass = ((EditText) findViewById(R.id.passinput)).getText().toString();
        String repass = ((EditText) findViewById(R.id.passreinput)).getText().toString();
        boolean admin = ((CheckBox) findViewById(R.id.admin_check)).isChecked();

        TextView msg = (TextView) findViewById(R.id.register_msg);

        System.err.println("user: " + user + "\nadmin: " + admin + "\npass1: " + pass + "\npass2: " + repass);
        if (!pass.equals(repass)) {
            msg.setText("Passwords do not match.");
            ((EditText) findViewById(R.id.passinput)).setText("");
            ((EditText) findViewById(R.id.passreinput)).setText("");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
            return;
        }

        if (user.equals("") || pass.equals("")) {
            msg.setText("Field(s) are empty");
            msg.setTextColor(Color.RED);
        }
        else {
            // Register user
            String passhash = hash(pass);
            Log.d("register", "Password hash: " + passhash);
            int error = server.register(user, passhash, admin);
            if (error == 0) {
                msg.setText("Success!");

                msg.setTextColor(Color.GREEN);
                msg.setVisibility(View.VISIBLE);

                finish();
            }
            else {
                msg.setText("Username and/or feedcode already taken.");
                msg.setTextColor(Color.RED);
            }
        }
        msg.setVisibility(View.VISIBLE);
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
