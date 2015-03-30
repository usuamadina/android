package usuamadina.earthquakes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
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
    public static final String KEY_DATE= "date";
    public static final String KEY_PLACE="place";
    public static final String KEY_LOCATION_LAT="lat";
    public static final String KEY_LOCATION_LNG= "lng";
    public static final String KEY_DEPTH = "depth";
    public static final String KEY_MAGNITUDE = "magnitude";
    public static final String KEY_LINK = "link";


    public static final String [] ALL_COLUMNS = {
            KEY_ID,
            KEY_DATE,
            KEY_PLACE,
            KEY_LOCATION_LAT,
            KEY_LOCATION_LNG,
            KEY_DEPTH,
            KEY_MAGNITUDE,
            KEY_LINK

    };

    public List <EarthQuake> getAllByMagnitude (int magnitude){
        String = where
    }




    public List<EarthQuake> query(String where, String[] wherarArgs){

        List<EarthQuake> earthQuakes = new ArrayList<>();

        Cursor cursor = db.query(
                EarthQuakeOpenHelper.DATABASE_TABLE,
                ALL_COLUMNS,
                where,
                wherarArgs,
                null,
                null,
                KEY_DATE + "DESC"
        );

        HashMap<String,Integer> indexes = new HashMap<>();
        for (int i = 0; i < ALL_COLUMNS.length; i++){
            indexes.put(ALL_COLUMNS[i],cursor.getColumnIndex(ALL_COLUMNS[i]));
        }

        //Recorremos el cursor, hay que moverse siempre al primero para que no nos de un error

        while (cursor.moveToNext()){
            EarthQuake earthQuake = new EarthQuake();
            earthQuake.setId(cursor.getString(indexes.get(KEY_ID)));
            earthQuake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
            earthQuake.setMagnitude(cursor.getDouble(indexes.get(KEY_MAGNITUDE)));
            earthQuake.setLatitude(cursor.getString(indexes.get(KEY_LOCATION_LAT)));
            earthQuake.setLongitude(cursor.getDouble(indexes.get(KEY_LOCATION_LNG)));
            earthQuake.setTime(cursor.getDouble(indexes.get(KEY_DEPTH)));
            earthQuake.

            //terminar
        }

        return null;
    }


    public void insertEarthQuake (EarthQuake earthQuake) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_ID, earthQuake.getId());
        newValues.put(KEY_DATE, earthQuake.getPlace());
        newValues.put(KEY_PLACE, earthQuake.getCoords().getLat());
        newValues.put(KEY_LOCATION_LAT, earthQuake.getCoords().getLng());
        newValues.put(KEY_LOCATION_LNG, earthQuake.getMagnitude());
        newValues.put(KEY_MAGNITUDE, earthQuake.getTime().getTime());
        newValues.put(KEY_LINK, earthQuake.getUrl());

        try {

            db.insert(EarthQuakeOpenHelper.DATABASE_TABLE, null, newValues);

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

      public ArrayList<EarthQuake> getAll(){


            Cursor cursor = db.query(
                    EarthQuakeOpenHelper,
                    ALL_COLUMNS,
                    null,
                    null,
                    null,
                    null,
                    KEY_DATE + "DESC"
            );

          HashMap<String,Integer> indexes = new HashMap<>();
          for (int i = 0; i < ALL_COLUMNS.length; i++){
              indexes.put(ALL_COLUMNS[i],cursor.getColumnIndex(ALL_COLUMNS[i]));
          }

         //Recorremos el cursor, hay que moverse siempre al primero para que no nos de un error

          while (cursor.moveToNext()){
              EarthQuake earthQuake = new EarthQuake();
              earthQuake.setId(cursor.getString(indexes.get(KEY_ID)));
              earthQuake.setPlace(cursor.getString(indexes.get(KEY_PLACE)));
              earthQuake.setMagnitude(cursor.getDouble(indexes.get(KEY_MAGNITUDE)));
              earthQuake.setLatitude(cursor.getString(indexes.get(KEY_LOCATION_LAT)));
              earthQuake.setLongitude(cursor.getDouble(indexes.get(KEY_LOCATION_LNG)));
              earthQuake.setTime(cursor.getDouble(indexes.get(KEY_DEPTH)));
              earthQuake.

                      //terminar
          }

            return null;
        }









    }







    public EarthQuakeDB(Context context) {
        this.helper = new EarthQuakeOpenHelper(context, EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        this.db = helper.getWritableDatabase();
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME= "earthquakes.db";
        private static final String DATABASE_TABLE= "EARTHQUAKES";
        private static final int DATABASE_VERSION= 1;





        private static final String DATABASE_CREATE= "CREATE TABLE " + DATABASE_TABLE + " (_id TEXT PRIMARY KEY, place TEXT, magnitude REAL, lat REAL, long REAL, depth REAL, url TEXT, time INTEGER)";



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


