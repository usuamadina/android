package usuamadina.earthquakes.abstracts;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import usuamadina.earthquakes.database.EarthQuakeDB;
import usuamadina.earthquakes.fragments.EarthQuakeListFragment;
import usuamadina.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 13/04/15.
 */
public class EarthQuakeMapFragment extends AbstractMapFragment {

    private EarthQuake earthQuake;
    private EarthQuakeDB earthQuakeDB;

    @Override
    protected void getData() {
        String id = getActivity().getIntent().getStringExtra(EarthQuakeListFragment.EARTHQUAKE_ID);
        earthQuake = earthQuakeDB.getById(id);

    }


    @Override
    protected void paintMap() {
        MarkerOptions marker = createMarker(earthQuake);
        getMap().addMarker(marker);


        CameraPosition cameraPosition = new CameraPosition.Builder().target(marker.getPosition())
                .zoom(5)
                .build();

        CameraUpdate camUpd = CameraUpdateFactory.newCameraPosition(cameraPosition);
        getMap().animateCamera(camUpd);

    }


}
