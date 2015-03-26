package usuamadina.earthquakes.Tasks;

import android.os.AsyncTask;

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

import usuamadina.earthquakes.model.EarthQuake;
import usuamadina.earthquakes.model.Coordinate;

/**
 * Created by cursomovil on 25/03/15.
 */
public class DownloadEarthQuakesTask extends AsyncTask<String, EarthQuake, Integer> {


    // De esta forma obligamos a quien quiera usar el AsynTask a ejecutar el método AddEarthQuakeInterface exigiéndole
    //que nos pase algo de tipo AddEarthQuakeInterface y que implemente el método addEarthQuake

    private final String EARTHQUAKE = "EARTHQUAKE";
    //Una interfaz es un tipo de datos, así que nos vale para declarar una variable con ese tipo de datos
    private AddEarthQuakeInterface target;

    //No podemos poner un tipo de datos concreto porque no sabemos quien va a llamar a este método, pero si sabemos que va a
    //ejecutar este método
    public DownloadEarthQuakesTask(AddEarthQuakeInterface target) {
        this.target = target;




    }

    @Override
    protected Integer doInBackground(String... urls) {
        int count = 0;

        if (urls.length > 0) {
            count= updateEarthQuakes(urls[0]);
        }

        return count;
    }

    @Override
    protected void onProgressUpdate(EarthQuake... earthQuakes) {
        super.onProgressUpdate(earthQuakes);

        target.addEarthQuake(earthQuakes[0]);

    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);

        target.notifyTotal(count);



    }

    private int updateEarthQuakes(String earthquakesFeed) {

        JSONObject json;
        int count =0;

        try {
            URL url = new URL(earthquakesFeed);


            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader streamReader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                json = new JSONObject(responseStrBuilder.toString());
                JSONArray earthquakes = json.getJSONArray("features");
                count = earthquakes.length();

                for (int i = earthquakes.length() - 1; i >= 0; i--) {
                    processEarthQuakeTask(earthquakes.getJSONObject(i));
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void processEarthQuakeTask(JSONObject jsonObject) {
        try {


            JSONArray jsonCoords = jsonObject.getJSONObject("geometry").getJSONArray("coordinates");
            Coordinate coords = new Coordinate(jsonCoords.getDouble(0), jsonCoords.getDouble(1), jsonCoords.getDouble(2));


            JSONObject properties = (JSONObject) jsonObject.get("properties");
            EarthQuake earthQuake = new EarthQuake();
            earthQuake.setId(jsonObject.getString("id"));
            earthQuake.setPlace(properties.getString("place"));
            earthQuake.setMagnitude(properties.getDouble("mag"));
            earthQuake.setCoords(coords);
            earthQuake.setDate(properties.getLong("time"));
            earthQuake.setUrl(properties.getString("url"));

            // Aquí ya tenemos por lo menos un terremoto, llamamos a onProgressUpdate mediante la llamada publishProgress(earthQuake)

            //mediante el método de Android publishProgress() hace su proceso interno, llama al onProgressUpdate
            // y de esta forma nos comunicamos con la vista devolviéndole el terremoto a quien nos lo ha pedido, el target
            publishProgress(earthQuake);


        } catch (JSONException e) {

            e.printStackTrace();


        }
    }

    public interface AddEarthQuakeInterface {
        public void addEarthQuake(EarthQuake earthQuake);

        public void notifyTotal(int total);
    }


}
