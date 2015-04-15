package com.cursomovil.earthquakes.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cursomovil.earthquakes.R;
import com.cursomovil.earthquakes.abstracts.AbstractMapFragment;
import com.cursomovil.earthquakes.model.EarthQuake;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthquakeDetailMapFragment extends AbstractMapFragment {

    private Intent detailIntent;

    private List<EarthQuake> earthquakesList;

    public EarthquakeDetailMapFragment() {
        // Required empty public constructor
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes) {
        this.earthquakesList = earthQuakes;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        earthquakesList = new ArrayList<>();

        // Obtenemos el Intent que ha abierto el activity de este fragmento
        detailIntent = getActivity().getIntent();
        return layout;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String url = earthquakesList.get(0).getUrl();
        Intent intentUrl = new Intent(Intent.ACTION_VIEW);
        intentUrl.setData(Uri.parse(url));
        startActivity(intentUrl);
    }


    /*
    @Override
    public void onResume() {

        getEarthquakesData();
        // Primero cargamos los datos, y luego le llamaos al onResume del padre. Éste se encarga de añadir el listener
        // onMapLoaded. Cuando se lance dicho evento, pintamos los datos. Es redundante, ya que estamos añadiendo un
        // nuevo listener cada vez que pasa por onResume, pero es lo que hay. Aunque el evento se haya lanzado ya
        // la primera vez que se haya cargado, al añadir otra vez el listener, android internamente hace algo así como:
        // "Ah! te pones a escuchar esto? Vale, te mando el evento"
        super.onResume();


    }
    */

    @Override
    protected void getEarthquakesData() {
        // Limpiar el array
        earthquakesList.clear();
        // Cargar datos desde la BBDD. SIEMPRE con addAll, NO asignacion
        earthquakesList.addAll(db.getEarthQuakesById(detailIntent.getStringExtra(EarthquakeListFragment.EQ_ITEM)));

    }

    @Override
    protected void paintEarthquakes() {


        Log.d("MARKER", "solo 1: " + earthquakesList.size());

        EarthQuake earthQuake = earthquakesList.get(0);

        MarkerOptions marker = paintMarker(earthQuake);

        // Definimos nueva posición de cámara en el punto del terremoto
        CameraPosition camPos = new CameraPosition.Builder().target(marker.getPosition()).zoom(3).build();

        //le damos a la camara la nueva posición
        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(camPos);

        // le asignamos al mapa la cámara
        myMap.moveCamera(camUpd);

    }
}
