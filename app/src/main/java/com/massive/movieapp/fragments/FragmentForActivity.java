package com.massive.movieapp.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.massive.movieapp.BaseAdapterMovie;
import com.massive.movieapp.ICallBack;
import com.massive.movieapp.MainActivity;
import com.massive.movieapp.R;
import com.massive.movieapp.db.FavouireDB;
import com.massive.movieapp.db.MovieDb;
import com.massive.movieapp.db.RealmControllers;
import com.massive.movieapp.model.Movie;
import com.massive.movieapp.utils.Constants;
import com.massive.movieapp.utils.NetworkUtils;
import com.massive.movieapp.webservices.WebServiceCall;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.massive.movieapp.utils.Constants.MovieUrl;


public class FragmentForActivity extends Fragment implements ICallBack<Movie> {
    public static final String MOVIE_KEY = "Movie_key";
    private BaseAdapterMovie MyAdapter;
    public static ArrayList<Movie> movieArrayList;
    public GridView mGridView;
    public ArrayList mydata;
    public MovieDb movieDb;
    public static Realm realm;
    public RealmResults<MovieDb> findData;


    public void movieMethod() {
        if (movieArrayList != null && mGridView != null) {
            MyAdapter = new BaseAdapterMovie( movieArrayList, getActivity() );
            mGridView.setAdapter( MyAdapter );
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
        CallNetwork( MovieUrl );
    }

    private void CallNetwork(String baseUrl) {
        if (NetworkUtils.isNetworkAvailable( getActivity() )) {
            Type type = new TypeToken<List<Movie>>() {
            }.getType();
            new WebServiceCall<Movie>( this, getActivity(), type ).execute( baseUrl );
        } else {
            showErrormessage();
        }
    }

    public void showErrormessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setCancelable( false );
        builder.setTitle( "No Internet" );
        builder.setMessage( "Internet is required. Please Retry." );
        builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        } );

        builder.setPositiveButton( "Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CallNetwork( MovieUrl );
            }
        } );
        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText( getActivity(), "Network Unavailable!", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu( menu, inflater );
        inflater.inflate( R.menu.my_setting, menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByPopular:
                CallNetwork( Constants.MoviePopularUrl );
                break;

            case R.id.sortByTopRated:
                CallNetwork( Constants.MovieTopRatedUrl );
                break;

            case R.id.normal:
                CallNetwork( Constants.MovieUrl );
                break;
            case R.id.Favourite: {
                realm = RealmControllers.with( getActivity() ).getRealm();
                if (RealmControllers.hasData()) {

                    movieArrayList = new ArrayList<>(  );
                    realm.beginTransaction();

                    ArrayList<FavouireDB> favouriteList = RealmControllers.with( getActivity() ).getAllfavouitemovie();
                    for (FavouireDB db : favouriteList) {
                        Movie movie = new Movie();
                        movie.setId( db.getId() );
                        movie.setTitle( db.getOriginal_title() );
                        movie.setOverview( db.getOverview() );
                        movie.setBackdrop_path( db.getBackdrop_path() );
                        movie.setPoster_path( db.getPoster_path() );
                        movie.setRelease_date( db.getRelease_date() );
                        movie.setVote_count( db.getVote_count() );
                        movie.setVote_average( db.getVote_average() );
                        movieArrayList.add( movie );
                    }
                    realm.commitTransaction();
                    movieMethod();
                } else {
                    Toast.makeText( getActivity(), "Your Data Base Is Empty", Toast.LENGTH_LONG ).show();
                }
            }
        }
        return super.onOptionsItemSelected( item );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate( R.layout.fragment_grid, null );
        mGridView = (GridView) viewRoot.findViewById( R.id.gridInFragement );
        movieMethod();

        mGridView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Fragment fragment = new DetialMovieApp().newInstance( movieArrayList.get( position ) );
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (((MainActivity) getActivity()).CheckTwoPane() != true) {
                    ft.replace( R.id.Container, fragment ).addToBackStack( null ).commit();
                } else {
                    ft.replace( R.id.details_frag, fragment ).commit();
                }
            }
        } );
        return viewRoot;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        realm = RealmControllers.with( this ).getRealm();
//
//    }

//    public void getAllMovies() {
//        int i = 0;
//        realm = Realm.getDefaultInstance();
//        findData = realm.where( MovieDb.class ).findAll();
//        //movieArrayList = new ArrayList( findData );
//        for (MovieDb movieDb : hasnene) {
//            movieArrayList.get( i ).;
//            i++;
//        }
//        movieMethod();
//
//    }

//    public void putDataInRealm(ArrayList<Movie> resultObj) {
//        realm = Realm.getInstance( getActivity() );
//        realm.beginTransaction();
//        for (Movie item : resultObj) {
//            movieDb = new MovieDb();
//            movieDb.setId( item.getId() );
//            movieDb.setOriginal_title( item.getOriginal_title() );
//            movieDb.setRelease_date( item.getRelease_date() );
//            movieDb.setOverview( item.getOverview() );
//            movieDb.setVote_average( item.getVote_average() );
//            movieDb.setVote_count( item.getVote_count() );
//            movieDb.setBackdrop_path( item.getBackdrop_path() );
//            realm.copyToRealmOrUpdate( movieDb );
//        }
//        realm.commitTransaction();
//        Log.i( "allmydata", String.valueOf( mydata ) );
//    }


    @Override
    public void onPostExcuteCallBack(ArrayList<Movie> ArrayList) {
        this.movieArrayList = ArrayList;
        Log.i( "my_arraylist", String.valueOf( movieArrayList ) );
        movieMethod();
        //putDataInRealm(movieArrayList);
    }
}
