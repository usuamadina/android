package usuamadina.earthquakes.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.fragments.EarthQuakeMapFragment;
import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.model.EarthQuake;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String id;
    private Double longitude;
    private Double latitude;

    private EarthQuake earthQuake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //recoger el id que me estan pasando por la intent
        Intent detailIntent = getIntent();

        id = detailIntent.getStringExtra(EarthQuakeMapFragment.EarthQuakeListFragment.EARTHQUAKE_ID);

      // longitude = Double.valueOf(detailIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE_LONG));
      // latitude = Double.valueOf(detailIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE_LAT));

        //coger los datos del terremoto de la BD

        EarthQuakeDB earthQuakeDB = new EarthQuakeDB(this);
        earthQuake = earthQuakeDB.getById(id);


        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        String S;
        S = String.valueOf((earthQuake.getMagnitude()));

        //usar los datos del terremoto para rellenar el marker

        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(earthQuake.getCoords().getLng(),earthQuake.getCoords().getLat())).title(S);

        mMap.addMarker(marker);

        LatLng punto = new LatLng(earthQuake.getCoords().getLng(),earthQuake.getCoords().getLat());

        //centrar el mapa en ese punto

        CameraPosition cameraPosition = new CameraPosition.Builder().target(punto)
                .zoom(5)
                .build();
        CameraUpdate camUpd =CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(camUpd);



    }
}
