package usuamadina.earthquakes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import usuamadina.earthquakes.R;
import usuamadina.earthquakes.model.EarthQuake;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarthQuakesMapFragment extends MapFragment implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap map;
    private List<EarthQuake> earthQuakes;
    public static final String EARTHQUAKE_LIST = "EARTHQUAKE_LIST";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = super.onCreateView(inflater,container,savedInstanceState);

        map = this.getMap();
        map.setOnMapLoadedCallback(this);

        return layout;
    }

    public void setEarthQuakes(List<EarthQuake> earthQuakes) {
        this.earthQuakes = earthQuakes;
    }

    @Override
    public void onMapLoaded() {

        EarthQuake oneEarthQuake;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if( earthQuakes.size() == 1){

            Log.d( EARTHQUAKE_LIST, "La lista solo tiene un elemento");

            oneEarthQuake = earthQuakes.get(0);

            String S;
            S = String.valueOf((oneEarthQuake.getMagnitude()));

            //usar los datos del terremoto para rellenar el marker

            MarkerOptions marker = new MarkerOptions()
                    .position(new LatLng(oneEarthQuake.getCoords().getLng(), oneEarthQuake.getCoords().getLat())).title(S);

            map.addMarker(marker);

            LatLng punto = new LatLng(oneEarthQuake.getCoords().getLng(), oneEarthQuake.getCoords().getLat());

            //centrar el mapa en ese punto

            CameraPosition cameraPosition = new CameraPosition.Builder().target(punto)
                    .zoom(5)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            map.animateCamera(cameraUpdate);


        } else if (earthQuakes.size() > 1) {

            for (EarthQuake earthQuake: earthQuakes){
                String S;
                S = String.valueOf((earthQuake.getMagnitude()));
                LatLng point= new LatLng(earthQuake.getCoords().getLng(),earthQuake.getCoords().getLng());


                MarkerOptions marker = new MarkerOptions()
                        .position(point)
                        .title(S)
                        .snippet(earthQuake.getCoords().toString());

                map.addMarker(marker);
                builder.include(marker.getPosition());

                //usar los datos del terremoto para rellenar el marker
            }

            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,0);

            map.animateCamera(cu);

        }



    }
}
