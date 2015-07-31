package com.yrsbradford.binapp.transmission;

import com.yrsbradford.binapp.WebUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 10askinsw on 31/07/2015.
 */
public class RegisterEvent {

    private String email, username, password, confirmPassword;
    private JSONObject response;

    public RegisterEvent(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void register(){

        String text = WebUtils.getTextFromPage(WebUtils.HOST + "/register.php?&email="+email
                +"&username="+username
                +"&password="+password
                +"&confirmPassword="+confirmPassword);

        try {
            response = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean worked(){

        try {
            return response != null && response.getInt("status") == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getMessage(){

        try {
            return response != null ? response.getString("reason") : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean connectionFailed(){
        return response == null;
    }
}
