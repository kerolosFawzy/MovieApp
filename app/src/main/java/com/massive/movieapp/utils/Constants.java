package com.massive.movieapp.utils;

public class Constants {
    //// TODO: 26/11/2016 prepare urls for video and review
    //https://api.themoviedb.org/3/movie/{id}/reviews?api_key=5372c5a4714c65cf3e361699c681a136
    //https://api.themoviedb.org/3/discover/movie?api_key=5372c5a4714c65cf3e361699c681a136
    //https://www.youtube.com/watch?v=(FnZF82_3Cts) this i get from
    //https://api.themoviedb.org/3/movie/movieid/videos?api_key=5372c5a4714c65cf3e361699c681a136
    public static String baseYoutube="https://www.youtube.com/watch?v=";
    public static String BASE_URL = "https://API.themoviedb.org/3/";
    public static String MovieUrl = BASE_URL + "discover/movie?";
    public static String MoviePopularUrl = BASE_URL + "movie/popular?";
    public static String MovieTopRatedUrl = BASE_URL + "movie/top_rated?";
    public static String PosterUrl = "http://image.tmdb.org/t/p/w185";
    public static String API ="api_key";

}
