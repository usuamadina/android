package usuamadina.earthquakes.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import usuamadina.earthquakes.R;



public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(this);

        //Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.userpreferences);


    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        //En Java no se puede hacer un switch de un String, tenemos que hacerlo con if else

        String PREF_AUTO_UPDATE = getString(R.string.PREF_AUTO_UPDATE);
        String PREF_UPDATE_INTERVAL = getString(R.string.PREF_UPDATE_INTERVAL);
        String PREF_MIN_MAG = getString(R.string.PREF_MIN_MAG);

        if(key.equals("PREF_AUTO_UPDATE")){
            //Start/stop auto refresh

        } else if (key.equals("PREF_UPDATE_INTERVAL")){
            //Change auto refresh interval

        }


    }
}
