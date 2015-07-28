package com.yrsbradford.binapp;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;

import com.yrsbradford.binapp.transmission.Timer;
import com.yrsbradford.binapp.transmission.Transmit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity implements LocationListener {

    private File details;
    private String sessionToken;
    private int ACTIVITY_CREATE = 0;
    private Transmit transmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transmit = new Transmit();

        details = new File(this.getApplicationContext().getFilesDir().getPath().toString()+"/login.dat");

        System.out.println("Running BinApp");

        Intent intent = new Intent(this, Website.class);
        startActivityForResult(intent, ACTIVITY_CREATE);

        //TODO: use session key

        if(!details.exists()){

            try {
                details.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //TODO: redirect to login page
        }else{

            String contents = FileUtils.readFile(details);
            //TODO: decrypt file text

            try {

                JSONObject json = new JSONObject(contents);

                if(json.has("sessionToken")) {
                    sessionToken = json.get("sessionToken").toString();
                    //TODO: authenticate
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Handler handler = new Handler();

        handler.post(new Runnable(){

            @Override
            public void run() {

                transmit.onUpdate();

                handler.postDelayed(this, Timer.MINUTE * 1000 * 10);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        JSONObject save = new JSONObject();

        try {
            save.put("sessionToken", sessionToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileUtils.writeFile(details, save.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        transmit.setData(location.getLongitude(), location.getLatitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider){

    }

    @Override
    public void onProviderDisabled(String provider){

    }
}
