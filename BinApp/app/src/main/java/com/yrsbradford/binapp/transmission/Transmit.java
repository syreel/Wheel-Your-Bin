package com.yrsbradford.binapp.transmission;

import android.location.LocationManager;
import android.location.LocationProvider;

import com.yrsbradford.binapp.Channel;
import com.yrsbradford.binapp.MainActivity;
import com.yrsbradford.binapp.WebUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles anything relating to sending location data to the server, will be overhauled in the future
 * Created by 10askinsw on 27/07/2015.
 */
public class Transmit {

    private double longitude, latitude;
    private boolean changed;

    public Transmit(){

    }

    /**
     * Sends the location data to the web server
     */
    public void onUpdate(String sessionToken){
        if(changed) {
            sendData(longitude, latitude, sessionToken);
            changed = false;
        }
    }

    public void setData(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
        changed = true;
        MainActivity.getMain().log(Channel.GPS, "Location data changed");
    }

    /**
     * Sends the geographical location to the web server
     * @param longitude the geographical longitude of the phone
     * @param latitude the geographical latitude of the phone
     * @param sessionToken the token given to the client by the server
     */
    private void sendData(double longitude, double latitude, String sessionToken){
        MainActivity.getMain().log(Channel.SEND, "Sending location data to server");
        WebUtils.sendLocationData(longitude, latitude, sessionToken);
    }
}
