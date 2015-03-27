package usuamadina.earthquakes.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import usuamadina.earthquakes.activities.detail_activity;
import usuamadina.earthquakes.model.EarthQuake;
import usuamadina.earthquakes.EarthQuakeAdapter.EarthQuakeAdapter;
import usuamadina.earthquakes.R;

import usuamadina.earthquakes.Tasks.DownloadEarthQuakesTask;

//Hacemos que EarthQuakeListFragment sea del tipo DownloadEarthQuakesTask.AddEarthQuakeInterface mediante el implements
//pero necesitamos implementar el método addEarthQuakeInterface

public class EarthQuakeListFragment extends ListFragment implements DownloadEarthQuakesTask.AddEarthQuakeInterface {
    public static final String EARTHQUAKE_ID = "ID";
    private static final String EARTHQUAKE = "EARTHQUAKE";
    private ArrayList<EarthQuake> earthQuakes;

    private ArrayAdapter<EarthQuake> aa;

    private SharedPreferences prefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();

        // Obtenemos las preferencias

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_detail, earthQuakes);

        setListAdapter(aa);

        return layout;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void notifyTotal(int total) {
        String msg = getString(R.string.num_earthquakes, total);
        Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        t.show();
    }

    //A través del método onListItemClick seleccionamos el item en pantalla que queremos ver y nos lleva a la vista en detalle
    //creando un Intent con la clase detalle, pasándole el o los elementos que queremos mostrar, en este caso, solo el ID del
    //terremoto por medio del putExtra
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        EarthQuake earthQuake = earthQuakes.get(position);


        Intent detailIntent = new Intent(getActivity(), detail_activity.class);
        detailIntent.putExtra(EARTHQUAKE_ID, earthQuake.getId());
        startActivity(detailIntent);


    }



}


