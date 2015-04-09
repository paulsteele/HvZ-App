package edu.purdue.cs.hvzmasterapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by manasigoel on 4/7/15.
 */
public class MissionListActivity extends ActionBarActivity{

    ArrayList<Game> missionList;
    ArrayList<String> itemList = new ArrayList<>();
    Server server = Server.getInstance();
    private Globals global = Globals.getInstance();
    ListView list;

    int numMissions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        /*missionList = server.getMissionList();
        if (missionList == null) {
            missionList = new ArrayList<>();
            missionList.add(new Game("There are no missions", null, null));
        }
        list = (ListView) findViewById(R.id.missionListView);*/
    }

}
