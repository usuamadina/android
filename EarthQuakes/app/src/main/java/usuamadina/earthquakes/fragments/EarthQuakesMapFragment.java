package usuamadina.earthquakes.fragments;


import android.os.Bundle;
import android.app.Fragment;
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
public class EarthQuakesMapFragment extends MapFragment {
    private GoogleMap mMap;

    public EarthQuakesMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    public void paintEarthQuakes(List<EarthQuake> earthQuakeList){
        GoogleMap map = getMap();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        for (EarthQuake earthQuake: earthQuakeList){
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
        map.moveCamera(cu);





    }




}
