package com.example.saisantosh.popularmovies.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.saisantosh.popularmovies.BuildConfig;
import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.activities.PopularMoviesActivity;
import com.example.saisantosh.popularmovies.adapters.PopularMovieAdapter;
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

public class PopularMoviesFragment extends Fragment {
    @BindView(R.id.movies_recycler_view)
    RecyclerView recyclerView;
    private Context context;
    private ArrayList<PopularMoviesApiData> moviesApiData = new ArrayList<>();
    private PopularMovieAdapter movieAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            setLayoutManager(getActivity().getResources().getConfiguration().orientation);
            movieAdapter = new PopularMovieAdapter(context);
            if (moviesApiData.size() == 0) {
                showPopularMovies();
            } else {
                movieAdapter.setData(moviesApiData);
                recyclerView.setAdapter(movieAdapter);
            }
        }
        setHasOptionsMenu(true);
        return view;
    }

    private void setLayoutManager(int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3);
                recyclerView.setLayoutManager(layoutManager);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                layoutManager = new GridLayoutManager(context, 2);
                recyclerView.setLayoutManager(layoutManager);
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayoutManager(newConfig.orientation);
    }

    private void showTopRatedMovies() {
        moviesApiData.clear();
        Call<PopularMoviesResponse> call = ((PopularMoviesActivity) context).getMoviesApi().getTopRatedMovies(BuildConfig.TMDB_MAP_API_KEY);
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
        Call<PopularMoviesResponse> call = ((PopularMoviesActivity) context).getMoviesApi().getPopularMovies(BuildConfig.TMDB_MAP_API_KEY);
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        AppCompatActivity parentActivity = (AppCompatActivity) context;
        if (parentActivity != null && parentActivity.getSupportActionBar() != null) {
            parentActivity.getSupportActionBar().setHomeButtonEnabled(false);
            parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
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
        Cursor cursor = context.getContentResolver().query(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, projection, null, null, null);
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
