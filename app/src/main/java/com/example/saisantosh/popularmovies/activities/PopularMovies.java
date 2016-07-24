package com.example.saisantosh.popularmovies.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saisantosh.popularmovies.api.ApiClient;
import com.example.saisantosh.popularmovies.BuildConfig;
import com.example.saisantosh.popularmovies.api.MoviesApi;
import com.example.saisantosh.popularmovies.adapters.PopularMovieAdapter;
import com.example.saisantosh.popularmovies.models.PopularMoviesApiData;
import com.example.saisantosh.popularmovies.models.PopularMoviesResponse;
import com.example.saisantosh.popularmovies.R;

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
    private PopularMovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(layoutManager);
        }
        movieAdapter = new PopularMovieAdapter(context);
        moviesApi = ApiClient.getClient().create(MoviesApi.class);
        showPopularMovies();
    }

    private void showTopRatedMovies() {


        Call<PopularMoviesResponse> call = moviesApi.getTopRatedMovies(BuildConfig.TMDB_MAP_API_KEY);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                ArrayList<PopularMoviesApiData> movies = response.body().getResults();
                movieAdapter.setData(movies);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {

            }
        });
    }

    private void showPopularMovies() {

        Call<PopularMoviesResponse> call = moviesApi.getPopularMovies(BuildConfig.TMDB_MAP_API_KEY);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                ArrayList<PopularMoviesApiData> movies = response.body().getResults();
                movieAdapter.setData(movies);
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
