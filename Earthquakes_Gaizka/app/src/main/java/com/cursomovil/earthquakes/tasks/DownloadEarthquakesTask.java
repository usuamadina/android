package com.cursomovil.earthquakes.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cursomovil.earthquakes.R;
import com.cursomovil.earthquakes.database.EarthQuakeDB;
import com.cursomovil.earthquakes.model.Coordinate;
import com.cursomovil.earthquakes.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by cursomovil on 25/03/15.
 */

/**
 *
 */
public class DownloadEarthquakesTask extends AsyncTask<String, EarthQuake, Integer> {


    private EarthQuakeDB earthQuakeDB;

    // definimos una interface para poder pasar datos a cualquier clase que nos llame desde fuera.
    // Como no sabemos que tipo de clase nos va a instanciar, definimos una interfaz en la que hay un método
    // que van a tener que implementar para recibir mis datos
    public interface AddEarthquakeInterface {
        public void notifyTotal(int total);
    }

    private static final String EARTHQUAKE = "EARTHQUAKE";

    // Definimos una var de tipo interfaz que hemos definido
    private AddEarthquakeInterface target;

    // creamos el constructor para pasarle los datos al que nos llama. Para ello,utilizamos el constructor,
    // así, cuando alguien desde fuera instancie esta clase, al pedirle la interfaz como parámetro,
    // le obligamos a implementar esa interfaz y el método que hemos definido en la interfaz, esto es,
    // cuando desde fuera instanciamos esta clase, en el constructor se deberá pasar como parámetro una
    // clase que implementa esta interfaz. La mayoría de las veces será this
    public DownloadEarthquakesTask(Context context, AddEarthquakeInterface target) {

        this.target = target;

        earthQuakeDB = new EarthQuakeDB(context);
    }

    @Override
    protected Integer doInBackground(String... urls) {
        int count = 0;

        if (urls.length > 0) {
            count = updateEarthquakes(urls[0]);
        }

        return new Integer(count);
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);

        // Este es el método que está sincronizado con la vista. Por ello, añadimos a la var de
        // tipo interfaz el earthquake, esto es, el dato que nos interesa pasar
        // a la clase que nos instancie desde fuera
        // target.addEarthquake(earthQuakes[0]);

    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);

        target.notifyTotal(count.intValue());

    }

    private int updateEarthquakes(String earthquakesFeed) {
        JSONObject json;
        int count = 0;

        // Nos conectamos y obtenemos un objeto JSON con toda la información
        try {
            URL url = new URL(earthquakesFeed);

            // Create a new HTTP URL connection
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(
                                httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");
                count = earthquakes.length();

                for (int i = earthquakes.length()-1; i >= 0; i--) {
                    // Procesamos, uno a uno, los earthquakes que vienen en el objeto JSON
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return count;
    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {

            // Obtenemos los datos del JSON recibido
            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");

            // Hemos creado en nuestro modelo un objeto coordenadas donde se recogen todas las coordenadas
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1), jsonCoords.getDouble(2));

            JSONObject properties = jsonObject.getJSONObject("properties");

            // Definimos nuevo objeto EarthQuake
            EarthQuake earthQuake = new EarthQuake();

            // Alimentamos sus propiedades con los datos obtenidos desde el JSON
            earthQuake.set_id(jsonObject.getString("id"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setTime(properties.getLong("time"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setUrl(properties.getString("url"));
            earthQuake.setCoords(coords);

            Log.d(EARTHQUAKE, earthQuake.toString());

            //eqTime = (int)earthQuakes[i].getTime();
            earthQuakeDB.addNewEarthquake(earthQuake);

            // este método lo implementa Android, nosoptros sólo lo llamamos con el earthquake obtenido,
            // y Android se encarga de sincronizar con la vista. Android nos manda al método onProgressUpdate
            // en el que nos llegará el earthquake pero ya en un método sincronizado con la vista
            // publishProgress(earthQuake);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
