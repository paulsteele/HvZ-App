package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by manasigoel on 4/7/15.
 */
public class MissionListActivity extends ActionBarActivity {

    ArrayList<Mission> missionList;
    ArrayList<String> itemList = new ArrayList<>();
    Server server = Server.getInstance();
    private Globals global = Globals.getInstance();
    ListView list;

    int numMissions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_list);

        refresh();
    }

    private class MissionAdapter extends ArrayAdapter<Mission> {

        public MissionAdapter(Context context, ArrayList<Mission> missions) {
            super(context, 0, missions);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Mission mission = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.missionitem, parent, false);
            }
            // Lookup view for data population
            TextView title = (TextView) convertView.findViewById(R.id.missionTitle);
            TextView humanObjectives = (TextView) convertView.findViewById(R.id.missionHuman);
            TextView zombieObjectives = (TextView) convertView.findViewById(R.id.missionZombie);
            // Populate the data into the template view using the data object
            title.setText(mission.getTitle() + "\n");
            humanObjectives.setText("Human Objectives: " + mission.getHumanObjective());
            zombieObjectives.setText("Zombie Objectives: " + mission.getZombieObjective());
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void refresh(){
        missionList = server.getMissionList(global.getGameCode());
        if (missionList == null) {
            missionList = new ArrayList<>();
            missionList.add(new Mission("There are no missions", null, null));
        }
        list = (ListView) findViewById(R.id.missionListView);

        MissionAdapter adapter = new MissionAdapter(this, missionList);
        list.setAdapter(adapter);
    }
}
