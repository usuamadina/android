package usuamadina.earthquakes.fragments;

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

import java.util.ArrayList;
import java.util.List;

import usuamadina.earthquakes.activities.DetailActivity;
import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.model.EarthQuake;
import usuamadina.earthquakes.EarthQuakeAdapter.EarthQuakeAdapter;
import usuamadina.earthquakes.R;

import usuamadina.earthquakes.Tasks.DownloadEarthQuakesTask;
import usuamadina.earthquakes.services.DownloadEarthQuakeService;

//Hacemos que EarthQuakeListFragment sea del tipo DownloadEarthQuakesTask.AddEarthQuakeInterface mediante el implements
//pero necesitamos implementar el método addEarthQuakeInterface

public class EarthQuakeListFragment extends ListFragment implements DownloadEarthQuakesTask.AddEarthQuakeInterface {

    public static final String EARTHQUAKE_ID = "ID";
    public static final String EARTHQUAKE_LONG = "LONG";
    public static final String EARTHQUAKE_LAT = "LAT";
    private static final String EARTHQUAKE = "EARTHQUAKE";
    // private ArrayList<EarthQuake> earthQuakes;

    private ArrayAdapter<EarthQuake> aa;

    private SharedPreferences prefs;
    private EarthQuakeDB earthQuakeDB;

    private List<EarthQuake> earthQuakes;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());



        earthQuakeDB = new EarthQuakeDB(getActivity());
        earthQuakes = new ArrayList<>();

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new EarthQuakeAdapter(getActivity(), R.layout.earthquake_list_row, earthQuakes);

        setListAdapter(aa);

        return layout;
    }



    @Override
    public void onResume() {
        super.onResume();

        // oBTENER LA magnitud minima

        int minMag = Integer.parseInt(prefs.getString(getString(R.string.PREF_MIN_MAG), "0"));

        earthQuakes.clear();
        earthQuakes.addAll(earthQuakeDB.getAllByMagnitude(minMag));

        aa.notifyDataSetChanged();

        // hacer la query
        // Limpiar el array
        // Añadir los terremotos al array
        // Notificar al adaptador
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


        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);

        detailIntent.putExtra(EARTHQUAKE_ID,earthQuake.getId());

        startActivity(detailIntent);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_refresh,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_refresh){
            Intent download = new Intent(getActivity(), DownloadEarthQuakeService.class);
            getActivity().startService(download);
        }

        return super.onOptionsItemSelected(item);
    }
}