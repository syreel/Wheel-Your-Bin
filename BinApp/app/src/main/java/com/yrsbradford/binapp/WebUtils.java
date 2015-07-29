package com.yrsbradford.binapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 10askinsw on 28/07/2015.
 */
public class WebUtils {

    public static String HOST = "http://yawk.net";

    public static String getTextFromPage(String websiteUrl){

        try {
            URL url = new URL(websiteUrl);

            String response = "";

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                response += str;
            }
            in.close();

            return response;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void connectToUrl(String websiteUrl){

        try {
            URL url = new URL(websiteUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidSessionToken(String username, String token){

        String response = getTextFromPage(HOST+"/valid.php?username=" + username + "&token="+token);

        if(response != null && response.length() > 0) {
            try {
                JSONObject json = new JSONObject(response);
                return json.has("status") && json.getBoolean("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            return false;
        }

        return false;
    }

    public static JSONObject getLoginResponse(String username, String password){

        String response = getTextFromPage(HOST+"/login.php?username="+username+"&password="+password);

        try {
            return response != null? new JSONObject(response):null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void sendLocationData(double longitude, double latitude, String sessionKey){
        connectToUrl(HOST+"/submit.php?longitude="+longitude+"&latitude="+latitude+"&sessionKey="+sessionKey);
    }
}
