package com.cursomovil.earthquakes.database;

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

    private EarthQuakeOpenHelper helper;
    private SQLiteDatabase db;

    public static final String EQ_ID_COLUMN = "_id";
    public static final String EQ_PLACE_COLUMN = "place";
    public static final String EQ_LAT_COLUMN = "lat";
    public static final String EQ_LONG_COLUMN = "long";
    public static final String EQ_DEPTH_COLUMN = "depth";
    public static final String EQ_MAG_COLUMN = "magnitude";
    public static final String EQ_URL_COLUMN = "url";
    public static final String EQ_TIME_COLUMN = "time";

    public static final String[]	ALL_COLUMNS	= {
                                                    EQ_ID_COLUMN,
                                                    EQ_PLACE_COLUMN,
                                                    EQ_LAT_COLUMN,
                                                    EQ_LONG_COLUMN,
                                                    EQ_DEPTH_COLUMN,
                                                    EQ_MAG_COLUMN,
                                                    EQ_URL_COLUMN,
                                                    EQ_TIME_COLUMN
                                                    };


    // El OpenHelper necesita un contexto, luego, lo pedimos en el constructor para que, cuando
    // se instancie desde fuera, se pase el contexto como parámetro
    public EarthQuakeDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private	static final String	DATABASE_NAME	=	"earthquakes.db";
        private	static final int	DATABASE_VERSION	=	1;
        private	static final String	DATABASE_TABLE	=	"EARTHQUAKES";
        private static final String DATABASE_DROP_TABLE = "DROP	TABLE IF EXISTS	";

        // SQL Statement to create a new database
        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + "(" +
                EQ_ID_COLUMN +" TEXT PRIMARY KEY, "+
                EQ_PLACE_COLUMN + " TEXT, " +
                EQ_MAG_COLUMN + " REAL, " +
                EQ_LAT_COLUMN + " REAL, " +
                EQ_LONG_COLUMN + " REAL, " +
                EQ_DEPTH_COLUMN + " REAL, " +
                EQ_URL_COLUMN + " TEXT, " +
                EQ_TIME_COLUMN + " INTEGER)";

        private EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //	Simplest	case	is	to	drop	the	old	table	and	create	a	new	one.
            db.execSQL(	DATABASE_DROP_TABLE + DATABASE_TABLE);
            //	Create	a	new	one.
            onCreate(db);
        }


    }

    public	void	addNewEarthquake(EarthQuake eq){

        //	Create	a	new	row	of	values	to	insert.
        ContentValues newValues	=	new	ContentValues();

        //	Assign	values	for	each	row.
        newValues.put(EQ_ID_COLUMN,	eq.get_id());
        newValues.put(EQ_PLACE_COLUMN,	eq.getPlace());
        newValues.put(EQ_LAT_COLUMN,	eq.getCoords().getLat());
        newValues.put(EQ_LONG_COLUMN,	eq.getCoords().getLng());
        newValues.put(EQ_DEPTH_COLUMN,	eq.getCoords().getDepth());
        newValues.put(EQ_MAG_COLUMN,	eq.getMagnitude());
        newValues.put(EQ_URL_COLUMN,	eq.getUrl());
        newValues.put(EQ_TIME_COLUMN,	eq.getTime().getTime());


        //	Insert	the	row	into	your	table
        //SQLiteDatabase	db	=	EarthQuakeOpenHelper.getWritableDatabase();
        try {
            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, newValues);
        } catch (SQLException e) {
            Log.d("EARTHEQUAKE", "ERROR en el insert: " + e);
        }

    }

    private ArrayList<EarthQuake> query(String where, String[] whereArgs) {
        ArrayList<EarthQuake> eqs = new ArrayList<>();

        Cursor	cursor	=	db.query(
                EarthQuakeOpenHelper.DATABASE_TABLE,
                ALL_COLUMNS,
                where,
                whereArgs,
                null,// groupBy,
                null,// having,
                EQ_TIME_COLUMN + " DESC"
        );// order);

        HashMap<String, Integer> indexes = new HashMap<>();

        for (int i = 0; i < ALL_COLUMNS.length; i++) {
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }
        //	Iterate	over	the	cursors	rows
        while	(cursor.moveToNext())	{
            EarthQuake eq = new EarthQuake();
            eq.set_id(cursor.getString(indexes.get(EQ_ID_COLUMN)));
            eq.setPlace(cursor.getString(indexes.get(EQ_PLACE_COLUMN)));
            eq.setMagnitude(cursor.getDouble(indexes.get(EQ_MAG_COLUMN)));
            eq.setCoords(new Coordinate(cursor.getDouble(indexes.get(EQ_LAT_COLUMN)),
                                        cursor.getDouble(indexes.get(EQ_LONG_COLUMN)),
                                        cursor.getDouble(indexes.get(EQ_DEPTH_COLUMN))));
            eq.setUrl(cursor.getString(indexes.get(EQ_URL_COLUMN)));
            eq.setTime(cursor.getLong(indexes.get(EQ_TIME_COLUMN)));

            eqs.add(eq);

        }

        cursor.close();

        return eqs;
    }

    public ArrayList<EarthQuake> getAll() {
        return query(null, null);
    }

    public ArrayList<EarthQuake> getEarthQuakesByMagnitude(int magnitude) {
        String where = EQ_MAG_COLUMN + " >= ?";
        String[] whereArgs = {
                String.valueOf(magnitude)
        };

        return query(where, whereArgs);
    }

    public ArrayList<EarthQuake> getEarthQuakesById(String id) {
        String where = EQ_ID_COLUMN + " = ?";
        String[] whereArgs = {
                id
        };

        return query(where, whereArgs);
    }

}
