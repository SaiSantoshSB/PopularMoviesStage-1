package com.example.saisantosh.popularmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class PopularMovies extends AppCompatActivity {
    ArrayList<PopularMoviesApiData> data = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        initViews();
    }

    private void initViews() {
        FetchMovieList fetchMovieList = new FetchMovieList(recyclerView, progress, PopularMovies.this, data);
        fetchMovieList.execute(sortBy.POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSortPopularity:
                FetchMovieList fetchMovieList_popular = new FetchMovieList(recyclerView, progress, PopularMovies.this, data);
                fetchMovieList_popular.execute(sortBy.POPULAR);
                return true;
            case R.id.menuSortRating:
                FetchMovieList fetchMovieList_rating = new FetchMovieList(recyclerView, progress, PopularMovies.this, data);
                fetchMovieList_rating.execute(sortBy.RATING);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public enum sortBy {
        POPULAR, RATING
    }
}
