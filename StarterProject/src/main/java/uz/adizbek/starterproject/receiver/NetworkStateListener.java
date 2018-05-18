package uz.adizbek.starterproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import uz.adizbek.starterproject.event.NetworkStateEvent;

public class NetworkStateListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = false;

        if (activeNetwork != null && activeNetwork.isConnected()) {
            isConnected = true;
        }

        EventBus.getDefault().post(new NetworkStateEvent(isConnected));
    }
}
