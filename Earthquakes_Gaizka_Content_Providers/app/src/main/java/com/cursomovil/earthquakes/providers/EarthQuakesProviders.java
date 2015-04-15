package com.cursomovil.earthquakes.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.SQLException;

public class EarthQuakesProviders extends ContentProvider {

    //Debemos exponer hacia el exterior  indicando la dirección de acceso

    public static final Uri CONTENT_URI = Uri.parse("content://com.usuamadina.provider.earthquakes/earthquakes");

    private static final int ALLROWS = 1;
    private static final int SINGLE_ROW = 2;
    private static final UriMatcher uriMatcher;

    EarthQuakeOpenHelper earthQuakeOpenHelper ;

    static{

        //Definimos las rutas
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.usuamadina.provider.earthquakes", "earthquakes", ALLROWS);
        uriMatcher.addURI("com.usuamadina.provider.earthquakes", "earthquakes/#", SINGLE_ROW);
    }

    //No es necesario implementar la clase Columns pero es recomendamedable xq Base Columns ya tiene la _id

    public static class Columns implements BaseColumns{

        public static final String EQ_ID_COLUMN = "_id";
        public static final String EQ_PLACE_COLUMN = "place";
        public static final String EQ_LAT_COLUMN = "lat";
        public static final String EQ_LONG_COLUMN = "long";
        public static final String EQ_DEPTH_COLUMN = "depth";
        public static final String EQ_MAG_COLUMN = "magnitude";
        public static final String EQ_URL_COLUMN = "url";
        public static final String EQ_TIME_COLUMN = "time";


    }


    public static final String[]	ALL_COLUMNS	= {

            Columns.EQ_ID_COLUMN,
            Columns.EQ_PLACE_COLUMN,
            Columns.EQ_LAT_COLUMN,
            Columns.EQ_LONG_COLUMN,
            Columns.EQ_DEPTH_COLUMN,
            Columns.EQ_MAG_COLUMN,
            Columns.EQ_URL_COLUMN,
            Columns.EQ_TIME_COLUMN
    };


    public EarthQuakesProviders() {
    }




    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case ALLROWS:
                return "vnd.android.cursor.dir/vnd.usuamadina.provider.earthquakes";
            case SINGLE_ROW:
                return "vnd.android.cursor.dir/vnd.usuamadina.provider.earthquakes";
            default:
                throw new IllegalArgumentException("Unsupported URI : " + uri);

        }


    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        SQLiteDatabase db = earthQuakeOpenHelper.getWritableDatabase();

        try {
            long id = db.insert(EarthQuakeOpenHelper.DATABASE_TABLE, null, values);

            // si tuviesemos varias tablas tendríamos que hacer un s
            // switch(uriMatcher.match(uri)){
            //     case ALL_ROWS:
            //         String table = EarthQuakeOpenHelper.DATABASE_TABLE;
            //     default:break;
            //
            // }


            //Esta parte es super importante
            if (id > -1) {


                // Construct and return the URI of the newly inserted row
                Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);

                //Patrón observer, notifica los cambios en el conjunto de datos
                getContext().getContentResolver().notifyChange(insertedId, null);
                return insertedId;

            } else {
                return null;
            }
        } catch (SQLiteException ex){
            Log.d("EARTHQUAKE", "no ha hecho el insert" + ex);
            return null;
        }


    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        earthQuakeOpenHelper = new EarthQuakeOpenHelper(getContext(), EarthQuakeOpenHelper.DATABASE_NAME, null, EarthQuakeOpenHelper.DATABASE_VERSION);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SQLiteDatabase db;
        try {
            db = earthQuakeOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = earthQuakeOpenHelper.getReadableDatabase();
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case SINGLE_ROW:    //Selecciona la uri SINGLE_ROW
                String rowID = uri.getPathSegments().get(1); // devuelve el valor de la # de earthquakes/#
                queryBuilder.appendWhere(Columns._ID + " = ?"); // Al hacer la query abajo no machaca este valor aunque se le llame desde fuera con otra condición porque es un append
                selectionArgs = new String[]{ rowID};
            default:
                break;
        }

        queryBuilder.setTables(EarthQuakeOpenHelper.DATABASE_TABLE);
        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static class EarthQuakeOpenHelper extends SQLiteOpenHelper {

        private	static final String	DATABASE_NAME	=	"earthquakes.db";
        private	static final int	DATABASE_VERSION	=	1;
        private	static final String	DATABASE_TABLE	=	"EARTHQUAKES";
        private static final String DATABASE_DROP_TABLE = "DROP	TABLE IF EXISTS	";

        // SQL Statement to create a new database
        private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + "(" +
                Columns.EQ_ID_COLUMN +" TEXT PRIMARY KEY, "+
                Columns.EQ_PLACE_COLUMN + " TEXT, " +
                Columns.EQ_MAG_COLUMN + " REAL, " +
                Columns.EQ_LAT_COLUMN + " REAL, " +
                Columns.EQ_LONG_COLUMN + " REAL, " +
                Columns.EQ_DEPTH_COLUMN + " REAL, " +
                Columns.EQ_URL_COLUMN + " TEXT, " +
                Columns.EQ_TIME_COLUMN + " INTEGER)";

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




}
