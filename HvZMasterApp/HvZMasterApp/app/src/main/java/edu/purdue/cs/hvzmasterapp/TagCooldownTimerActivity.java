package edu.purdue.cs.hvzmasterapp;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by manasigoel on 4/16/15.
 */
public class TagCooldownTimerActivity extends ActionBarActivity {
    Globals g = Globals.getInstance();
    Server server = Server.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_cooldown);
        TextView time = (TextView) findViewById(R.id.tag_cooldown_timer);
        int t = server.getTagCooldownTime(g.getGameCode(), g.getFeedCode());
        if(t == -1)
            time.setText("something went wrong");
        else if(t == 0)
            time.setText("You may now tag");
        else {
            int sec;
            int min;
            min = t/60;
            sec = t%60;
            time.setText(Integer.toString(min)+":"+Integer.toString(sec));
        }
    }
}
