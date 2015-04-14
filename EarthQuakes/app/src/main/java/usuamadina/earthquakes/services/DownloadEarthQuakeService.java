package usuamadina.earthquakes.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

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

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.activities.MainActivity;
import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.model.Coordinate;
import usuamadina.earthquakes.model.EarthQuake;

public class DownloadEarthQuakeService extends Service {

    private EarthQuakeDB earthQuakeDB;

    @Override
    public void onCreate() {
        super.onCreate();

        earthQuakeDB = new EarthQuakeDB(this);

        Log.d("EARTHQUAKE", "Servicio descargas iniciado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);

        Thread t = new Thread(new Runnable(){
            @Override
            public void run() {
                updateEarthQuakes(getString(R.string.earthquakes_url));
            }
        });

        t.start();

        return Service.START_STICKY;
    }


    private int updateEarthQuakes(String earthquakesFeed) {

        JSONObject json;
        int count = 0;

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
        sendNofication(count);
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


            earthQuakeDB.insertEarthQuake(earthQuake);

        } catch (JSONException e) {

            e.printStackTrace();


        }
    }

    private void sendNofication(int count){

        Intent intentToFire = new Intent(this, MainActivity.class);
        PendingIntent activityIntent = PendingIntent.getActivity(this,0,intentToFire,0);

        Notification.Builder builder = new Notification.Builder(DownloadEarthQuakeService.this);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(count + "new EarthQuakes")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSound(
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(activityIntent)
                .setLights(Color.MAGENTA,0,1)
                .setAutoCancel(true);

        Notification notification = builder.getNotification();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        int NOTIFICATION_REF = 1;
        notificationManager.notify(NOTIFICATION_REF,notification);

    }


    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }
}
