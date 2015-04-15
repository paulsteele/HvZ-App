package edu.purdue.cs.hvzmasterapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Parcelable;



public class TagActivity extends ActionBarActivity implements CreateNdefMessageCallback, OnNdefPushCompleteCallback{

    Globals g = Globals.getInstance();
    Server server = Server.getInstance();
    Globals global = Globals.getInstance();
    User self = global.getSelf();
    NfcAdapter nfcAdapter;

    PopupWindow popup;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        Globals global = Globals.getInstance();
        intent = getIntent();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter==null){
            TextView msg = (TextView) findViewById(R.id.tag_msg);
            msg.setText("No NFC adapter exists");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }else{
            nfcAdapter.setNdefPushMessageCallback(this, this);
            nfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
    }

    public void tag(View view){
        String taggeeFeedcode = ((EditText)findViewById(R.id.feedcode)).getText().toString();
        String playerFeedcode = g.getFeedCode();
        int status = server.tagUsingFeedcodes(playerFeedcode, taggeeFeedcode, g.getGameCode());
        if (status == 0) {
            TextView msg = (TextView) findViewById(R.id.tag_msg);
            msg.setText("Success!");
            msg.setTextColor(Color.GREEN);
            msg.setVisibility(View.VISIBLE);
            setResult(1);
            finish();
        }
        else {
            TextView msg = (TextView) findViewById(R.id.tag_msg);
            msg.setText("Failed to tag player");
            msg.setTextColor(Color.RED);
            msg.setVisibility(View.VISIBLE);
        }
    }

    public void NFCtag(View view){

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String action = intent.getAction();
        if(action != null){
            if(action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
                Parcelable[] parcelables =
                        intent.getParcelableArrayExtra(
                                NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefMessage inNdefMessage = (NdefMessage) parcelables[0];
                NdefRecord[] inNdefRecords = inNdefMessage.getRecords();
                NdefRecord NdefRecord_0 = inNdefRecords[0];
                String inMsg = new String(NdefRecord_0.getPayload());
                String received = ((EditText) findViewById(R.id.feedcode)).getText().toString();
                String playerFeedcode = g.getFeedCode();
                server.tagUsingFeedcodes(inMsg, playerFeedcode, g.getGameCode());
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        final String eventString = "onNdefPushComplete\n" + event.toString();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String playerFeedcode = g.getFeedCode();
                server.tagUsingFeedcodes(eventString, playerFeedcode, g.getGameCode());
            }
        });

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        String playerFeedcode = g.getFeedCode();
        String stringOut = playerFeedcode;
        byte[] bytesOut = stringOut.getBytes();

        NdefRecord ndefRecordOut = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA,
                "text/plain".getBytes(),
                new byte[] {},
                bytesOut);

        NdefMessage ndefMessageout = new NdefMessage(ndefRecordOut);
        return ndefMessageout;
    }


}
