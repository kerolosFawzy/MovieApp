package com.massive.movieapp.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.massive.movieapp.ICallBack;
import com.massive.movieapp.R;
import com.massive.movieapp.model.Movie;
import com.massive.movieapp.model.MovieTrailer;
import com.massive.movieapp.utils.Constants;
import com.massive.movieapp.webservices.WebServiceCall;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetialMovieApp extends Fragment implements ICallBack<MovieTrailer> {
    private static final String MOVIE_kEY = "Movie_key";
    private Movie MyInfo;
    Button FirstButton, SecondButton, ThirdButton, ShowReview;
    public ArrayList<MovieTrailer> movieTrailorsArrayList;
    private String baseUrlVideoPlayer = "https://www.youtube.com/watch?v=";

    @BindView(R.id.original_title_tv)
    TextView Title;
    @BindView(R.id.OverViewShow)
    TextView ovewView;
    @BindView(R.id.ReleaseDataView)
    TextView relsedData;
    @BindView(R.id.ratingBar)
    RatingBar Rate;
    @BindView(R.id.detialImgview)
    ImageView imageView;

//    @BindView(R.id.buttonShowView1)
//    Button FirstButton;
//    @BindView(R.id.buttonShowView2)
//    Button SecondButton;
//    @BindView(R.id.buttonShowView3)
//    Button ThirdButton;
//    @BindView( R.id.ShowReview )
//    Button ShowReview;


    public static DetialMovieApp newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable( MOVIE_kEY, movie );
        DetialMovieApp fragment = new DetialMovieApp();
        fragment.setArguments( args );
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_detials, container, false );
//        Title = (TextView) rootView.findViewById( original_title_tv );
//        ovewView = (TextView) rootView.findViewById( R.id.OverViewShow );
//        relsedData = (TextView) rootView.findViewById( R.id.ReleaseDataView );
//        Rate = (RatingBar) rootView.findViewById( R.id.ratingBar );
//        imageView = (ImageView) rootView.findViewById( R.id.detialImgview );
        FirstButton = (Button) rootView.findViewById( R.id.buttonShowView1 );
        SecondButton = (Button) rootView.findViewById( R.id.buttonShowView2 );
        ThirdButton = (Button) rootView.findViewById( R.id.buttonShowView3 );
        ShowReview = (Button) rootView.findViewById( R.id.ShowReview );
        MyInfo = (Movie) getArguments().getSerializable( MOVIE_kEY );

        if ((null != MyInfo)) { //set all details in my second fragment
            String tailorUrl = "https://api.themoviedb.org/3/movie/" + MyInfo.getId() + "/videos";
            Type type = new TypeToken<List<MovieTrailer>>() {}.getType();
            new WebServiceCall<MovieTrailer>( this, getActivity(), type ).execute( tailorUrl );
            ButterKnife.bind( this, rootView );
            Title.setText( MyInfo.getOriginal_title() );
            ovewView.setText( MyInfo.getOverview() );
            relsedData.setText( MyInfo.getRelease_date() );
            Rate.setRating( (Float.valueOf( (float) MyInfo.getVote_average() )) / 2 );
            Picasso.with( getActivity() ).load( Constants.PosterUrl + MyInfo.getPoster_path() )
                    .error( R.drawable.noimage )
                    .networkPolicy( NetworkPolicy.OFFLINE )
                    .into( imageView );
        }
        ShowReview.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new ReviewDetial().newInstance(MyInfo);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace( R.id.Container,fragment ).addToBackStack( null ).commit();

            }
        } );
        FirstButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    watchYoutubeVideo( movieTrailorsArrayList.get( 0 ).getKey() );
                } catch (Exception e) {
                    Toast.makeText( getActivity(), "No Video Unavailable!", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        SecondButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    watchYoutubeVideo( movieTrailorsArrayList.get( 1 ).getKey() );
                } catch (Exception e) {
                    Toast.makeText( getActivity(), "No Video Unavailable!", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        ThirdButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    watchYoutubeVideo( movieTrailorsArrayList.get( 2 ).getKey() );
                } catch (Exception e) {
                    Toast.makeText( getActivity(), "No Video Unavailable!", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        return rootView;
    }

    private void watchYoutubeVideo(String key) {
        try {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( baseUrlVideoPlayer + key ) );
            getActivity().startActivity( intent );
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent( Intent.ACTION_VIEW,
                    Uri.parse( baseUrlVideoPlayer + key ) );
            getActivity().startActivity( intent );
        }
    }


    @Override
    public void onPostExcuteCallBack(ArrayList<MovieTrailer> ArrayList) {
        this.movieTrailorsArrayList = ArrayList;
    }
}

