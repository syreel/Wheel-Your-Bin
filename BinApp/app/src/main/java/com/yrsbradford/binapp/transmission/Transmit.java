package com.yrsbradford.binapp.transmission;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.NotificationCompat;

import com.yrsbradford.binapp.Channel;
import com.yrsbradford.binapp.MainActivity;
import com.yrsbradford.binapp.R;
import com.yrsbradford.binapp.SendMessage;
import com.yrsbradford.binapp.WebUtils;
import com.yrsbradford.binapp.Website;

import org.json.JSONArray;
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
    public void onUpdate(final String sessionToken){

        (new Thread(){
            public void run(){

                if(changed) {
                    sendData(longitude, latitude, sessionToken);
                    changed = false;
                    MainActivity.getMain().log(Channel.GPS, "Location data sent");
                }else{
                    MainActivity.getMain().log(Channel.GPS, "Location data not sent");
                }

                String response = WebUtils.getTextFromPage(WebUtils.HOST+"/getData.php?&token="+MainActivity.getMain().sessionToken+"&username="+MainActivity.getMain().username);

                if(response != null && response.length() > 0){

                    try {

                        JSONObject object = new JSONObject(response);
                        checkAlerts(object.getJSONArray("alerts"));
                        checkDistances(object.getJSONObject("distances"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    private void checkAlerts(JSONArray array) throws JSONException {

        for(int i = 0; i < array.length(); i++){

            JSONObject alert = array.getJSONObject(i);

            Intent resultIntent = new Intent(MainActivity.getMain(), SendMessage.class);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(MainActivity.getMain())
                            .setSmallIcon(R.drawable.logoclear)
                            .setColor(0xFF5F005F)
                            .setContentTitle(alert.getString("name"))
                            .setContentText(alert.getString("value"))
                            .setPriority(Notification.PRIORITY_MAX);

            /*
            Intent yesReceive = new Intent();
            yesReceive.setAction("MESSAGE");
            PendingIntent pendingIntentYes = PendingIntent.getBroadcast(MainActivity.getMain(), 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.addAction(R.drawable.logoclear, "Message Neighbour", pendingIntentYes);
            */

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            MainActivity.getMain(),
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotifyMgr =
                    (NotificationManager) MainActivity.getMain().getSystemService(MainActivity.getMain().NOTIFICATION_SERVICE);
            mNotifyMgr.notify(i, mBuilder.build());
        }
    }

    private int lastDistance;
    private boolean wasTaken;

    private void checkDistances(JSONObject distances) throws JSONException {
        if(distances.getBoolean("status")){
            if(distances.has("distance") && !distances.isNull("distance")){

                int distance = distances.getInt("distance");

                if(lastDistance != distance && distance > 200 && !wasTaken) {

                    Intent resultIntent = new Intent(MainActivity.getMain(), Website.class);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.getMain())
                                    .setSmallIcon(R.drawable.logoclear)
                                    .setColor(0xFFFF0000)
                                    .setContentTitle("Bin Taken")
                                    .setContentText("Your bin has been removed")
                                    .setPriority(Notification.PRIORITY_MAX);

                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    MainActivity.getMain(),
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                    mBuilder.setContentIntent(resultPendingIntent);

                    NotificationManager mNotifyMgr =
                            (NotificationManager) MainActivity.getMain().getSystemService(MainActivity.getMain().NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(100, mBuilder.build());

                    lastDistance = distance;
                    wasTaken = true;

                }else if(wasTaken && distance < 200){

                    Intent resultIntent = new Intent(MainActivity.getMain(), Website.class);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.getMain())
                                    .setSmallIcon(R.drawable.logoclear)
                                    .setColor(0xFF00FF00)
                                    .setContentTitle("Bin Returned")
                                    .setContentText("Your bin is back at home!")
                                    .setPriority(Notification.PRIORITY_MAX);

                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    MainActivity.getMain(),
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                    mBuilder.setContentIntent(resultPendingIntent);

                    NotificationManager mNotifyMgr =
                            (NotificationManager) MainActivity.getMain().getSystemService(MainActivity.getMain().NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(100, mBuilder.build());

                    wasTaken = false;
                }
            }
        }
    }

    /**
     * Sets the data to be used by the transmission to the server
     * @param longitude geographical longitude position
     * @param latitude geographical latitude position
     */
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
