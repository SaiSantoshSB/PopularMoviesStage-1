package com.example.saisantosh.popularmovies.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saisantosh.popularmovies.BuildConfig;
import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.adapters.PopularMovieAdapter;
import com.example.saisantosh.popularmovies.api.ApiClient;
import com.example.saisantosh.popularmovies.api.MoviesApi;
import com.example.saisantosh.popularmovies.data.PopularMoviesContracts;
import com.example.saisantosh.popularmovies.models.PopularMoviesApiData;
import com.example.saisantosh.popularmovies.models.PopularMoviesResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovies extends AppCompatActivity {
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;
    private final Context context = PopularMovies.this;
    private MoviesApi moviesApi;
    private ArrayList<PopularMoviesApiData> moviesApiData = new ArrayList<>();
    private PopularMovieAdapter movieAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        ButterKnife.bind(this);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(layoutManager);
        }
        movieAdapter = new PopularMovieAdapter(context);
        moviesApi = ApiClient.getClient().create(MoviesApi.class);
        if (savedInstanceState != null) {
            movieAdapter.setData(savedInstanceState.<PopularMoviesApiData>getParcelableArrayList("data"));
            recyclerView.setAdapter(movieAdapter);
        } else {
            showPopularMovies();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("data", moviesApiData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(layoutManager);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(context, 3);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    private void showTopRatedMovies() {
        moviesApiData.clear();
        Call<PopularMoviesResponse> call = moviesApi.getTopRatedMovies(BuildConfig.TMDB_MAP_API_KEY);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                moviesApiData = response.body().getResults();
                movieAdapter.setData(moviesApiData);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {

            }
        });
    }

    private void showPopularMovies() {
        moviesApiData.clear();
        Call<PopularMoviesResponse> call = moviesApi.getPopularMovies(BuildConfig.TMDB_MAP_API_KEY);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                moviesApiData = response.body().getResults();
                movieAdapter.setData(moviesApiData);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {

            }
        });
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
                showPopularMovies();
                return true;
            case R.id.menuSortRating:
                showTopRatedMovies();
                return true;
            case R.id.menuSortFavorite:
                showFavoriteMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFavoriteMovies() {
        String[] projection = new String[]{PopularMoviesContracts.PopularMoviesEntry.DATA};
        Cursor cursor = getContentResolver().query(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            moviesApiData.clear();
            do {
                moviesApiData.add(new Gson().fromJson(cursor.getString(cursor.getColumnIndexOrThrow("moviedata")), PopularMoviesApiData.class));
            } while (cursor.moveToNext());
            movieAdapter.setData(moviesApiData);
            recyclerView.setAdapter(movieAdapter);
            cursor.close();

        }
    }
}
