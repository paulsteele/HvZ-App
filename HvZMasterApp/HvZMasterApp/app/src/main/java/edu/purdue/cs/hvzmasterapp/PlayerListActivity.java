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

import java.util.ArrayList;

public class PlayerListActivity extends ActionBarActivity {
    private int type;
    private Server server;
    ArrayList<User> humans;
    ArrayList<User> zombies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plist_humans);
        type = 0;
        server = Server.getInstance();
        init();
        splitUsers();
        populateList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void init() {
        humans = new ArrayList<User>();
        zombies = new ArrayList<User>();
    }

    public void splitUsers() {
        ArrayList<User> users = server.getUserList();
        if (users == null) {
            System.err.println("getUserList not working :(");
            return;
        }
        int numH = 0;
        int numZ = 0;
        for (User u : users) {
            if (!u.isAdmin) {
                if (!u.isZombie) {
                    humans.add(u);
                }
                else {
                    zombies.add(u);
                }
            }
        }
    }

    public void populateList() {
        /* 0 for humans
         * 1 for zombies
         */
        if (type == 0) {
            LinearLayout hl = (LinearLayout) findViewById(R.id.human_list);
            hl.removeAllViewsInLayout();
            int i = 1;
            for (User u : humans) {
                addPlayer(hl, u, i++);
            }
        }
        else {
            LinearLayout zl = (LinearLayout) findViewById(R.id.zombie_list);
            zl.removeAllViewsInLayout();
            int i = 1;
            for (User u : zombies) {
                addPlayer(zl, u, i++);
            }
        }
    }

    private void addPlayer(LinearLayout ll, User u, int n) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final TextView view = new TextView(this);
        if (type == 0) {
            view.setTextColor(getResources().getColor(R.color.bright_foreground_material_light));
        }
        else {
            view.setTextColor(getResources().getColor(R.color.bright_foreground_material_dark));
        }
        view.setLayoutParams(params);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        view.setText(n + ": " + u.username);
        ll.addView(view);
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
