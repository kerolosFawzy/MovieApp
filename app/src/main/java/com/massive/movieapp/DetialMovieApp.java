package com.massive.movieapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.massive.movieapp.model.Movie;
import com.squareup.picasso.Picasso;


public class DetialMovieApp extends Fragment {
    Movie MyInfo;
    private TextView Title  , ovewView , relsedData;
    private RatingBar Rate;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detials, container, false);
        Title = (TextView) rootView.findViewById(R.id.original_title_tv);
        ovewView=(TextView) rootView.findViewById(R.id.OverViewShow);
        relsedData=(TextView) rootView.findViewById(R.id.ReleaseDataView);
        Rate=(RatingBar)rootView.findViewById(R.id.ratingBar);
        imageView=(ImageView)rootView.findViewById(R.id.detialImgview);
        MyInfo = (Movie) getArguments().getSerializable(FragmentForActivity.MOVIE_KEY);
        if(null != MyInfo){
            Title.setText(MyInfo.getTitle());
            ovewView.setText(MyInfo.getOverview());
            relsedData.setText(MyInfo.getDate());
            Rate.setRating((Float.valueOf(MyInfo.getVote()))/2);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+MyInfo.getPoster_image()).into(imageView);
        }
        return rootView;
    }


}

