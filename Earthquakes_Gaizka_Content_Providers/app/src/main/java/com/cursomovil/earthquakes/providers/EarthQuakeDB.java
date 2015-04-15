package com.cursomovil.earthquakes.providers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cursomovil.earthquakes.model.Coordinate;
import com.cursomovil.earthquakes.model.EarthQuake;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {


    private Context context;



    public static final String[]	ALL_COLUMNS	= {

            EarthQuakesProviders.Columns._ID,
            EarthQuakesProviders.Columns.EQ_PLACE_COLUMN,
            EarthQuakesProviders.Columns.EQ_LAT_COLUMN,
            EarthQuakesProviders.Columns.EQ_LONG_COLUMN,
            EarthQuakesProviders.Columns.EQ_DEPTH_COLUMN,
            EarthQuakesProviders.Columns.EQ_MAG_COLUMN,
            EarthQuakesProviders.Columns.EQ_URL_COLUMN,
            EarthQuakesProviders.Columns.EQ_TIME_COLUMN

    };

    public EarthQuakeDB (Context context){
        this.context = context;
    }




    public	void addNewEarthquake(EarthQuake eq){

        //	Create	a	new	row	of	values	to	insert.
        ContentValues newValues	=	new	ContentValues();

        //	Assign	values	for	each	row.
        newValues.put(EarthQuakesProviders.Columns._ID,	eq.get_id());
        newValues.put(EarthQuakesProviders.Columns.EQ_PLACE_COLUMN,	eq.getPlace());
        newValues.put(EarthQuakesProviders.Columns.EQ_LAT_COLUMN,	eq.getCoords().getLat());
        newValues.put(EarthQuakesProviders.Columns.EQ_LONG_COLUMN,	eq.getCoords().getLng());
        newValues.put(EarthQuakesProviders.Columns.EQ_DEPTH_COLUMN,	eq.getCoords().getDepth());
        newValues.put(EarthQuakesProviders.Columns.EQ_MAG_COLUMN,	eq.getMagnitude());
        newValues.put(EarthQuakesProviders.Columns.EQ_URL_COLUMN,	eq.getUrl());
        newValues.put(EarthQuakesProviders.Columns.EQ_TIME_COLUMN,	eq.getTime().getTime());




        ContentResolver cr = context.getContentResolver();

        cr.insert(EarthQuakesProviders.CONTENT_URI,newValues);


    }

    private ArrayList<EarthQuake> query(String where, String[] whereArgs) {
        ContentResolver cr = context.getContentResolver();

        ArrayList<EarthQuake> eqs = new ArrayList<>();
        String orderBy = EarthQuakesProviders.Columns.EQ_TIME_COLUMN + " DESC";

        Cursor	cursor	=	cr.query(
                EarthQuakesProviders.CONTENT_URI,
                ALL_COLUMNS,
                where,
                whereArgs,
               orderBy
        );

        HashMap<String, Integer> indexes = new HashMap<>();

        for (int i = 0; i < ALL_COLUMNS.length; i++) {
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }
        //	Iterate	over	the	cursors	rows
        while	(cursor.moveToNext())	{
            EarthQuake eq = new EarthQuake();
            eq.set_id(cursor.getString(indexes.get(EarthQuakesProviders.Columns.EQ_ID_COLUMN)));
            eq.setPlace(cursor.getString(indexes.get(EarthQuakesProviders.Columns.EQ_PLACE_COLUMN)));
            eq.setMagnitude(cursor.getDouble(indexes.get(EarthQuakesProviders.Columns.EQ_MAG_COLUMN)));
            eq.setCoords(new Coordinate(cursor.getDouble(indexes.get(EarthQuakesProviders.Columns.EQ_LAT_COLUMN)),
                                        cursor.getDouble(indexes.get(EarthQuakesProviders.Columns.EQ_LONG_COLUMN)),
                                        cursor.getDouble(indexes.get(EarthQuakesProviders.Columns.EQ_DEPTH_COLUMN))));
            eq.setUrl(cursor.getString(indexes.get(EarthQuakesProviders.Columns.EQ_URL_COLUMN)));
            eq.setTime(cursor.getLong(indexes.get(EarthQuakesProviders.Columns.EQ_TIME_COLUMN)));

            eqs.add(eq);

        }

        cursor.close();

        return eqs;

    }

    public ArrayList<EarthQuake> getAll() {

        return query(null, null);
    }

    public ArrayList<EarthQuake> getEarthQuakesByMagnitude(int magnitude) {

        ContentResolver cr = context.getContentResolver();

        String where = EarthQuakesProviders.Columns.EQ_MAG_COLUMN + " >= ?";
        String[] whereArgs = {
                String.valueOf(magnitude)
        };

        String orderBy = EarthQuakesProviders.Columns.EQ_TIME_COLUMN;

        return query(where, whereArgs);

    }

    public ArrayList<EarthQuake> getEarthQuakesById(String id) {
        /*
        String where = EQ_ID_COLUMN + " = ?";
        String[] whereArgs = {
                id
        };

        return query(where, whereArgs);
    }*/
        return null;

    }

}
