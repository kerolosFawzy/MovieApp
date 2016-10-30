package com.massive.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.massive.movieapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BaseAdapterMovie extends BaseAdapter {
    ArrayList<Movie> movieArrayList;
    Context context;
    LayoutInflater Inflater;


    public BaseAdapterMovie(ArrayList<Movie> movieArrayList, Context context1) {
        this.movieArrayList = movieArrayList;
        context = context1;
    }

    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View cameView, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (cameView == null) {
            Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cameView = Inflater.inflate(R.layout.row, null);
            viewHolder = new ViewHolder(cameView);
            cameView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) cameView.getTag();
        }
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+ movieArrayList.get(position).getPoster_image()).into(viewHolder.ivMoviePoster);

        return cameView;
    }

    public class ViewHolder {

        protected SquareImageView ivMoviePoster;
        public ViewHolder(View view) {
            ivMoviePoster = (SquareImageView) view.findViewById(R.id.ivMoviePoster);
        }
    }


}

