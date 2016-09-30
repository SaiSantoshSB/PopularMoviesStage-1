package com.example.saisantosh.popularmovies.Fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisantosh.popularmovies.BuildConfig;
import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.activities.PopularMoviesActivity;
import com.example.saisantosh.popularmovies.data.PopularMoviesContracts;
import com.example.saisantosh.popularmovies.models.PopularMoviesApiData;
import com.example.saisantosh.popularmovies.models.PopularMoviesReviews;
import com.example.saisantosh.popularmovies.models.PopularMoviesTrailers;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesDetailFragment extends Fragment implements View.OnClickListener {
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
    @BindView(R.id.trailers)
    LinearLayout trailers;
    @BindView(R.id.reviews)
    LinearLayout reviews;
    @BindView(R.id.trailersImage)
    ImageView showTrailers;
    @BindView(R.id.reviewsImage)
    ImageView showReviews;
    @BindView(R.id.overviewImage)
    ImageView showOverview;
    @BindView(R.id.add_as_favorite)
    ImageView favorite;
    private PopularMoviesApiData data;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies_detail, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        data = getArguments().getParcelable("data");
        if (data != null) {
            if (movieTitle != null) {
                movieTitle.setText(data.getOriginalTitle());
            }
            Picasso.with(context).load("http://image.tmdb.org/t/p/w342/" + data.getPosterPath()).error(R.drawable.noimage).placeholder(R.drawable.loading).into(moviePoster);
            if (synopsis != null) {
                synopsis.setText(data.getOverView());
            }
            if (rating != null) {
                rating.setText(MessageFormat.format("{0}{1}", String.valueOf(data.getVoteAverage()), getString(R.string.max_rating)));
            }
            if (date != null) {
                date.setText(data.getReleaseDate());
            }
            if (trailers != null) {
                loadTrailers();

            }
            if (reviews != null) {
                loadReviews();
            }
            if (showOverview != null) {
                showOverview.setOnClickListener(this);
            }
            if (showReviews != null) {
                showReviews.setOnClickListener(this);
            }
            if (showTrailers != null) {
                showTrailers.setOnClickListener(this);
            }
            if (favorite != null) {
                Cursor cursor = context.getContentResolver().query(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, null, "movieID = ?", new String[]{String.valueOf(data.getId())}, null);
                if (cursor != null && cursor.getCount() == 1) {
                    favorite.setSelected(true);
                    cursor.close();
                }
                favorite.setOnClickListener(this);
            }
        }
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        AppCompatActivity parentActivity = (AppCompatActivity) context;
        if (parentActivity != null && parentActivity.getSupportActionBar() != null) {
            parentActivity.getSupportActionBar().setHomeButtonEnabled(true);
            parentActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadReviews() {
        ((PopularMoviesActivity) context).getMoviesApi().getMovieReviews(data.getId(), BuildConfig.TMDB_MAP_API_KEY).enqueue(new Callback<PopularMoviesReviews>() {
            @Override
            public void onResponse(Call<PopularMoviesReviews> call, Response<PopularMoviesReviews> response) {
                final ArrayList<PopularMoviesReviews.PopularMoviesReviewsData> moviesReviews = response.body().getReviews();
                if (moviesReviews.size() == 0) {
                    View view = LayoutInflater.from(context).inflate(R.layout.review_layout, trailers, false);
                    ((TextView) view.findViewById(R.id.reviewText)).setText(R.string.review_hint);
                    view.findViewById(R.id.imageView2).setVisibility(View.GONE);
                    reviews.addView(view);
                } else {
                    for (int i = 0; i < moviesReviews.size(); i++) {
                        View view = LayoutInflater.from(context).inflate(R.layout.review_layout, trailers, false);

                        TextView reviewText = (TextView) view.findViewById(R.id.reviewText);
                        reviewText.setText(MessageFormat.format("Review by {0}", moviesReviews.get(i).getAuthor()));

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final PopularMoviesReviews.PopularMoviesReviewsData popularMoviesReviewsData = moviesReviews.get((Integer) view.getTag());
                                String url = popularMoviesReviewsData.getUrl();
                                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                CustomTabsIntent customTabsIntent = builder.build();
                                customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
                            }
                        });
                        reviews.addView(view);
                        view.setTag(reviews.indexOfChild(view));
                    }
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesReviews> call, Throwable t) {

            }
        });
    }

    private void loadTrailers() {
        ((PopularMoviesActivity) context).getMoviesApi().getMovieTrailers(data.getId(), BuildConfig.TMDB_MAP_API_KEY).enqueue(new Callback<PopularMoviesTrailers>() {
            @Override
            public void onResponse(Call<PopularMoviesTrailers> call, Response<PopularMoviesTrailers> response) {
                final ArrayList<PopularMoviesTrailers.PopularMoviesTrailersData> moviesTrailers = response.body().getResults();
                if (moviesTrailers.size() == 0) {
                    View view = LayoutInflater.from(context).inflate(R.layout.trailer_layout, trailers, false);
                    ((TextView) view.findViewById(R.id.trailerText)).setText(R.string.trailer_hint);
                    view.findViewById(R.id.imageView2).setVisibility(View.GONE);
                    trailers.addView(view);
                } else {
                    for (int i = 0; i < moviesTrailers.size(); i++) {
                        View view = LayoutInflater.from(context).inflate(R.layout.trailer_layout, trailers, false);
                        ((TextView) view.findViewById(R.id.trailerText)).setText(MessageFormat.format("Trailer   {0}", i + 1));
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent1 = YouTubeStandalonePlayer.createVideoIntent(getActivity(), BuildConfig.YOUTUBE_API_KEY, moviesTrailers.get((Integer) view.getTag()).getKey(), 0, true, true);
                                startActivity(intent1);
                            }
                        });
                        trailers.addView(view);
                        view.setTag(trailers.indexOfChild(view));
                    }
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesTrailers> call, Throwable t) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trailersImage:
                expandView(trailers, showTrailers);
                break;
            case R.id.reviewsImage:
                expandView(reviews, showReviews);
                break;
            case R.id.overviewImage:
                expandView(synopsis, showOverview);
                break;
            case R.id.add_as_favorite:
                if (favorite.isSelected()) {
                    favorite.setSelected(false);
                    context.getContentResolver().delete(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, "movieID = ?", new String[]{String.valueOf(data.getId())});
                } else {
                    favorite.setSelected(true);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PopularMoviesContracts.PopularMoviesEntry.MOVIE_ID, data.getId());
                    contentValues.put(PopularMoviesContracts.PopularMoviesEntry.DATA, new Gson().toJson(data));
                    context.getContentResolver().insert(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, contentValues);
                }
                break;
        }
    }

    public void expandView(View view, ImageView imageView) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_less_black_36dp));
        } else if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_expand_more_black_36dp));
        }
    }
}
