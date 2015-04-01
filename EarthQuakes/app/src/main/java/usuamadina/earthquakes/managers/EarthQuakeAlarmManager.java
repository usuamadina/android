package usuamadina.earthquakes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import usuamadina.earthquakes.services.DownloadEarthQuakeService;

/**
 * Created by cursomovil on 1/04/15.
 */
public class EarthQuakeAlarmManager {


    public static void activateAlarm(Context context, long interval){
        AlarmManager alarmManager = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE); //el getSystemService nos lo da el contexto

        int alarmType = AlarmManager.RTC;

        Intent intentToUpdate = new Intent(context, DownloadEarthQuakeService.class);

        //Creamos un pending intent xq es un todavía no se va a ejecutar, es un Intent pendiente

        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intentToUpdate, 0);

        alarmManager.set(alarmType, interval, alarmIntent);

        Log.d("Earthquake", "Alarma activada");

    }

    public static void deactivateAlarm(Context context){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intentToUpdate = new Intent(context, DownloadEarthQuakeService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intentToUpdate, 0);

        alarmManager.cancel(alarmIntent);




    }

    public static void updateAlarm(Context context, long interval){
        activateAlarm(context, interval);

    }

}
