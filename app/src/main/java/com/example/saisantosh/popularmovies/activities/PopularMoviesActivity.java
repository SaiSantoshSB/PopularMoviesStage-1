package com.example.saisantosh.popularmovies.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saisantosh.popularmovies.Fragments.PopularMoviesDetailFragment;
import com.example.saisantosh.popularmovies.Fragments.PopularMoviesFragment;
import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.api.ApiClient;
import com.example.saisantosh.popularmovies.api.MoviesApi;


public class PopularMoviesActivity extends AppCompatActivity {
    MoviesApi moviesApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesApi = ApiClient.getClient().create(MoviesApi.class);
        setupAppBar();
        getFragmentManager().beginTransaction().replace(R.id.movies_fragment, new PopularMoviesFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setupAppBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Popular Movies");
        }
    }

    public MoviesApi getMoviesApi() {
        return moviesApi;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().findFragmentById(R.id.movies_fragment) instanceof PopularMoviesDetailFragment) {
                    getFragmentManager().popBackStack("movies", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else super.onOptionsItemSelected(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentById(R.id.movies_fragment) instanceof PopularMoviesDetailFragment) {
            getFragmentManager().popBackStack("movies", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }else super.onBackPressed();
    }

}
