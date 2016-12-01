package com.massive.movieapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieReview implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
