package usuamadina.intents.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class ConectionReceiver extends BroadcastReceiver {

    private final String RECEIVER = "RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "ConectionReceiver onReceive");
        Log.d(RECEIVER, "ACTION: " + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            // DO SOMETHING  Log.d(RECEIVER, "ACTION: " + intent.getAction());
        } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

        }

    }

}
