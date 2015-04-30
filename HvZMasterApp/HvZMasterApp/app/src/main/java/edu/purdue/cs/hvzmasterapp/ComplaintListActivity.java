package edu.purdue.cs.hvzmasterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ComplaintListActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();
    ArrayList<Complaint> complaints;
    Complaint selected;
    ListView list;
    ComplaintAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        list = (ListView) findViewById(R.id.complaintList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                // select game to join and show confirmation menu
                Complaint complaint = complaints.get(position);

                selected = complaint;
            }
        });
        refresh();
    }

    private class ComplaintAdapter extends ArrayAdapter<Complaint> {

        public ComplaintAdapter(Context context, ArrayList<Complaint> complaints) {
            super(context, 0, complaints);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Complaint complaint = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.complaintitem, parent, false);
            }
            // Lookup view for data population
            TextView sender = (TextView) convertView.findViewById(R.id.sender);
            TextView message = (TextView) convertView.findViewById(R.id.message);

            // Populate the data into the template view using the data object
            sender.setText("From: " + complaint.getSender());
            message.setText(complaint.getMessage());

            // Return the completed view to render on screen
            return convertView;
        }
    }

    public void refresh(){
        complaints = server.getComplaints(g.getGameCode());
        if (complaints == null) {
            complaints = new ArrayList<>();
        }

        list = (ListView) findViewById(R.id.complaintList);

        adapter = new ComplaintAdapter(this, complaints);
        list.setAdapter(adapter);
    }

    public void deleteComplaint(View view) {
        if (selected != null) {
            String gamecode = g.getGameCode();
            server.deleteComplaint(selected.getID(), gamecode);
            selected = null;
            refresh();
        }
    }
}

