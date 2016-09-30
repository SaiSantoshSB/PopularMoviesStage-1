package com.example.saisantosh.popularmovies.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PopularMoviesTrailers {

    @SerializedName("id")
    private int id;
    @SerializedName("results")
    private ArrayList<PopularMoviesTrailersData> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PopularMoviesTrailersData> getResults() {
        return results;
    }

    public void setResults(ArrayList<PopularMoviesTrailersData> results) {
        this.results = results;
    }

    public class PopularMoviesTrailersData {
        @SerializedName("id")
        private String id;
        @SerializedName("iso_639_1")
        private String iso;
        @SerializedName("key")
        private String key;
        @SerializedName("name")
        private String name;
        @SerializedName("site")
        private String site;
        @SerializedName("size")
        private String size;
        @SerializedName("type")
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso() {
            return iso;
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
