package com.massive.movieapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.massive.movieapp.model.Movie;
import com.massive.movieapp.utils.NetworkUtils;

import java.util.ArrayList;

public class FragmentForActivity extends Fragment implements ICallBack {
    public static final String MOVIE_KEY = "Movie_key";
    public BaseAdapterMovie MyAdapter;
    public ArrayList<Movie> movieArrayList;
    public GridView mGridView;
    public static String BASE_URL = "https://api.themoviedb.org/3/";
    public String MovieUrl = BASE_URL + "discover/movie?";
    public String MoviePopularUrl = BASE_URL + "movie/popular?";
    public String MovieTopRatedUrl = BASE_URL + "movie/top_rated?";


    public void movieMethod() {
        if (movieArrayList != null) {
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
            CharSequence text = "check your internet connection!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getActivity(), text, duration);
            toast.show();
        }

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
                CallNetwork(MoviePopularUrl);
                break;

            case R.id.sortByTopRated:
                CallNetwork(MovieTopRatedUrl);
                break;

            case R.id.normal:
                CallNetwork(MovieUrl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_grid, null, false);
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
    public void onPostExcuteCallBack(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        movieMethod();
    }


}
