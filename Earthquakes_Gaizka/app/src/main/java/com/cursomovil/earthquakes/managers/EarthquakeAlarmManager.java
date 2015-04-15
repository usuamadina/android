package com.cursomovil.earthquakes.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cursomovil.earthquakes.services.DownloadEarthquakesService;

/**
 * Created by cursomovil on 1/04/15.
 */
public class EarthquakeAlarmManager {

    public static void setAlarm(Context context, long interval)	{

        //	Get	a	reference	to	the	Alarm	Manager
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //	Set	the	alarm	to	wake	the	device	if	sleeping.
        int	alarmType = AlarmManager.RTC;

        //	Create	a	Pending	Intent	that	will	broadcast	and	action
        Intent intentToFire = new	Intent(context, DownloadEarthquakesService.class);
        PendingIntent alarmIntent =	PendingIntent.getService(context, 0, intentToFire, 0);

        //	Set	the	alarm
        alarmManager.setInexactRepeating(alarmType, interval, interval, alarmIntent);
        Log.d("ALARMMANAGER", "Alarma lanzada");
    }

    public static void cancelAlarm(Context context)	{

        //	Get	a	reference	to	the	Alarm	Manager
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);


        //	Create	a	Pending	Intent	that	will	broadcast	and	action
        Intent intentToFire = new	Intent(context, DownloadEarthquakesService.class);
        PendingIntent alarmIntent =	PendingIntent.getService(context, 0, intentToFire, 0);

        //	Set	the	alarm
        alarmManager.cancel(alarmIntent);
        Log.d("ALARMMANAGER", "Alarma cancelada");
    }

    public static void updateAlarm(Context context, long interval) {
        Log.d("ALARMMANAGER", "Alarma actualizada");
        setAlarm(context, interval);
    }

}
