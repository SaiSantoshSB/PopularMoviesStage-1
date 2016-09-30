// Generated code from Butter Knife. Do not modify!
package com.example.saisantosh.popularmovies.activities;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.example.saisantosh.popularmovies.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class PopularMoviesDetail_ViewBinding<T extends PopularMoviesDetail> implements Unbinder {
  protected T target;

  public PopularMoviesDetail_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.movieTitle = finder.findRequiredViewAsType(source, R.id.movie_title, "field 'movieTitle'", TextView.class);
    target.synopsis = finder.findRequiredViewAsType(source, R.id.plot_synopsis, "field 'synopsis'", TextView.class);
    target.rating = finder.findRequiredViewAsType(source, R.id.user_rating, "field 'rating'", TextView.class);
    target.date = finder.findRequiredViewAsType(source, R.id.release_date, "field 'date'", TextView.class);
    target.moviePoster = finder.findRequiredViewAsType(source, R.id.movie_poster, "field 'moviePoster'", ImageView.class);
    target.trailers = finder.findRequiredViewAsType(source, R.id.trailers, "field 'trailers'", LinearLayout.class);
    target.reviews = finder.findRequiredViewAsType(source, R.id.reviews, "field 'reviews'", LinearLayout.class);
    target.showTrailers = finder.findRequiredViewAsType(source, R.id.trailersImage, "field 'showTrailers'", ImageView.class);
    target.showReviews = finder.findRequiredViewAsType(source, R.id.reviewsImage, "field 'showReviews'", ImageView.class);
    target.showOverview = finder.findRequiredViewAsType(source, R.id.overviewImage, "field 'showOverview'", ImageView.class);
    target.favorite = finder.findRequiredViewAsType(source, R.id.add_as_favorite, "field 'favorite'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.movieTitle = null;
    target.synopsis = null;
    target.rating = null;
    target.date = null;
    target.moviePoster = null;
    target.trailers = null;
    target.reviews = null;
    target.showTrailers = null;
    target.showReviews = null;
    target.showOverview = null;
    target.favorite = null;

    this.target = null;
  }
}
