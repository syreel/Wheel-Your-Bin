package com.yrsbradford.binapp.transmission;

import android.location.LocationManager;
import android.location.LocationProvider;

import com.yrsbradford.binapp.WebUtils;

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
     * Sends the location data to the web server
     */
    public void onUpdate(String sessionKey){
            sendData(longitude, latitude, sessionKey);
    }

    public void setData(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Sends the geographical location to the web server
     * @param longitude the geographical longitude of the phone
     * @param latitude the geographical latitude of the phone
     */
    private void sendData(double longitude, double latitude, String sessionKey){
        WebUtils.sendLocationData(longitude, latitude, sessionKey);
    }
}
