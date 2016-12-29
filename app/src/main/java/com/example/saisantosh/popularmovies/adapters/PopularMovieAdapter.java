package com.example.saisantosh.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saisantosh.popularmovies.R;
import com.example.saisantosh.popularmovies.activities.PopularMoviesActivity;
import com.example.saisantosh.popularmovies.activities.PopularMoviesDetailActivity;
import com.example.saisantosh.popularmovies.fragments.PopularMoviesDetailFragment;
import com.example.saisantosh.popularmovies.models.PopularMoviesApiData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {
    private ArrayList<PopularMoviesApiData> data = new ArrayList<>();
    private Context context;
    private boolean mTwoPane;

    public PopularMovieAdapter(Context context, boolean mTwoPane) {
        this.context = context;
        this.mTwoPane = mTwoPane;
    }

    public void setData(ArrayList<PopularMoviesApiData> data) {
        this.data = data;
    }

    @Override
    public PopularMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularMovieAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + data.get(position).getPosterPath()).error(R.drawable.noimage).placeholder(R.drawable.loading).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_image)
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.movie_image)
        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            if(mTwoPane) {
                PopularMoviesDetailFragment fragment = new PopularMoviesDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", data.get(itemPosition));
                fragment.setArguments(bundle);
                ((PopularMoviesActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.movies_detail_container, fragment).commit();
            }else{
                Intent intent = new Intent(context,PopularMoviesDetailActivity.class);
                intent.putExtra("data",data.get(itemPosition));
                context.startActivity(intent);
            }

        }
    }

}
