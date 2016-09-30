// Generated code from Butter Knife. Do not modify!
package com.example.saisantosh.popularmovies.adapters;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import com.example.saisantosh.popularmovies.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class PopularMovieAdapter$ViewHolder_ViewBinding<T extends PopularMovieAdapter.ViewHolder> implements Unbinder {
  protected T target;

  private View view2131558554;

  public PopularMovieAdapter$ViewHolder_ViewBinding(final T target, Finder finder, Object source) {
    this.target = target;

    View view;
    view = finder.findRequiredView(source, R.id.movie_image, "field 'imageView' and method 'onClick'");
    target.imageView = finder.castView(view, R.id.movie_image, "field 'imageView'", ImageView.class);
    view2131558554 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.imageView = null;

    view2131558554.setOnClickListener(null);
    view2131558554 = null;

    this.target = null;
  }
}
