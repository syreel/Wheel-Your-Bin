package com.yrsbradford.binapp.transmission;

import com.yrsbradford.binapp.WebUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 10askinsw on 31/07/2015.
 */
public class RegisterEvent {

    private String username, password, confirmPassword;
    private JSONObject response;

    public RegisterEvent(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void register(){

        String text = WebUtils.getTextFromPage(WebUtils.HOST + "/register.php?&username="+username
                +"&password="+password
                +"&confirmPassword="+confirmPassword);

        try {
            response = new JSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(response);
        System.out.println(this.isValid());
        System.out.println(this.getMessage());
    }

    public boolean isValid(){

        try {
            return response != null && response.getInt("status") == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getMessage(){

        try {
            return response != null ? response.getString("message") : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean connectionFailed(){
        return response == null;
    }
}
