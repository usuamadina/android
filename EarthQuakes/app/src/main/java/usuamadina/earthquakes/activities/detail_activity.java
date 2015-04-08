package usuamadina.earthquakes.activities;

/**
 * Created by cursomovil on 26/03/15
 *
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import usuamadina.earthquakes.R;
import usuamadina.earthquakes.fragments.EarthQuakeListFragment;
import usuamadina.earthquakes.model.Coordinate;
import usuamadina.earthquakes.model.EarthQuake;

public class detail_activity extends ActionBarActivity {

    public static final String EARTHQUAKE_ITEM = "EARTHQUAKE_ITEM";
    private TextView txtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.each_earthquake_detail);
        txtId = (TextView) findViewById(R.id.txtId);

        //recibimos el intent y extraemos el id mediante getStringExtra, el get parcelable del ToDoList
        // lo usamos cuando el objeto no es nativo de android, en este caso es un String y si lo es
        Intent detailIntent = getIntent();
        txtId.setText(detailIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE_ID));



    }


}
