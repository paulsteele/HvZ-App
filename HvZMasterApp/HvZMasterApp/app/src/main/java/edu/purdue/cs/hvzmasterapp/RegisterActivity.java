package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


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
            return true;
        }
        else if (id == R.id.action_register) {
            return true;
        }
        else if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_plist) {
            Intent intent = new Intent(this, PlayerListActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCode(View view) {
        EditText text = (EditText) findViewById(R.id.feedcodeinput);
        if (text.getText().toString().equals("")) {
            boolean admin = ((CheckBox) findViewById(R.id.admin_check)).isChecked();
            String code = server.getNewFeedcode(admin);
            System.err.println("feedcode: " + code);
            text.setText(code, TextView.BufferType.EDITABLE);
        }
    }

    public void register(View view) {
        /* Get user inputs */
        String user = ((EditText) findViewById(R.id.userinput)).getText().toString();
        String pass = ((EditText) findViewById(R.id.passinput)).getText().toString();
        String repass = ((EditText) findViewById(R.id.passreinput)).getText().toString();
        String feedcode = ((EditText) findViewById(R.id.feedcodeinput)).getText().toString();
        boolean admin = ((CheckBox) findViewById(R.id.admin_check)).isChecked();

        TextView msg = (TextView) findViewById(R.id.register_msg);

        System.err.println("user: " + user + "\nadmin: " + admin + "\nfeedcode: " + feedcode + "\npass1: " + pass + "\npass2: " + repass);
        if (!pass.equals(repass)) {
            msg.setText("Passwords do not match.");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
            return;
        }

        if (user.equals("") || pass.equals("") || feedcode.equals("")) {
            msg.setText("Field(s) are empty");
            msg.setTextColor(Color.RED);
        }
        else {
            // Register user
            int error = server.register(user, feedcode, pass, admin);
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
}
