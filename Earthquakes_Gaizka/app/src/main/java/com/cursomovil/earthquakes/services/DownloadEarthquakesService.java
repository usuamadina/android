package com.cursomovil.earthquakes.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.util.Log;

import com.cursomovil.earthquakes.MainActivity;
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

public class DownloadEarthquakesService extends Service {

    private EarthQuakeDB db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = new EarthQuakeDB(this);
        Log.d("ALARMMANAGER", "Servicio de descarga lanzado");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                updateEarthquakes(getString(R.string.earthquakes_url));

            }
        });
        t.start();
        Log.d("ALARMMANAGER", "Servicio de descarga lanzado");
        return Service.START_STICKY;
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

            sendNotification(count);

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

            //Log.d(EARTHQUAKE, earthQuake.toString());

            //eqTime = (int)earthQuakes[i].getTime();
            db.addNewEarthquake(earthQuake);

            // este método lo implementa Android, nosoptros sólo lo llamamos con el earthquake obtenido,
            // y Android se encarga de sincronizar con la vista. Android nos manda al método onProgressUpdate
            // en el que nos llegará el earthquake pero ya en un método sincronizado con la vista
            // publishProgress(earthQuake);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(int count) {

        // Este método lanza una notificación a la barra de notificaciones de nuestro dispositivo
        // Creamos un Intent,donde definimos a dónde queremos dirigir el usuario con esa notificación
        Intent intentToFire = new Intent(this, MainActivity.class);

        // El activity definido lo asociamos a un pendingintent, esto es, se queda pendiente de algo.
        // Ese algo es, en este caso, el click del usuario en la notificación. Esto desncadenará en el
        // lanzamiento del intent
        PendingIntent activityIntent = PendingIntent.getActivity(this, 0, intentToFire, 0);

        Notification.Builder builder = new Notification.Builder(DownloadEarthquakesService.this);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.count_earthquakes, count))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSound(
                        RingtoneManager.getDefaultUri(
                                RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(activityIntent)
                .setAutoCancel(true);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int NOTIFICATION_REF = 1;
        notificationManager.notify(NOTIFICATION_REF, notification);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
