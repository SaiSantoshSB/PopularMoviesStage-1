package com.example.saisantosh.popularmovies.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saisantosh.popularmovies.BuildConfig;
import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.activities.BaseAcitivty;
import com.example.saisantosh.popularmovies.data.PopularMoviesContracts;
import com.example.saisantosh.popularmovies.models.PopularMoviesApiData;
import com.example.saisantosh.popularmovies.models.PopularMoviesReviews;
import com.example.saisantosh.popularmovies.models.PopularMoviesTrailers;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable("movieData");
        } else {
            if (getArguments() != null && getArguments().getParcelable("data") != null) {
                data = getArguments().getParcelable("data");
            } else {
                return null;
            }
        }
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
                ReadFromFavDB fromFavDB = new ReadFromFavDB();
                try {
                    if (fromFavDB.execute().get()) {
                        favorite.setSelected(true);
                    }
                    favorite.setOnClickListener(this);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        setHasOptionsMenu(true);
        return view;
    }

    private void loadReviews() {
        ((BaseAcitivty) context).getMoviesApi().getMovieReviews(data.getId(), BuildConfig.TMDB_MAP_API_KEY).enqueue(new Callback<PopularMoviesReviews>() {
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
                Toast.makeText(context, "Cannot load reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTrailers() {
        ((BaseAcitivty) context).getMoviesApi().getMovieTrailers(data.getId(), BuildConfig.TMDB_MAP_API_KEY).enqueue(new Callback<PopularMoviesTrailers>() {
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
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + moviesTrailers.get((Integer) view.getTag()).getKey())));
                            }
                        });
                        trailers.addView(view);
                        view.setTag(trailers.indexOfChild(view));
                    }
                }
            }

            @Override
            public void onFailure(Call<PopularMoviesTrailers> call, Throwable t) {
                Toast.makeText(context, "Cannot load trailers", Toast.LENGTH_SHORT).show();
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
                    DeleteFromFavDB deleteFromFavDB = new DeleteFromFavDB();
                    deleteFromFavDB.execute();
                } else {
                    favorite.setSelected(true);
                    WriteToFavDB toFavDB = new WriteToFavDB();
                    toFavDB.execute();
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movieData", data);
        super.onSaveInstanceState(outState);
    }

    private class WriteToFavDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PopularMoviesContracts.PopularMoviesEntry.MOVIE_ID, data.getId());
            contentValues.put(PopularMoviesContracts.PopularMoviesEntry.DATA, new Gson().toJson(data));
            context.getContentResolver().insert(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, contentValues);
            return null;
        }
    }

    private class ReadFromFavDB extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            Cursor cursor = context.getContentResolver().query(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, null, "movieID = ?", new String[]{String.valueOf(data.getId())}, null);
            if (cursor != null && cursor.getCount() == 1) {
                cursor.close();
                return true;
            }
            return false;
        }
    }

    private class DeleteFromFavDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            context.getContentResolver().delete(PopularMoviesContracts.PopularMoviesEntry.CONTENT_URI, "movieID = ?", new String[]{String.valueOf(data.getId())});
            return null;
        }
    }
}
