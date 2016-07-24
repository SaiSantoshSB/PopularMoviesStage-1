package com.example.saisantosh.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PopularMoviesApiData implements Parcelable {

    public static final Creator<PopularMoviesApiData> CREATOR = new Creator<PopularMoviesApiData>() {
        @Override
        public PopularMoviesApiData createFromParcel(Parcel in) {
            return new PopularMoviesApiData(in);
        }

        @Override
        public PopularMoviesApiData[] newArray(int size) {
            return new PopularMoviesApiData[size];
        }
    };
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds = new ArrayList<>();
    @SerializedName("id")
    private int id;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String overView;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    public PopularMoviesApiData() {

    }

    protected PopularMoviesApiData(Parcel in) {
        adult = in.readByte() != 0;
        backdropPath = in.readString();
        id = in.readInt();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        overView = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        popularity = in.readFloat();
        video = in.readByte() != 0;
        voteAverage = in.readFloat();
        voteCount = in.readInt();
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdropPath);
        dest.writeInt(id);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overView);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(title);
        dest.writeFloat(popularity);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
    }
}
