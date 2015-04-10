package edu.purdue.cs.hvzmasterapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Wells on 4/9/2015.
 */
public class ReviveCodeActivity extends ActionBarActivity {
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    ArrayList<String> rcodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revive_code_list);
        refresh();
    }

    public void refresh() {
        rcodes = server.getAllReviveCodes(g.getGameCode());

        LinearLayout ll = (LinearLayout) findViewById(R.id.revive_code_list);
        ll.removeAllViewsInLayout();

        if (rcodes.isEmpty()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final TextView view = new TextView(this);
            view.setTextColor(Color.BLACK);
            view.setLayoutParams(params);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            view.setText("No revive codes");
            ll.addView(view);
        }
        else {
            for (String code : rcodes) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                final TextView view = new TextView(this);
                view.setTextColor(Color.BLACK);
                view.setLayoutParams(params);
                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                view.setText(code);
                ll.addView(view);
            }
        }
    }

    public void createReviveCode(View view) {
        String code = server.getNewReviveCode(g.getGameCode());
        if (code != null) {
            Log.d("revive code list", "new code success");
            refresh();
        }
        else {
            Log.d("revive code list", "error");
        }
    }
}
