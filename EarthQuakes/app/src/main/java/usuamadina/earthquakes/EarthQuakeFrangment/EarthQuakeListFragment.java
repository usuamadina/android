package usuamadina.earthquakes.EarthQuakeFrangment;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import usuamadina.earthquakes.EarthQuake;
import usuamadina.earthquakes.R;

import usuamadina.earthquakes.EarthQuakeFrangment.dummy.DummyContent;
import usuamadina.earthquakes.Tasks.DownloadEarthQuakesTask;
import usuamadina.earthquakes.model.Coordinate;

//Hacemos que EarthQuakeListFragment sea del tipo DownloadEarthQuakesTask.AddEarthQuakeInterface mediante el implements
//pero necesitamos implementar el método addEarthQuakeInterface

public class EarthQuakeListFragment extends ListFragment implements DownloadEarthQuakesTask.AddEarthQuakeInterface {
    private static final String EARTHQUAKE = "EARTHQUAKE";

    private ArrayList<EarthQuake> earthQuakes;

    private ArrayAdapter<EarthQuake> aa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        earthQuakes = new ArrayList<>();


        DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this);
        task.execute(getString(R.string.earthquakes_url)); // crea un nuevo thread y ejecuta el método doInBackground dentro del thread

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        aa = new ArrayAdapter<EarthQuake>(getActivity(),android.R.layout.simple_list_item_1,earthQuakes);

        setListAdapter(aa);

        return layout;
    }


    @Override
    public void addEarthQuake(EarthQuake earthQuake) {
        earthQuakes.add(0, earthQuake);
        aa.notifyDataSetChanged();

    }
}


