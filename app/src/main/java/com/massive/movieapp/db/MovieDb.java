package com.massive.movieapp.db;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class MovieDb extends RealmObject implements Serializable{
//// TODO: 26/11/2016 must make it take all data and store it
    @PrimaryKey
    private String MovieID;
    @Index
    private String title;
    @Required
    private String Vote;
    private String overview;
    private String rate;
    private String poster_image;
    private String date;
    @Ignore
    private String backdrop_path;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMovieID() {
        return MovieID;
    }

    public void setMovieID(String movieID) {
        MovieID = movieID;
    }

}
