package usuamadina.earthquakes.activities;




import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;



import android.os.Bundle;


import android.view.Menu;
import android.view.MenuItem;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.Tasks.DownloadEarthQuakesTask;
import usuamadina.earthquakes.fragments.EarthQuakeListFragment;
import usuamadina.earthquakes.fragments.EarthQuakesMapFragment;
import usuamadina.earthquakes.managers.EarthQuakeAlarmManager;
import usuamadina.earthquakes.services.DownloadEarthQuakeService;


public class MainActivity extends Activity implements DownloadEarthQuakesTask.AddEarthQuakeInterface {

    private final int PREFS_ACTIVITY = 1;
    private final String EARTHQUAKE_PREFS = "EARTHQUAKES_PREFS";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkToSetAlarm();

       ActionBar actionBar = getActionBar();
       actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabOne = actionBar.newTab();
        tabOne.setText("List")
                .setTabListener(

                        new TabListener<EarthQuakeListFragment>
                                (this, R.id.fLayout, EarthQuakeListFragment.class));

        actionBar.addTab(tabOne);

        ActionBar.Tab tabTwo = actionBar.newTab();
        tabTwo.setText("Map")
                .setTabListener( new TabListener<EarthQuakesMapFragment>(this,R.id.fLayout,EarthQuakesMapFragment.class));

        actionBar.addTab(tabTwo);





        // downloadEarthQuakes();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            startActivityForResult(prefsIntent,PREFS_ACTIVITY);

            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    /*

    private void downloadEarthQuakes() {

       /* DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this,this); // De esta forma le pasamos el contexto
        task.execute(getString(R.string.earthquakes_url)); //crea un nuevo thread y ejecuta el método doInBackground dentro del thread

        Intent download = new Intent(this, DownloadEarthQuakeService.class);
        startService(download);

    }*/

    private void checkToSetAlarm(){

        String KEY ="LAUNCHED_BEFORE";

        SharedPreferences prefs = getSharedPreferences(EARTHQUAKE_PREFS,MODE_PRIVATE);
        if (!prefs.getBoolean(KEY,false)){
            long interval = getResources().getInteger(R.integer.default_interval);
            EarthQuakeAlarmManager.activateAlarm(this,interval);

            prefs.edit().putBoolean("LAUNCHED_BEFORE",true).apply();
        }


    }

    @Override
    public void notifyTotal(int total) {

    }
}
