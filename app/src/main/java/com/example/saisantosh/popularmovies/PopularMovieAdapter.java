package com.example.saisantosh.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sai.santosh on 19/06/16.
 */
public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.ViewHolder> {
    private ArrayList<PopularMoviesApiData> data = new ArrayList<>();
    private Context context;

    public PopularMovieAdapter(Context context, ArrayList<PopularMoviesApiData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public PopularMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopularMovieAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/" + data.get(position).getPosterPath()).placeholder(R.drawable.noimage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.movie_image);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, PopularMoviesDetail.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            int itemPosition = getAdapterPosition();
            intent.putExtra("data", data.get(itemPosition));
            context.startActivity(intent);
        }
    }

}
