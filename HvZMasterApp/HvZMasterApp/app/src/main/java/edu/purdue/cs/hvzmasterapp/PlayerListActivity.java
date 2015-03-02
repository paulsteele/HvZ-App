package edu.purdue.cs.hvzmasterapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerListActivity extends ActionBarActivity {
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plist_humans);
        type = 0;
        populateList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void populateList() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        /* 0 for humans
         * 1 for zombies
         */
        if (type == 0) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.human_list);
            ll.removeAllViewsInLayout();
            int id = 10000;
            for (int i = 0; i < 10; i++) {
                final TextView view = new TextView(this);
                view.setTextColor(getResources().getColor(R.color.bright_foreground_material_light));
                view.setLayoutParams(params);
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                view.setText(id + ": aasdfasdfads");
                id++;
                ll.addView(view);
            }
        }
        else if (type == 1) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.zombie_list);
            ll.removeAllViewsInLayout();
            int id = 10000;
            for (int i = 0; i < 10; i++) {
                final TextView view = new TextView(this);
                view.setTextColor(getResources().getColor(R.color.bright_foreground_material_dark));
                view.setLayoutParams(params);
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                view.setText(id + ": aasdfasdfads");
                id++;
                ll.addView(view);
            }
        }
    }

    public void switchHuman(View view) {
        setContentView(R.layout.activity_plist_humans);
        type = 0;
        populateList();
    }

    public void switchZombie(View view) {
        setContentView(R.layout.activity_plist_zombies);
        type = 1;
        populateList();
    }


}
