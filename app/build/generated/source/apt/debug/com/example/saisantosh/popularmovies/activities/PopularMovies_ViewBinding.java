// Generated code from Butter Knife. Do not modify!
package com.example.saisantosh.popularmovies.activities;

import android.support.v7.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.example.saisantosh.popularmovies.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class PopularMovies_ViewBinding<T extends PopularMovies> implements Unbinder {
  protected T target;

  public PopularMovies_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.recyclerView = finder.findRequiredViewAsType(source, R.id.movies_recycler_view, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.recyclerView = null;

    this.target = null;
  }
}
