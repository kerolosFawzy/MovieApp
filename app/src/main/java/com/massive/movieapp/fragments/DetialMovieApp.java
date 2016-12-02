package com.massive.movieapp.fragments;

import android.app.Fragment;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.reflect.TypeToken;
import com.massive.movieapp.ICallBack;
import com.massive.movieapp.R;
import com.massive.movieapp.db.FavouireDB;
import com.massive.movieapp.db.RealmControllers;
import com.massive.movieapp.model.Movie;
import com.massive.movieapp.model.MovieTrailer;
import com.massive.movieapp.utils.Constants;
import com.massive.movieapp.webservices.WebServiceCall;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


public class DetialMovieApp extends Fragment implements ICallBack<MovieTrailer> {
    private static final String MOVIE_kEY = "Movie_key";
    private Movie MyInfo;
    Button  AddToFav;
    public ArrayList<MovieTrailer> movieTrailorsArrayList;
    private String baseUrlVideoPlayer = "https://www.youtube.com/watch?v=";
    Realm realm;

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
    @BindView(R.id.buttonShowView2)
    Button SecondButton;
    @BindView(R.id.buttonShowView3)
    Button ThirdButton;
    @BindView( R.id.ShowReview )
    Button ShowReview;


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
        ButterKnife.bind( this, rootView );
        AddToFav=(Button)rootView.findViewById( R.id.addToFavourite ) ;
        MyInfo = (Movie) getArguments().getSerializable( MOVIE_kEY );

        final ArrayList<FavouireDB> favouriteList =  RealmControllers.with( getActivity()).getAllfavouitemovie();

        if ((null != MyInfo)) { //set all details in my second fragment
            String tailorUrl = "https://api.themoviedb.org/3/movie/" + MyInfo.getId() + "/videos";
            Type type = new TypeToken<List<MovieTrailer>>() {
            }.getType();
            new WebServiceCall<MovieTrailer>( this, getActivity(), type ).execute( tailorUrl );

            Title.setText( MyInfo.getOriginal_title() );
            ovewView.setText( MyInfo.getOverview() );
            relsedData.setText( MyInfo.getRelease_date() );
            Rate.setRating( (Float.valueOf( (float) MyInfo.getVote_average() )) / 2 );
            Glide.with( getActivity() ).load( Constants.PosterUrl + MyInfo.getBackdrop_path() )
                    .error( R.drawable.noimage )
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .into( imageView );
        }
        AddToFav.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   realm = RealmControllers.with( getActivity() ).getRealm();
                   realm.beginTransaction();
                   FavouireDB movieDb = new FavouireDB();
                   movieDb.setId( MyInfo.getId() );
                   movieDb.setOriginal_title( MyInfo.getOriginal_title() );
                   movieDb.setRelease_date( MyInfo.getRelease_date() );
                   movieDb.setOverview( MyInfo.getOverview() );
                   movieDb.setVote_average( MyInfo.getVote_average() );
                   movieDb.setVote_count( MyInfo.getVote_count() );
                   movieDb.setBackdrop_path( MyInfo.getBackdrop_path() );
                   movieDb.setPoster_path( MyInfo.getPoster_path() );
                   realm.copyToRealmOrUpdate( movieDb );
                   realm.commitTransaction();
                   Toast.makeText( getActivity(),"Added to favourite0",Toast.LENGTH_SHORT).show();
               }catch (Exception e){
                   Toast.makeText( getActivity(),"this movie did not put in favourite",Toast.LENGTH_SHORT).show();
               }

            }
        } );
        ShowReview.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity() , ReviewDetial.class );
                intent.putExtra( ReviewDetial.MOVIE_kEY , MyInfo.getId()  );
                getActivity().startActivity( intent );
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

    @OnClick(R.id.buttonShowView1)
    public void HandleButtonOneClicked(){
        try {
            watchYoutubeVideo( movieTrailorsArrayList.get( 0 ).getKey() );
        } catch (Exception e) {
            Toast.makeText( getActivity(), "No Video Unavailable!", Toast.LENGTH_LONG ).show();
        }
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

