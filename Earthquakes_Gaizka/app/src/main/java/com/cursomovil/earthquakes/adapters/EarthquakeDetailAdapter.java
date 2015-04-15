package com.cursomovil.earthquakes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cursomovil.earthquakes.R;
import com.cursomovil.earthquakes.model.EarthQuake;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 31/03/15.
 */
public class EarthquakeDetailAdapter extends ArrayAdapter{

    private int resource;

    public EarthquakeDetailAdapter(Context context, int resource, List<EarthQuake> objects) {
        super(context, resource, objects);

        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout layout;

        if (convertView == null) {
            // Si no existe la vista, la creamos
            layout = new RelativeLayout(getContext());

            LayoutInflater li;
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, layout, true);
        } else {
            layout = (RelativeLayout) convertView;
        }

        EarthQuake eq = (EarthQuake) getItem(position);


        TextView lblPlace = (TextView) layout.findViewById(R.id.txtPlace);
        TextView lblUrl = (TextView) layout.findViewById(R.id.txtLink);
        TextView lblDate = (TextView) layout.findViewById(R.id.txtDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        lblPlace.setText(eq.getPlace());
        lblUrl.setText(String.valueOf(eq.getUrl()));
        lblDate.setText(sdf.format(eq.getTime()));

        return layout;
    }
}
