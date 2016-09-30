package com.example.saisantosh.popularmovies.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PopularMoviesReviews {

    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<PopularMoviesReviewsData> reviews;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("total_results")
    private int totalResults;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {

        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<PopularMoviesReviewsData> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<PopularMoviesReviewsData> reviews) {
        this.reviews = reviews;
    }

    public class PopularMoviesReviewsData {
        @SerializedName("id")
        private String id;
        @SerializedName("author")
        private String author;
        @SerializedName("content")
        private String content;
        @SerializedName("url")
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
