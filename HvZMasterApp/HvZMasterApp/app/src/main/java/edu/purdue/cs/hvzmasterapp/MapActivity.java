package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by manasigoel on 4/15/15.
 */
public class MapActivity extends ActionBarActivity{
    private final static int SELECT_PHOTO = 10;
    Server server = Server.getInstance();
    Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (g.isAdmin()) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.addmap);
            ll.setVisibility(View.VISIBLE);
        }
        else {
            LinearLayout ll = (LinearLayout) findViewById(R.id.addmap);
            ll.setVisibility(View.GONE);
        }

        Bitmap map = server.getMap(Globals.getInstance().getGameCode());
        if (map != null) {
            ImageView view = (ImageView) findViewById(R.id.map);
            view.setImageBitmap(map);
        }
    }

    /* code from http://stackoverflow.com/questions/10296734/image-uri-to-bytesarray */
    public void postMap(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void sendMap(InputStream imageStream) {
        Log.d("sendMap", "sending map");
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        try {
            while ((len = imageStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] selectedMap = byteBuffer.toByteArray();
        server.postMap(selectedMap, Globals.getInstance().getGameCode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Log.d("sendMap", "selected pic");
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();;
                    }
                    sendMap(imageStream);
                }
                break;
        }
    }
}
