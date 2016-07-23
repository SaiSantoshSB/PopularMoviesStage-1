package com.example.saisantosh.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesDetail extends AppCompatActivity {
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.plot_synopsis)
    TextView synopsis;
    @BindView(R.id.user_rating)
    TextView rating;
    @BindView(R.id.release_date)
    TextView date;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        PopularMoviesApiData data = (PopularMoviesApiData) intent.getExtras().get("data");
        if (data != null) {
            if (movieTitle != null) {
                movieTitle.setText(data.getOriginalTitle());
            }
            Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/" + data.getPosterPath()).error(R.drawable.noimage).placeholder(R.drawable.loading).into(moviePoster);
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
