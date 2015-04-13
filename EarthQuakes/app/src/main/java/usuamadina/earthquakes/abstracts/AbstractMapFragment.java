package usuamadina.earthquakes.abstracts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 13/04/15.
 */
public abstract class AbstractMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    //Mucho cuidado con la visibilidad de las variables en las clases abstractas
    //

    private EarthQuakeDB earthQuakeDB;
    private GoogleMap map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        earthQuakeDB = new EarthQuakeDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = super.onCreateView(inflater, container, savedInstanceState);
        getMap().setOnMapLoadedCallback(this);

        return layout;
    }

    protected MarkerOptions createMarker (EarthQuake earthQuake){
        LatLng point = new LatLng(earthQuake.getCoords().getLng(),earthQuake.getCoords().getLat());

        MarkerOptions marker = new MarkerOptions()
                .position(point)
                .snippet(earthQuake.getCoords().toString());
        return marker;
    }



    //Obligamos a los que heredan de esta clase a que implementen los siguientes métodos

    abstract protected void getData(); // No se le ponen las llaves xq no se implementan aquí
    abstract protected void paintMap();


    @Override
    public void onMapLoaded() {

        this.getData();
        this.paintMap();

    }
}








