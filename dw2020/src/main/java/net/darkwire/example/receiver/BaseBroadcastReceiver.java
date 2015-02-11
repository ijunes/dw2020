package net.darkwire.example.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.darkwire.example.service.BaseBroadcastIntentService;

/**
 * Created by ijunes on 02/05/2015.
 */
public class BaseBroadcastReceiver extends BroadcastReceiver {
    private final static String TAG = BaseBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive was triggered");

        Intent service = new Intent(context, BaseBroadcastIntentService.class);
        service.putExtra("ACTION", intent.getStringExtra("PERFORM"));

        context.startService(service);


    }
}
