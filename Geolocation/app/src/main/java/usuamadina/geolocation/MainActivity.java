package usuamadina.geolocation;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TextView lblLatitude;
    private TextView lblLongitude;
    private TextView lblAltitude;
    private TextView lblSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblLatitude = (TextView)findViewById(R.id.lblLatitude);
        lblLongitude = (TextView)findViewById(R.id.lblLongitude);
        lblAltitude = (TextView)findViewById(R.id.lblAltitude);
        lblSpeed = (TextView)findViewById(R.id.lblSpeed);


    }

}
