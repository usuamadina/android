package com.cursomovil.earthquakes.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cursomovil.earthquakes.R;
import com.cursomovil.earthquakes.abstracts.AbstractMapFragment;
import com.cursomovil.earthquakes.model.EarthQuake;
import com.cursomovil.earthquakes.services.DownloadEarthquakesService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthquakesMapFragment extends AbstractMapFragment {

    private List<EarthQuake> earthquakesList;


    private SharedPreferences prefs;
    private int mag;


    public EarthquakesMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Añadimos elemento en menú. Refrescar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = super.onCreateView(inflater, container, savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        earthquakesList = new ArrayList<>();

        return layout;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        String url = earthquakesList.get(0).getUrl();
        Intent intentUrl = new Intent(Intent.ACTION_VIEW);
        intentUrl.setData(Uri.parse(url));
        startActivity(intentUrl);
    }


    @Override
    protected void getEarthquakesData() {
        mag = Integer.parseInt(prefs.getString(getString(R.string.pref_key_min_magnitude),"0"));

        // Limpiar el array
        earthquakesList.clear();
        // Cargar datos desde la BBDD. SIEMPRE con addAll, NO asignacion
        earthquakesList.addAll(db.getEarthQuakesByMagnitude(mag));
    }

    @Override
    protected void paintEarthquakes() {

        // Haya o no terremotos, tenemos que limpiar los que había
        myMap.clear();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();


        for (EarthQuake earthQuake: earthquakesList) {
            MarkerOptions marker = paintMarker(earthQuake);
            builder.include(marker.getPosition());
        }

        if (earthquakesList.size() > 0 ) {

            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);

            myMap.animateCamera(cu);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.refresh_btn) {
            Intent download = new Intent(getActivity(), DownloadEarthquakesService.class);
            getActivity().startService(download);
        }

        return super.onOptionsItemSelected(item);
    }



}
