package com.yrsbradford.binapp.transmission;

import org.json.JSONObject;

/**
 * Created by 10askinsw on 27/07/2015.
 */
public class Transmit {

    //TODO: Decide on this properly
    private static int UPDATE_TIME = Timer.MINUTE * 10;

    private static Transmit instance = new Transmit();

    private Timer timer = new Timer();
    
    public Transmit(){

    }

    public static Transmit getInstance() {
        return instance;
    }

    /**
     * Checks for whether or not we need to send data to the web server
     * If so, it will send the data
     */
    public void onUpdate(){

        if(timer.hasElapsed(UPDATE_TIME)){

            //TODO: send geographical data

            timer.reset();
        }

    }

    /**
     * Sends the geographical data to the web server
     * @param data an associative array of keys to values
     */
    private void sendData(JSONObject data){
        //TODO: make this work
    }
}
