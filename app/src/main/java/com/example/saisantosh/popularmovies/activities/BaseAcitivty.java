package com.example.saisantosh.popularmovies.activities;


import android.support.v7.app.AppCompatActivity;

import com.example.saisantosh.popularmovies.api.ApiClient;
import com.example.saisantosh.popularmovies.api.MoviesApi;

public class BaseAcitivty extends AppCompatActivity {
    MoviesApi moviesApi = ApiClient.getClient().create(MoviesApi.class);
    public MoviesApi getMoviesApi() {
        return moviesApi;
    }
}
