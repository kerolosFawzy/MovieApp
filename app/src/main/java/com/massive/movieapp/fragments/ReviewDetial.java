package com.massive.movieapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.massive.movieapp.ICallBack;
import com.massive.movieapp.R;
import com.massive.movieapp.model.MovieReview;
import com.massive.movieapp.utils.NetworkUtils;
import com.massive.movieapp.webservices.WebServiceCall;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewDetial extends Activity implements ICallBack<MovieReview> {
    private String movieId;
    public static final String MOVIE_kEY = "Movie_key";
    public ArrayList<MovieReview> movieReviews;

    @BindView(R.id.ReviewAuthor)
    TextView ReviewAuthor;
    @BindView(R.id.ReviewContan)
    TextView ReviewContan;
    @BindView(R.id.layout)
    LinearLayout layout;

    @BindString(R.string.no_internet)
    String NoInternet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.review );
        ButterKnife.bind( this );
        movieId = getIntent().getStringExtra( MOVIE_kEY );
        callMyService();
    }

    private void updateView() {
        if (movieReviews != null) {
            SetTextToTextview(ReviewAuthor , movieReviews.get( 0 ).getAuthor() , "Not found" );
            SetTextToTextview(ReviewContan , movieReviews.get( 0 ).getContent() , "Not found" );

        }
    }

    private void SetTextToTextview(TextView textView, String value, String defaultValue) {
        if (value != null) {
            textView.setText( value );
        } else {
            textView.setText( defaultValue );
        }
    }


    void callMyService() {
        if (NetworkUtils.isNetworkAvailable( this )) {
            String reviewUrl = "https://api.themoviedb.org/3/movie/" + movieId + "/reviews";
            Type type = new TypeToken<ArrayList<MovieReview>>() {
            }.getType();
            new WebServiceCall<MovieReview>( this, this, type ).execute( reviewUrl );
        } else {
            Snackbar.make( layout, NoInternet, Snackbar.LENGTH_SHORT ).show();
        }

    }

    @Override
    public void onPostExcuteCallBack(ArrayList<MovieReview> ArrayList) {
        this.movieReviews = ArrayList;
        updateView();
    }
}
