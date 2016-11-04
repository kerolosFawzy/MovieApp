package com.massive.movieapp.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Minafaw on 04/11/2016.
 */

public class MovieDb extends RealmObject {

    @PrimaryKey
    private String movieId;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
