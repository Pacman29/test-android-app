package com.example.pacman29.testalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by pacman29 on 01.10.17.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("Rington service","Receiver start id "+startId+":"+intent);

        String command = intent.getExtras().getString("extra");
        int songNumber = intent.getExtras().getInt("song_number");

        Log.e("Ring extra: ",command);


        assert command != null;
        switch (command) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intentMainActivity, 0);

        Notification notificationPopup = new Notification.Builder(this)
                .setContentTitle("alarm is going off!")
                .setContentText("Click me!")
                .setContentIntent(pendingIntentMainActivity)
                .setSmallIcon(R.drawable.ic_action_call)
                .setAutoCancel(true)
                .build();

        //btn alarm on and start to play music
        if(!this.isRunning && startId == 1) {
            Log.e("no music", "you want start");

            switch (songNumber){
                case 0: mediaPlayer = MediaPlayer.create(this, R.raw._7b);
                    break;
                case 1: mediaPlayer = MediaPlayer.create(this, R.raw.bi2);
            }
            Log.e("no music", "you want start");
            mediaPlayer.start();
            Log.e("no music", "you want start");
            notificationManager.notify(0,notificationPopup);
            Log.e("no music", "you want start");
            this.isRunning = true;
            this.startId = 0;
        }
        // stop music if btn alarm off
        else if (this.isRunning && startId == 0) {
            Log.e("music", "you want end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;

        }
        // do nothing
        else if(!this.isRunning && startId == 0) {
            Log.e("no music", "you want end");

            this.isRunning = false;
            this.startId = 0;
        }
        // do nothing
        else if (this.isRunning && startId == 1) {
            Log.e("music", "you want start");

            this.isRunning = true;
            this.startId = 1;
        } else {
            Log.e("else", "wat?");
        }



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("on destroy called", "lol");

        super.onDestroy();
        this.isRunning = false;

    }
}
