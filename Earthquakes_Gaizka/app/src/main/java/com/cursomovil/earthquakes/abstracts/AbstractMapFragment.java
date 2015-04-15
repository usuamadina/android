package com.cursomovil.earthquakes.abstracts;

import android.app.Activity;
import android.os.Bundle;

import com.cursomovil.earthquakes.database.EarthQuakeDB;
import com.cursomovil.earthquakes.model.EarthQuake;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapLoadedCallback{

    protected Activity context;
    protected GoogleMap myMap;
    protected EarthQuakeDB db;

    protected MarkerOptions paintMarker(EarthQuake earthQuake) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DecimalFormat df = new DecimalFormat("0.00");

        LatLng point = new LatLng(
                earthQuake.getCoords().getLng(),
                earthQuake.getCoords().getLat()
        );
        String snippet = earthQuake.getPlace() + ", \n" +
                earthQuake.getCoords().getLng() + ", " +
                earthQuake.getCoords().getLat() + ", " +
                earthQuake.getCoords().getDepth();
        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .title("Magnitude: " + String.valueOf(df.format(earthQuake.getMagnitude())) + ", " +
                        "Date: " + sdf.format(earthQuake.getTime()))
                .snippet(snippet);

        myMap.addMarker(marker);

        return marker;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new EarthQuakeDB(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();

        this.getEarthquakesData();

        setupMapIfNeeded();

        myMap.setOnMapLoadedCallback(this);
        myMap.setOnInfoWindowClickListener(this);


    }

    private void setupMapIfNeeded() {
        if(myMap == null) {
            myMap = getMap();
            myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
    }

    @Override
    public void onMapLoaded() {
        this.paintEarthquakes();
    }

    abstract protected void getEarthquakesData();
    abstract protected void paintEarthquakes();
}
