package com.yrsbradford.binapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yrsbradford.binapp.transmission.LoginEvent;


public class LoginActivity extends ActionBarActivity {

    private MainActivity main;

    public LoginActivity(){
        this.main = MainActivity.getMain();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView responseDisplay = (TextView) findViewById(R.id.textView3);
        final Button loginButton = (Button) findViewById(R.id.button);

        final EditText usernameField = (EditText) findViewById(R.id.editText);
        final EditText passwordField = (EditText) findViewById(R.id.editText2);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                responseDisplay.setText("Connecting...");

                (new Thread() {
                    public void run() {

                        LoginEvent event = new LoginEvent(usernameField.getText().toString(), passwordField.getText().toString());

                        event.login();

                        if (event.isValid()) {
                            main.redirect(Website.class);
                            main.startSendingData();
                        } else {
                            responseDisplay.post(new Runnable() {
                                                     public void run() {
                                                         responseDisplay.setText("Invalid username or password!");
                                                     }
                                                 });
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
