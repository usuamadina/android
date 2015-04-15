package com.cursomovil.earthquakes.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cursomovil.earthquakes.R;
import com.cursomovil.earthquakes.model.EarthQuake;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthquakeAdapter extends ArrayAdapter<EarthQuake> {

    private int resource;

    public EarthquakeAdapter(Context context, int resource, List<EarthQuake> objects) {
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

        EarthQuake eq = getItem(position);

        TextView lblMag = (TextView) layout.findViewById(R.id.lblMagValue);
        TextView lblPlace = (TextView) layout.findViewById(R.id.lblPlace);
        TextView lblDate = (TextView) layout.findViewById(R.id.lblDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DecimalFormat df = new DecimalFormat("0.00");

        lblMag.setText(String.valueOf(df.format(eq.getMagnitude())));
        lblPlace.setText(eq.getPlace());
        lblDate.setText(sdf.format(eq.getTime()));

        // Para dar un color en base a la magnitud, dentro de una escala(de rojo a verde)
        int n = (int) (eq.getMagnitude() * 10);
        int color = Color.rgb((255 * n) / 100, (255 * (100 - n)) / 100, 0);
        lblMag.setBackgroundColor(color);

        return layout;
    }
}
