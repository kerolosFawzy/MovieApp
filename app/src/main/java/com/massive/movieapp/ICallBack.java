package com.massive.movieapp;

import com.massive.movieapp.db.MovieDb;

import java.util.ArrayList;


public interface ICallBack {
    public void onPostExcuteCallBack(ArrayList<MovieDb> movieArrayList);
}
