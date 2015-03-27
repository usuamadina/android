package usuamadina.earthquakes.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.Tasks.DownloadEarthQuakesTask;


public class MainActivity extends ActionBarActivity implements DownloadEarthQuakesTask.AddEarthQuakeInterface{

    private final int PREFS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadEarthQuakes();

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

    private void downloadEarthQuakes() {

        DownloadEarthQuakesTask task = new DownloadEarthQuakesTask(this,this); // De esta forma le pasamos el contexto
        task.execute(getString(R.string.earthquakes_url)); //crea un nuevo thread y ejecuta el método doInBackground dentro del thread

    }

    @Override
    public void notifyTotal(int total) {

    }
}
