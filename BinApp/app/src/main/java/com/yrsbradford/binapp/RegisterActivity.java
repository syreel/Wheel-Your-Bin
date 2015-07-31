package com.yrsbradford.binapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yrsbradford.binapp.transmission.RegisterEvent;


public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Stops the keyboard moving the components
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final TextView responseDisplay = (TextView) findViewById(R.id.responseDisplay);
        final Button registerButton = (Button) findViewById(R.id.button4);

        final EditText emailField = (EditText) findViewById(R.id.email);
        final EditText usernameField = (EditText) findViewById(R.id.username);
        final EditText passwordField = (EditText) findViewById(R.id.password);
        final EditText confirmPasswordField = (EditText) findViewById(R.id.confirmPassword);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                (new Thread(){
                    public void run(){

                        final RegisterEvent event = new RegisterEvent(emailField.getText().toString(), usernameField.getText().toString(), passwordField.getText().toString(), confirmPasswordField.getText().toString());

                        event.register();

                        if(event.worked()){

                            MainActivity.getMain().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    MainActivity.getMain().redirect(LoginActivity.class);
                                }

                            });

                        }else if(!event.connectionFailed()){

                            responseDisplay.post(new Runnable() {
                                public void run() {
                                    responseDisplay.setText(event.getMessage());
                                }
                            });

                        }else{

                            responseDisplay.post(new Runnable() {
                                public void run() {
                                    responseDisplay.setText("Connection failed!");
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
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
