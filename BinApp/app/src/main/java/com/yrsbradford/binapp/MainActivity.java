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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends ActionBarActivity implements LocationListener {

    private File details;
    private String username, sessionToken;
    private boolean loggedIn;
    private int ACTIVITY_CREATE = 0;
    private Transmit transmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transmit = new Transmit();

        details = new File(this.getApplicationContext().getFilesDir().getPath().toString()+"/login.dat");

        System.out.println("Running BinApp");

        if(!details.exists()){

            try {
                details.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            redirect(LoginActivity.class);
        }else{

            String contents = FileUtils.readFile(details);
            //TODO: decrypt file text

            try {

                JSONObject json = new JSONObject(contents);

                if(json.has("username") && json.has("sessionToken")) {

                    username = json.get("username").toString();
                    sessionToken = json.get("sessionToken").toString();

                    if(WebUtils.isValidSessionToken(username, sessionToken)){
                        loggedIn = true;
                        redirect(Website.class);
                        startSendingData();
                    }else{
                        redirect(LoginActivity.class);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void redirect(Class activity){
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    private void startSendingData(){

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
            save.put("username", username);
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
