package com.yrsbradford.binapp.transmission;

import com.yrsbradford.binapp.WebUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 10askinsw on 28/07/2015.
 */
public class LoginEvent {

    private String username, password;
    private JSONObject response;

    public LoginEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void login(){
        response = WebUtils.getLoginResponse(username, password);
    }

    public boolean isValid(){

        try {
            return response != null && response.getBoolean("status") && getSessionToken() != null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getSessionToken(){

        try {
            return this.response.getString("sessionToken");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
