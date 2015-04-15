package com.cursomovil.earthquakes.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cursomovil.earthquakes.R;
import com.cursomovil.earthquakes.database.EarthQuakeDB;
import com.cursomovil.earthquakes.services.DownloadEarthquakesService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML file
        addPreferencesFromResource(R.xml.userpreferences);

    }



    private	void setAlarm(int time)	{

        //	Get	a	reference	to	the	Alarm	Manager
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        //	Set	the	alarm	to	wake	the	device	if	sleeping.
        int	alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;

        //	Trigger	the	device	in	10	seconds.
        long timeOrLengthofWait	= time;

        //	Create	a	Pending	Intent	that	will	broadcast	and	action
        String ALARM_ACTION	= "ALARM_ACTION";
        Intent intentToFire = new	Intent(ALARM_ACTION);
        PendingIntent alarmIntent =	PendingIntent.getBroadcast(getActivity(), 0, intentToFire, 0);

        //	Set	the	alarm
        alarmManager.setRepeating(alarmType, timeOrLengthofWait, timeOrLengthofWait, alarmIntent);
        alarmManager.cancel(alarmIntent);
    }

    private	void explicitStart()	{

        //	Explicitly	start	My	Service
        Intent intent =	new	Intent(getActivity(), DownloadEarthquakesService.class);

        //	TODO Add	extras if required.
        // startService(intent);

    }
    private	void stopServices() {

        //	Stop a service	explicitly.
        // stopService(new Intent(getActivity(), DownloadEarthquakesService.class));
    }

}
