package com.cursomovil.earthquakes;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cursomovil.earthquakes.fragments.EarthquakeListFragment;
import com.cursomovil.earthquakes.fragments.EarthquakesMapFragment;
import com.cursomovil.earthquakes.listeners.TabListener;
import com.cursomovil.earthquakes.managers.EarthquakeAlarmManager;
import com.cursomovil.earthquakes.tasks.DownloadEarthquakesTask;


public class MainActivity extends Activity implements DownloadEarthquakesTask.AddEarthquakeInterface{

    private final String EARTHQUAKE_PREFS = "EARTHQUAKE_PREFS";
    private final String LAUNCHED_BEFORE = "LAUNCHED_BEFORE";

    private  ActionBar actionBar;

    private	static final String	SELECTED_TAB_KEY = "SELECTED_TAB_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTabs();

        checkToSetAlarm();

    }

    private void addTabs() {

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabList = actionBar.newTab()
                .setText(R.string.tabList)
                .setTabListener(new TabListener<EarthquakeListFragment>(
                        this, R.id.frameContainer, EarthquakeListFragment.class));
        actionBar.addTab(tabList);

        ActionBar.Tab tabMap = actionBar.newTab()
                .setText(R.string.tabMap)
                .setTabListener(new TabListener<EarthquakesMapFragment>(
                        this, R.id.frameContainer, EarthquakesMapFragment.class));
        actionBar.addTab(tabMap);
    }

    private void checkToSetAlarm() {
        // Instanciamos una nueva tarea, y le pasamos como parámetro esta clase, la cual implementa el método definido
        // en la interfaz de la clase que instanciamos y el contexto
        // DownloadEarthquakesTask task = new DownloadEarthquakesTask(this, this);
        // task.execute(getString(R.string.earthquakes_url));

        // Ahora lo hacemos con un servicio
        // Intent download = new Intent(this, DownloadEarthquakesService.class);
        // startService(download);


        // Realmente sólo la primera vez que se ejecuta el mainActivity lanzaremos el servicio. Luego, según prefernecias
        SharedPreferences prefs = getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);
        if(!prefs.getBoolean(LAUNCHED_BEFORE, false)) {

            // Poner la alarma para que se lance el servicio
            long interval = getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            EarthquakeAlarmManager.setAlarm(this, interval);


            prefs.edit().putBoolean(LAUNCHED_BEFORE, true).apply();
        }

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

    @Override
    public void notifyTotal(int total) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_TAB_KEY, actionBar.getSelectedNavigationIndex());
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB_KEY));

    }
}
