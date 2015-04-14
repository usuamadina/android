package usuamadina.earthquakes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.fragments.EarthQuakesListMapFragment;
import usuamadina.earthquakes.managers.EarthQuakeAlarmManager;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Display the fragment as the main content
        //Inyectamos el fragmento en el lugar donde debería de ir el Layout, android.R.id.content es el contendor
        //donde metemos el SettingsFragment
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

    getFragmentManager()
            .beginTransaction()
            .replace(android.R.id.content,new EarthQuakesListMapFragment.SettingsFragment())
    .commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        //En Java no se puede hacer un switch de un String, tenemos que hacerlo con if else

        String PREF_AUTO_UPDATE = getString(R.string.PREF_AUTO_UPDATE);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);
        String PREF_MIN_MAG = getString(R.string.PREF_MIN_MAG);

        //Aquí es donde activamos y desactivamos la alarma porque es donde sabemos si el usuario cambia las preferencias

        if(key.equals("PREF_AUTO_UPDATE")){
            //Start/stop auto refresh

            if( prefs.getBoolean(key,true)){
                long interval = Long.parseLong(prefs.getString(PREF_UPDATE_INTERVAL,"1"));
                EarthQuakeAlarmManager.activateAlarm(this, interval *60 *1000); // intervalo *60 *1000 para calcular los milisegundos
            }else {
                EarthQuakeAlarmManager.deactivateAlarm(this);
            }

        } else if (key.equals("PREF_UPDATE_INTERVAL")){
            //Change auto refresh interval

            long interval = Long.parseLong(prefs.getString(PREF_UPDATE_INTERVAL,"1"));

            EarthQuakeAlarmManager.updateAlarm(this, interval *60 *1000);

        }



    }




}
