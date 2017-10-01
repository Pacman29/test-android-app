package com.example.pacman29.testalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by pacman29 on 01.10.17.
 */

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("in Receiver","kek");

        String command = intent.getExtras().getString("extra");

        Log.e("key from extra: ",command);

        int songNumber = intent.getExtras().getInt("song_number");

        Log.e("song number", String.valueOf(songNumber));

        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        serviceIntent.putExtra("extra",command);
        serviceIntent.putExtra("song_number", songNumber);

        //start service
        context.startService(serviceIntent);
    }
}
