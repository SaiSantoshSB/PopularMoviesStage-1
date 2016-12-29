package com.example.saisantosh.popularmovies.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.fragments.PopularMoviesDetailFragment;

public class PopularMoviesDetailActivity extends BaseAcitivty {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_detail);
        setupAppBar();
        PopularMoviesDetailFragment detailFragment = new PopularMoviesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", getIntent().getExtras().getParcelable("data"));
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.movies_detail_container, detailFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupAppBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Movie Details");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
