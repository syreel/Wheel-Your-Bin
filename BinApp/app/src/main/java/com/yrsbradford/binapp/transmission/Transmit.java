package com.yrsbradford.binapp.transmission;

import android.location.LocationManager;
import android.location.LocationProvider;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 10askinsw on 27/07/2015.
 */
public class Transmit {

    //TODO: Decide on this properly
    private static int UPDATE_TIME = Timer.MINUTE * 10;

    private Timer timer = new Timer();

    private double longitude, latitude;

    public Transmit(){

    }

    /**
     * Checks for whether or not we need to send data to the web server
     * If so, it will send the data
     */
    public void onUpdate(){

            JSONObject json = new JSONObject();

            try {
                json.put("longitude", longitude);
                json.put("latitude", latitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            sendData(json);
    }

    public void setData(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Sends the geographical data to the web server
     * @param data an associative array of keys to values
     */
    private void sendData(JSONObject data){
        //TODO: make this work
    }
}
