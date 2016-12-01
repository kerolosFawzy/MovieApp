package com.massive.movieapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.massive.movieapp.ICallBack;
import com.massive.movieapp.R;
import com.massive.movieapp.model.Movie;
import com.massive.movieapp.model.MovieReview;
import com.massive.movieapp.webservices.WebServiceCall;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewDetial extends Fragment implements ICallBack<MovieReview> {
    private Movie MyInfo;
    private static final String MOVIE_kEY = "Movie_key";
    public ArrayList<MovieReview> movieReviews;

    @BindView(R.id.ReviewAuthor)
    TextView ReviewAuthor;
    @BindView(R.id.ReviewContan)
    TextView ReviewContan;

    public static ReviewDetial newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable( MOVIE_kEY, movie );
        ReviewDetial fragment = new ReviewDetial();
        fragment.setArguments( args );
        return fragment;
    }

    void callMyService() {
        MyInfo = (Movie) getArguments().getSerializable( MOVIE_kEY );
        String reviewUrl = "https://api.themoviedb.org/3/movie/" + MyInfo.getId() + "/reviews";
        Type myType = new TypeToken<List<MovieReview>>() {
        }.getType();
        new WebServiceCall<MovieReview>( this, getActivity(), myType ).execute( reviewUrl );
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        callMyService();
        //// TODO: 01/12/2016 fix my call back movieReviews is null case of not get WebServiceCall at right time
        View view = inflater.inflate( R.layout.review, container, false );

        if (movieReviews.isEmpty() != true) {
            ButterKnife.bind( this, view );
            try {
                ReviewAuthor.setText( movieReviews.get( 0 ).getAuthor() );
                ReviewContan.setText( movieReviews.get( 0 ).getContent() );
            } catch (Exception e) {
                try {
                    ReviewAuthor.setText( movieReviews.get( 1 ).getAuthor() );
                    ReviewContan.setText( movieReviews.get( 1 ).getContent() );
                } catch (Exception e1) {
                    ReviewAuthor.setText( movieReviews.get( 2 ).getAuthor() );
                    ReviewContan.setText( movieReviews.get( 2 ).getContent() );
                }
            }
        }
        return view;
    }

    @Override
    public void onPostExcuteCallBack(ArrayList<MovieReview> ArrayList) {
        this.movieReviews = ArrayList;
    }
}
