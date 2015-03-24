package usuamadina.intents.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {

    final String RECEIVER = "RECEIVER";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "ConectionReceiver onReceive");
        Log.d(RECEIVER, "ACTION: " + intent.getAction());
    }
}
