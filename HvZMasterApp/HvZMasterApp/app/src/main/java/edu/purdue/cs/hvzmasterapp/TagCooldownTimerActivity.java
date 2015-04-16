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
        /*while(true) {
            get time
                if(can tag)
                    time.setText("You can tag");
                else
                    time.setText(time that was gotten);

            time.setText("time goes here");
        }*/
    }
}
