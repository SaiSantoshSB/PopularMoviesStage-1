package com.example.saisantosh.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMovies extends AppCompatActivity {
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;
    Context context = PopularMovies.this;
    ApiInterface apiService;

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
        showPopularMovies();
    }

    private void showTopRatedMovies() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PopularMoviesResponse> call = apiService.getTopRatedMovies(BuildConfig.TMDB_MAP_API_KEY);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                ArrayList<PopularMoviesApiData> movies = response.body().getResults();
                Log.d("movies", "Number of movies received: " + movies.size());

                PopularMovieAdapter movieAdapter = new PopularMovieAdapter(context, movies);
                recyclerView.setAdapter(movieAdapter);

            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("movies", t.toString());
            }
        });
    }

    private void showPopularMovies() {
        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PopularMoviesResponse> call = apiService.getPopularMovies(BuildConfig.TMDB_MAP_API_KEY);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(Call<PopularMoviesResponse> call, Response<PopularMoviesResponse> response) {
                ArrayList<PopularMoviesApiData> movies = response.body().getResults();
                Log.d("movies", "Number of movies received: " + movies.size());

                PopularMovieAdapter movieAdapter = new PopularMovieAdapter(context, movies);
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PopularMoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("movies", t.toString());
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
