package com.example.saisantosh.popularmovies.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PopularMoviesDBHelper extends SQLiteOpenHelper {

    //name & version
    private static final String DATABASE_NAME = "popularmovies.db";
    private static final int DATABASE_VERSION = 1;

    public PopularMoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES + "(" + PopularMoviesContracts.PopularMoviesEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PopularMoviesContracts.PopularMoviesEntry.DATA + " TEXT NOT NULL, " + PopularMoviesContracts.PopularMoviesEntry.MOVIE_ID + " TEXT NOT NULL ); ";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES + "'");

        onCreate(sqLiteDatabase);
    }
}
