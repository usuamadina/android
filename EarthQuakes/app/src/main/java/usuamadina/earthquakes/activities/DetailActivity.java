package usuamadina.earthquakes.activities;

/**
 * Created by cursomovil on 26/03/15
 *
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.fragments.EarthQuakeListFragment;
import usuamadina.earthquakes.fragments.EarthQuakesMapFragment;
import usuamadina.earthquakes.model.EarthQuake;

public class DetailActivity extends ActionBarActivity {

    public static final String EARTHQUAKE_ITEM = "EARTHQUAKE_ITEM";
    private TextView txtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        txtId = (TextView) findViewById(R.id.txtId);


        //recibimos el intent y extraemos el id mediante getStringExtra, el get parcelable del ToDoList
        // lo usamos cuando el objeto no es nativo de android, en este caso es un String y si lo es

        Intent detailIntent = getIntent();

        EarthQuakeDB earthQuakeDB = new EarthQuakeDB(this);
        EarthQuake earthQuake = earthQuakeDB.getById(detailIntent.getStringExtra(EarthQuakeListFragment.EARTHQUAKE_ID));

        txtId.setText(earthQuake.getId());

        EarthQuakesMapFragment mapFragment = (EarthQuakesMapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);

        List<EarthQuake> earthQuakes = new ArrayList<>();
        earthQuakes.add(earthQuake);

        mapFragment.setEarthQuakes(earthQuakes);
    }





    /*
    * private void showMap(EarthQuake earthQuake){
    *  List <EarthQuake> earthQuakes = new ArrayList()
    *  }
    * */


}
