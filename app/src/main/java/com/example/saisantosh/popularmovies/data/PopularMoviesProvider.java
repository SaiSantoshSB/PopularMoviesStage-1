package com.example.saisantosh.popularmovies.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class PopularMoviesProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PopularMoviesDBHelper mOpenHelper;

    private static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 200;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PopularMoviesContracts.CONTENT_AUTHORITY;
        matcher.addURI(authority, PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES, MOVIE);
        matcher.addURI(authority, PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES + "/#", MOVIE_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new PopularMoviesDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case MOVIE_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES,
                        projection,
                        PopularMoviesContracts.PopularMoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE: {
                return PopularMoviesContracts.PopularMoviesEntry.CONTENT_DIR_TYPE;
            }
            case MOVIE_WITH_ID: {
                return PopularMoviesContracts.PopularMoviesEntry.CONTENT_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                long _id = db.insert(PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES, null, contentValues);
                if (_id > 0) {
                    returnUri = PopularMoviesContracts.PopularMoviesEntry.buildFlavorsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {
            case MOVIE:
                numDeleted = db.delete(
                        PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES + "'");
                break;
            case MOVIE_WITH_ID:
                numDeleted = db.delete(PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES,
                        PopularMoviesContracts.PopularMoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES + "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                numUpdated = db.update(PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case MOVIE_WITH_ID: {
                numUpdated = db.update(PopularMoviesContracts.PopularMoviesEntry.TABLE_FAVORITES,
                        contentValues,
                        PopularMoviesContracts.PopularMoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
        }

        return numUpdated;
    }
}
