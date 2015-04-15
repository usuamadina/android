package com.cursomovil.earthquakes.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cursomovil.earthquakes.DetailActivity;
import com.cursomovil.earthquakes.R;

import com.cursomovil.earthquakes.adapters.EarthquakeAdapter;
import com.cursomovil.earthquakes.providers.EarthQuakeDB;
import com.cursomovil.earthquakes.model.EarthQuake;
import com.cursomovil.earthquakes.services.DownloadEarthquakesService;
import com.cursomovil.earthquakes.tasks.DownloadEarthquakesTask;

import java.util.ArrayList;


public class EarthquakeListFragment extends ListFragment implements DownloadEarthquakesTask.AddEarthquakeInterface {

    private static final String EARTHQUAKE = "EARTHQUAKE";
    // Definimos public para que se pueda acceder desde fuera, static para que sea a nivel de clase,
    // y no a nivel de instancia y final para que sea una constante
    public static final String EQ_ITEM = "EQ_ITEM";

    private SharedPreferences prefs;

    private ArrayList<EarthQuake> earthquakesList;
    private ArrayAdapter<EarthQuake> aa;
    private EarthQuakeDB db;
    private int mag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthquakesList = new ArrayList<>();
        // Instanciamos la BD
        db = new EarthQuakeDB(getActivity());

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Añadimos elemento en menú. Refrescar
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout =  super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthquakeAdapter(getActivity(), R.layout.earthquakes_list_layout, earthquakesList);
        // Asignar el adapter a la vista
        setListAdapter(aa);

        return layout;

    }


    @Override
    public void notifyTotal(int total) {

        // Al getString le pasamos el valor que queremos que saque por pantalla en ese string
        String msg = getString(R.string.num_earthquakes,total);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // En position está el índice en el ArrayList del elemento en el que se ha hecho el click
        // Obtenemos el objeto desde el ArrayList
        EarthQuake eq = earthquakesList.get(position);

        // Creamos nuevo Intent, le tenemos que dar el contexto(el activity en el que estamos) y el activity que queremos lanzar
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        //Intent detailIntent = new Intent(getActivity(), MapActivity.class);

        // Alimentamos el intent con los datos necesarios para el nuevo activity(hace de Bundle)
        detailIntent.putExtra(EQ_ITEM, eq.get_id());
        startActivity(detailIntent);
    }

    @Override
    public void onResume() {
        super.onResume();

        mag = Integer.parseInt(prefs.getString(getString(R.string.pref_key_min_magnitude),"0"));

        // Limpiar el array
        earthquakesList.clear();
        // Cargar datos desde la BBDD. SIEMPRE con addAll, NO asignacion
        earthquakesList.addAll(db.getEarthQuakesByMagnitude(mag));

        // Asignar el adapter a la vista
        aa.notifyDataSetChanged();
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
