package com.massive.movieapp;

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

import com.massive.movieapp.db.MovieDb;
import com.massive.movieapp.db.RealmControllers;
import com.massive.movieapp.utils.Constants;
import com.massive.movieapp.utils.NetworkUtils;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.massive.movieapp.utils.Constants.MovieUrl;


public class FragmentForActivity extends Fragment implements ICallBack {
    public static final String MOVIE_KEY = "Movie_key";
    private BaseAdapterMovie MyAdapter;
    public static ArrayList<MovieDb> movieArrayList;
    public GridView mGridView;
    public ArrayList mydata;
    public MovieDb movieDb;
    public static Realm realm;
    public RealmResults<MovieDb> findData;

    // https://api.themoviedb.org/3/discover/movie?api_key=5372c5a4714c65cf3e361699c681a136
    public void movieMethod() {
        if (movieArrayList != null && mGridView != null) {
            MyAdapter = new BaseAdapterMovie(movieArrayList, getActivity());
            mGridView.setAdapter(MyAdapter);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        CallNetwork(MovieUrl);
    }

    private void CallNetwork(String baseUrl) {
        if (NetworkUtils.isNetworkAvailable(getActivity())) {
            new Url_cont(this, getActivity()).execute(baseUrl);
        } else {
            getAllMovies();
            // showErrormessage();
        }
    }

    public void showErrormessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("No Internet");
        builder.setMessage("Internet is required. Please Retry.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CallNetwork(MovieUrl);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(getActivity(), "Network Unavailable!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_setting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByPopular:
                CallNetwork(Constants.MoviePopularUrl);
                break;

            case R.id.sortByTopRated:
                CallNetwork(Constants.MovieTopRatedUrl);
                break;

            case R.id.normal:
                CallNetwork(Constants.MovieUrl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView = (GridView) viewRoot.findViewById(R.id.gridInFragement);
        movieMethod();
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Fragment fragment = new DetialMovieApp();
                Bundle bundle = new Bundle();
                bundle.putSerializable(MOVIE_KEY, movieArrayList.get(position));
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.Container, fragment).addToBackStack(null).commit();
            }
        });
        return viewRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        realm = RealmControllers.with(this).getRealm();

    }

    public void getAllMovies() {
        realm = Realm.getDefaultInstance();
        findData = realm.where(MovieDb.class).findAll();
        movieArrayList = new ArrayList(findData);
        movieMethod();

    }

    public void putDataInRealm(ArrayList<MovieDb> resultObj) {
        realm = Realm.getInstance(getActivity());
        realm.beginTransaction();
        for (MovieDb item : resultObj) {
            movieDb = new MovieDb();
            movieDb.setMovieID(item.getMovieID());
            movieDb.setTitle(item.getTitle());
            movieDb.setDate(item.getDate());
            movieDb.setOverview(item.getOverview());
            movieDb.setRate(item.getRate());
            movieDb.setVote(item.getVote());
            movieDb.setBackdrop_path(item.getBackdrop_path());
            realm.copyToRealmOrUpdate(movieDb);
        }
        realm.commitTransaction();
        Log.i("allmydata", String.valueOf(mydata));
    }

    @Override
    public void onPostExcuteCallBack(ArrayList<MovieDb> movieArrayList) {
        this.movieArrayList = movieArrayList;
        movieMethod();
        putDataInRealm(movieArrayList);
    }


}
