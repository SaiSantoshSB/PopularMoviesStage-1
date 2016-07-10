package com.example.saisantosh.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

public class PopularMoviesDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_detail);
        Intent intent = getIntent();
        PopularMoviesApiData data = (PopularMoviesApiData) intent.getExtras().get("data");
        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        final ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);
        TextView synopsis = (TextView) findViewById(R.id.plot_synopsis);
        TextView rating = (TextView) findViewById(R.id.user_rating);
        TextView date = (TextView) findViewById(R.id.release_date);
        if (data != null) {
            if (movieTitle != null) {
                movieTitle.setText(data.getOriginalTitle());
            }
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/" + data.getPosterPath()).placeholder(R.drawable.noimage).into(moviePoster);
            if (synopsis != null) {
                synopsis.setText(data.getOverView());
            }
            if (rating != null) {
                rating.setText(MessageFormat.format("{0}{1}", String.valueOf(data.getVoteAverage()), getString(R.string.max_rating)));
            }
            if (date != null) {
                date.setText(data.getReleaseDate());
            }
        }
    }
}
