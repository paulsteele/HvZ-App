package edu.purdue.cs.hvzmasterapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;


/**
 * Created by manasigoel on 4/15/15.
 */
public class MapActivity extends ActionBarActivity{
    Server server = Server.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bitmap map = server.getMap();

        ImageView view = (ImageView) findViewById(R.id.map);
        view.setImageBitmap(map);
    }

}
