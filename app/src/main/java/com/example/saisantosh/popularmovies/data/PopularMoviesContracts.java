package com.example.saisantosh.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class PopularMoviesContracts {

    static final String CONTENT_AUTHORITY = "com.example.saisantosh.popularmovies.app";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class PopularMoviesEntry implements BaseColumns {
        public static final String DATA = "moviedata";
        public static final String MOVIE_ID = "movieID";
        static final String TABLE_FAVORITES = "favorites";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_FAVORITES).build();
        static final String _ID = "_id";
        static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FAVORITES;
        static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FAVORITES;

        static Uri buildFlavorsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
