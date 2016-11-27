package com.massive.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.massive.movieapp.db.MovieDb;
import com.massive.movieapp.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class BaseAdapterMovie extends BaseAdapter {
    private ArrayList<MovieDb> movieArrayList;
    private Context mContext;
    private LayoutInflater Inflater;

    public BaseAdapterMovie(ArrayList<MovieDb> movieArrayList, Context context) {
        this.movieArrayList = movieArrayList;
        this.mContext = context;
        this.Inflater = LayoutInflater.from(context);

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
        final ViewHolder viewHolder;
        if (cameView == null) {
            cameView = Inflater.inflate(R.layout.row, null);
            viewHolder = new ViewHolder(cameView);
            cameView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) cameView.getTag();
        }
       final String url = Constants.PosterUrl + movieArrayList.get(position).getPoster_image();
        Picasso.with(mContext).load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(viewHolder.ivMoviePoster, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.i("onSuccess","imag="+url+" loaded");
                    }
                    @Override
                    public void onError() {
                        Picasso.with(mContext).load(url).into(viewHolder.ivMoviePoster);
                    }});
        //Picasso.with(mContext).load(url).into(viewHolder.ivMoviePoster);

        return cameView;
    }

    public class ViewHolder {

        protected ImageView ivMoviePoster;

        public ViewHolder(View view) {
            ivMoviePoster = (ImageView) view.findViewById(R.id.ivMoviePoster);
        }
    }


}