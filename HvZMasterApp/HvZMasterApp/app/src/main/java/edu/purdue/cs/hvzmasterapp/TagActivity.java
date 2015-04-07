package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.view.View;
import android.widget.TextView;


public class TagActivity extends ActionBarActivity {

    Server server = Server.getInstance();

    PopupWindow popup;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        intent = getIntent();
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

    public void tag(View view){
        String taggeeFeedcode = ((EditText)findViewById(R.id.feedcode)).getText().toString();
        String playerFeedcode = intent.getStringExtra("feedcode");
        int status = server.tagUsingFeedcodes(playerFeedcode, taggeeFeedcode);
        if (status == 0) {
            TextView msg = (TextView) findViewById(R.id.tag_msg);
            msg.setText("Success!");
            msg.setTextColor(Color.GREEN);
            msg.setVisibility(View.VISIBLE);
            setResult(1);
            finish();
        }
        else {
            TextView msg = (TextView) findViewById(R.id.tag_msg);
            msg.setText("Failed to tag player");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
    }

}
