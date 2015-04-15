package com.cursomovil.earthquakes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cursomovil.earthquakes.database.EarthQuakeDB;
import com.cursomovil.earthquakes.fragments.EarthquakeDetailMapFragment;
import com.cursomovil.earthquakes.fragments.EarthquakeDetailsListFragment;
import com.cursomovil.earthquakes.fragments.EarthquakeListFragment;
import com.cursomovil.earthquakes.fragments.EarthquakesMapFragment;
import com.cursomovil.earthquakes.model.EarthQuake;

import java.util.ArrayList;


public class DetailActivity extends Activity {

    private EarthQuake eq;

    private View myMap;

    private TextView lblId;
    private ArrayList<EarthQuake> earthquakesList;
    private ArrayAdapter<EarthQuake> aa;
    private EarthQuakeDB db;
    private Intent detailIntent;
    private EarthquakeDetailMapFragment mapFragment;
    private EarthquakeDetailsListFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos el Intent que ha abierto el activity de este fragmento
        detailIntent = getIntent();
        setContentView(R.layout.activity_detail);
        earthquakesList = new ArrayList<>();
        // Instanciamos la BD
        db = new EarthQuakeDB(this);
        earthquakesList = db.getEarthQuakesById(detailIntent.getStringExtra(EarthquakeListFragment.EQ_ITEM));

        mapFragment = (EarthquakeDetailMapFragment) getFragmentManager().findFragmentById(R.id.map);
        detailFragment = (EarthquakeDetailsListFragment) getFragmentManager().findFragmentById(R.id.fragment);


        mapFragment.setEarthQuakes(earthquakesList);
        detailFragment.paintDetails(earthquakesList);
        //mapFragment.paintDetails(earthquakesList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent prefsIntent = new Intent(this, SettingsActivity.class);
            startActivity(prefsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
