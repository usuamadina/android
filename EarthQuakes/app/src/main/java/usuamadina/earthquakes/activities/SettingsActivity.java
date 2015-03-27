package usuamadina.earthquakes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.fragments.SettingsFragment;

/**
 * Created by cursomovil on 26/03/15.
 */
public class SettingsActivity extends PreferenceActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Display the fragment as the main content
        //Inyectamos el fragmento en el lugar donde debería de ir el Layout, android.R.id.content es el contendor
        //donde metemos el SettingsFragment


    getFragmentManager()
            .beginTransaction()
            .replace(android.R.id.content,new SettingsFragment())
    .commit();




    }



}
