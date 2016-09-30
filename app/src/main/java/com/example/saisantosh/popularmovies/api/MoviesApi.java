package com.example.saisantosh.popularmovies.api;


import com.example.saisantosh.popularmovies.models.PopularMoviesResponse;
import com.example.saisantosh.popularmovies.models.PopularMoviesReviews;
import com.example.saisantosh.popularmovies.models.PopularMoviesTrailers;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MoviesApi {
    @GET("movie/top_rated")
    Call<PopularMoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<PopularMoviesTrailers> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<PopularMoviesReviews> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);
}
