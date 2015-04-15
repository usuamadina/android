package com.cursomovil.earthquakes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import com.cursomovil.earthquakes.fragments.SettingsFragment;
import com.cursomovil.earthquakes.managers.EarthquakeAlarmManager;
import com.cursomovil.earthquakes.services.DownloadEarthquakesService;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Register this OnSharedPreferenceChangeListener
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        // Display the fragment as the main content.
        getFragmentManager()
            .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
            .commit();


    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

        //Log.d("SPC", getString(R.string.pref_key_min_magnitude) + " key: " + key);

        if(key.equals(getString(R.string.pref_key_auto_refresh))) {

            // Start/Stop auto refresh
            boolean newValue = prefs.getBoolean(key, true);
            if (newValue) {
                Log.d("PREF_CHANGE", "Autorefresco activado!!");
                long newInterval = Long.parseLong(prefs.getString(getString(R.string.pref_key_auto_refresh_frec), "5"));
                EarthquakeAlarmManager.setAlarm(this, newInterval * 60 * 1000);
            } else {
                Log.d("PREF_CHANGE", "Autorefresco desactivado!!");
                EarthquakeAlarmManager.cancelAlarm(this);
            }

        } else if(key.equals(getString(R.string.pref_key_auto_refresh_frec))) {

            // Change auto refresh interval
            long newInterval = Long.parseLong(prefs.getString(key, "5"));
            Log.d("PREF_CHANGE", "Frecuencia de actualización: " + newInterval);
            EarthquakeAlarmManager.updateAlarm(this, newInterval * 60 * 1000);

        }

    }


}
