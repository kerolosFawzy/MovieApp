package com.massive.movieapp.model;

import java.io.Serializable;

/**
 * Created by Minafaw on 28/11/2016.
 */

public class MovieTrailer implements Serializable {

    private String id;
    private String key;
    private String site;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
