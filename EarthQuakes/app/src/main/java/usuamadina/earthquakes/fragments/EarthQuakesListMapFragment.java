package usuamadina.earthquakes.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.fragments.abstracts.AbstractMapFragment;
import usuamadina.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 13/04/15.
 */
public class EarthQuakesListMapFragment extends AbstractMapFragment {

    EarthQuakeDB earthQuakeDB;
    private List<EarthQuake> earthQuakes;


    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


    }

    @Override
    protected void getData() {
        int minMag = Integer.parseInt(prefs.getString("PREF_MIN_MAG","0"));
        earthQuakes = earthQuakeDB.getAllByMagnitude(minMag);

    }

    @Override
    protected void paintMap() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (EarthQuake earthQuake: earthQuakes) {

            MarkerOptions marker = createMarker(earthQuake);
            getMap().addMarker(marker);
            builder.include(marker.getPosition());
        }

            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,10);

           getMap().animateCamera(cu);

            //usar los datos del terremoto para rellenar el marker
        }


    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            //Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.userpreferences);


        }



    }
}

