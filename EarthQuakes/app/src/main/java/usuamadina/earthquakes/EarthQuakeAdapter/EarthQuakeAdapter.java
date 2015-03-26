package usuamadina.earthquakes.EarthQuakeAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import usuamadina.earthquakes.EarthQuake;
import usuamadina.earthquakes.R;

/**
 * Created by Usua on 26/03/2015.
 */
public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake>{

    public EarthQuakeAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout;

        if (convertView == null){
            layout = new LinearLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater)getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        }else{
            layout = (LinearLayout)convertView;
        }

        EarthQuake item = getItem(position);
        TextView txtMagnitude = (TextView) layout.findViewById(R.id.txtMagnitude);
        TextView txtCoordinates = (TextView) layout.findViewById(R.id.txtCoordinates);
        TextView txtPlace = (TextView) layout.findViewById(R.id.txtPlace);
        TextView txtDate = (TextView) layout.findViewById(R.id.txtDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        txtMagnitude.setText(item.getMagnitude());
        txtCoordinates.setText(item.getCoords());
        txtPlace.setText(item.getMagnitude());
        txtDate.setText(item.getDate());

        return layout;

    }
}
