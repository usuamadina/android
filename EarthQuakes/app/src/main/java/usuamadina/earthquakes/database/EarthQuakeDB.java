package usuamadina.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usuamadina.earthquakes.model.EarthQuake;

/**
 * Created by cursomovil on 27/03/15.
 */
public class EarthQuakeDB {

    private EarthQuakeOpenHelper helper;
    private Cursor cursor;
    private SQLiteDatabase db;

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_PLACE = "place";
    public static final String KEY_LOCATION_LAT = "lat";
    public static final String KEY_LOCATION_LNG = "long";
    public static final String KEY_DEPTH = "depth";
    public static final String KEY_MAGNITUDE = "magnitude";
    public static final String KEY_LINK = "link";


    public static final String[] ALL_COLUMNS = {
            KEY_ID,
            KEY_DATE,
            KEY_PLACE,
            KEY_LOCATION_LAT,
            KEY_LOCATION_LNG,
            KEY_DEPTH,
            KEY_MAGNITUDE,
            KEY_LINK

    };

    public EarthQuakeDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    public List<EarthQuake> getAll() {
        return query(null, null);
    }


    public List<EarthQuake> getAllByMagnitude(int magnitude) {
        String where = KEY_MAGNITUDE + " >= ?";
        String[] whereArgs = {
                String.valueOf(magnitude)
        };

        return query(where, whereArgs);
    }

    public EarthQuake getById(String id) {
        String where = KEY_ID + " = ?";
        String[] whereArgs = {id};

        List<EarthQuake> earthQuakes = query(where, whereArgs);
        if (earthQuakes.size() > 0) {
            return earthQuakes.get(0);
        }

        return null;
    }


    public List<EarthQuake> query(String where, String[] wherarArgs) {

        List<EarthQuake> earthQuakes = new ArrayList<>();

        Cursor cursor = db.query(
                EarthQuakeOpenHelper.DATABASE_TABLE,
                ALL_COLUMNS,
                where,
                wherarArgs,
                null,
                null,
                KEY_DATE + " DESC"
        );

        HashMap<String, Integer> indexes = new HashMap<>();
        for (int i = 0; i < ALL_COLUMNS.length; i++) {
            indexes.put(ALL_COLUMNS[i], cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        //Recorremos el cursor, hay que moverse siempre al primero para que no nos de un error

        while (cursor.moveToNext()) {

            EarthQuake earthQuake = new EarthQuake();

            earthQuake.setId(cursor.getString(indexes.get(KEY_ID)));
            earthQuake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(KEY_MAGNITUDE)));
            earthQuake.setLatitude(cursor.getDouble(indexes.get(KEY_LOCATION_LAT)));
            earthQuake.setLongitude(cursor.getDouble(indexes.get(KEY_LOCATION_LNG)));
            earthQuake.setDepth(cursor.getDouble(indexes.get(KEY_DEPTH)));
            earthQuake.setDate(cursor.getLong(indexes.get(KEY_DATE)));
            earthQuake.setUrl(cursor.getString(indexes.get(KEY_LINK)));
            earthQuakes.add(earthQuake);
        }

        return earthQuakes;
    }


    public void insertEarthQuake(EarthQuake earthQuake) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ID, earthQuake.getId());
        newValues.put(KEY_DATE, earthQuake.getTime().getTime());
        newValues.put(KEY_PLACE, earthQuake.getPlace());
        newValues.put(KEY_LOCATION_LAT, earthQuake.getCoords().getLat());
        newValues.put(KEY_LOCATION_LNG, earthQuake.getCoords().getLng());
        newValues.put(KEY_DEPTH, earthQuake.getCoords().getDepth());
        newValues.put(KEY_MAGNITUDE, earthQuake.getMagnitude());
        newValues.put(KEY_LINK, earthQuake.getUrl());

        try {

            db.insertOrThrow(EarthQuakeOpenHelper.DATABASE_TABLE, null, newValues);

        } catch (SQLException e) {

            Log.e("EARTHQUAKES", e.getMessage());

        }
    }


    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "earthquakes.db";
        private static final String DATABASE_TABLE = "EARTHQUAKES";
        private static final int DATABASE_VERSION = 1;


        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " TEXT PRIMARY KEY," +
                KEY_PLACE + " TEXT," +
                KEY_MAGNITUDE + " REAL," +
                KEY_LOCATION_LAT + " REAL," +
                KEY_LOCATION_LNG + " REAL," +
                KEY_DEPTH + " REAL," +
                KEY_LINK + " TEXT," +
                KEY_DATE + " INTEGER)";


        public EarthQuakeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);


        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        }


    }


}


