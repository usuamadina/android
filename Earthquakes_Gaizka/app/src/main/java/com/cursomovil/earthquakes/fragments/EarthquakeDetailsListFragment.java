package com.cursomovil.earthquakes.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cursomovil.earthquakes.R;

import com.cursomovil.earthquakes.adapters.EarthquakeDetailAdapter;
import com.cursomovil.earthquakes.database.EarthQuakeDB;
import com.cursomovil.earthquakes.model.EarthQuake;

import java.util.ArrayList;

public class EarthquakeDetailsListFragment extends ListFragment {

    private ArrayList<EarthQuake> earthquakesList;
    private ArrayAdapter<EarthQuake> aa;

    public EarthquakeDetailsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthquakesList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthquakeDetailAdapter(getActivity(), R.layout.earthquake_details_layout, earthquakesList);
        // Asignar el adapter a la vista
        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    public void paintDetails(ArrayList<EarthQuake> eqList) {
        // Limpiar el array
        earthquakesList.clear();
        // Cargar datos desde la BBDD. SIEMPRE con addAll, NO asignacion
        earthquakesList.addAll(eqList);

        // Asignar el adapter a la vista
        aa.notifyDataSetChanged();
    }


}
