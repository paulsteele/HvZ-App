package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getCode(View view) {
        EditText text = (EditText) findViewById(R.id.feedcodeinput);
        if (text.getText().toString().equals("")) {
            String code = "code goes here";
            //code = getCode();
            text.setText(code, TextView.BufferType.NORMAL);
        }
    }

    public void register(View view) {
        String user = ((EditText) findViewById(R.id.userinput)).getText().toString();
        String pass = ((EditText) findViewById(R.id.passinput)).getText().toString();
        System.out.println("user: " + user + "\npass: " + pass);
        // Register user
    }
}
