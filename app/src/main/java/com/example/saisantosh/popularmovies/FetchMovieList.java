package com.example.saisantosh.popularmovies;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.saisantosh.popularmovies.PopularMovies.sortBy;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovieList extends AsyncTask<sortBy, Void, Void> {
    private RecyclerView recyclerView;
    private ProgressDialog progress;
    private Context context;
    private ArrayList<PopularMoviesApiData> data = new ArrayList<>();

    public FetchMovieList(RecyclerView recyclerView, ProgressDialog progress, Context context, ArrayList<PopularMoviesApiData> data) {
        this.recyclerView =recyclerView;
        this.progress =progress;
        this.context = context;
        this.data = data;
    }

    @Override
    protected Void doInBackground(sortBy... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String popularMoviesJsonStr;
        String POPULAR_MOVIES_BASE_URL = null;
        if (params[0].equals(sortBy.POPULAR)) {
            POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.TMDB_MAP_API_KEY;
        } else if (params[0].equals(sortBy.RATING)) {
            POPULAR_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + BuildConfig.TMDB_MAP_API_KEY;
        }

        URL url;
        try {
            url = new URL(POPULAR_MOVIES_BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            popularMoviesJsonStr = buffer.toString();
            JSONObject jsonObject = new JSONObject(popularMoviesJsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            data.clear();    //To remove previous values that are already present in the arrayList
            mapData(jsonArray);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progress.dismiss();

        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);
        PopularMovieAdapter movieAdapter = new PopularMovieAdapter(context, data);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();
    }

    private void mapData(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                data.add(new Gson().fromJson(String.valueOf(jsonObject), PopularMoviesApiData.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
