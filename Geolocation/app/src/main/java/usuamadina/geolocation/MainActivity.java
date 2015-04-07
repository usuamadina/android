package usuamadina.geolocation;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;

import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import usuamadina.geolocation.listeners.LocationListener;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private Boolean conectado;
    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblAltitude;
    private TextView lblSpeed;

    private String provider;
    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLatitude = (TextView) findViewById(R.id.lblLatitude);
        lblLongitude = (TextView) findViewById(R.id.lblLongitude);
        lblAltitude = (TextView) findViewById(R.id.lblAltitude);
        lblSpeed = (TextView) findViewById(R.id.lblSpeed);

        getLocationProvider();
        listenLocationChanges();


    }


    private void getLocationProvider() {


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true);
        criteria.setAltitudeRequired(true);

        provider = locationManager.getBestProvider(criteria, true);



        Log.d("GEO", provider);


    }


    private void listenLocationChanges() {

        int t = 5000;
        int distance = 5;

        LocationListener listener = new usuamadina.geolocation.listeners.LocationListener(this);

        locationManager.requestLocationUpdates(provider, t, distance,listener);




    }


    @Override
    public void setLocation(Location location) {
        lblLatitude.setText(String.valueOf(location.getLatitude()));
        lblLongitude.setText(String.valueOf(location.getLongitude()));
        lblAltitude.setText(String.valueOf(location.getLatitude()));
        lblSpeed.setText(String.valueOf(location.getSpeed()));

    }

    @Override
    public void onConnected(Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}