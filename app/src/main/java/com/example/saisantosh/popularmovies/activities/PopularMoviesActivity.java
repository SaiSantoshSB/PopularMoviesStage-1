package com.example.saisantosh.popularmovies.activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saisantosh.popularmovies.fragments.PopularMoviesDetailFragment;
import com.example.saisantosh.popularmovies.fragments.PopularMoviesFragment;
import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.api.ApiClient;
import com.example.saisantosh.popularmovies.api.MoviesApi;


public class PopularMoviesActivity extends BaseAcitivty {
    MoviesApi moviesApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       moviesApi = getMoviesApi();
        boolean mTwoPane;
        if (findViewById(R.id.movies_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movies_detail_container, new PopularMoviesDetailFragment(), "Detail Fragment")
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
        setupAppBar();
        Fragment fragment = new PopularMoviesFragment();
        Bundle  bundle  = new Bundle();
        bundle.putBoolean("mTwoPane", mTwoPane);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.movies_fragment, fragment).commit();
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

}
