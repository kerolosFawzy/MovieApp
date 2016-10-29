package com.massive.movieapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.massive.movieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Url_cont extends AsyncTask<String, Object, ArrayList<Movie>>  {

    final String log_tag = Url_cont.class.getSimpleName();
    ArrayList<Movie> resultObj = new ArrayList<>();
    ICallBack myCallBack;
    private Context mContext;
    ProgressDialog progressDialog;

    public Url_cont( ICallBack NetworkCallBack, Context mContext) {
        myCallBack = NetworkCallBack;
        this.mContext = mContext;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private ArrayList<Movie> formatMyJson(String jsonstr) throws JSONException {
        JSONObject object = new JSONObject(jsonstr);
        JSONArray mylist = object.getJSONArray("results");

        Movie mMovie;

        for (int i = 0; i < mylist.length(); i++) {
            mMovie = new Movie();
            JSONObject mynewob = mylist.getJSONObject(i);

            mMovie.setVote(mynewob.getString("vote_average"));
            mMovie.setTitle(mynewob.getString("original_title"));
            mMovie.setPoster_image(mynewob.getString("poster_path"));
            mMovie.setOverview(mynewob.getString("overview"));
            mMovie.setDate(mynewob.getString("release_date"));
            resultObj.add(mMovie);
        }
        return resultObj;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        String jsonstr = null;
        try {
            Uri builtUri = Uri.parse(params[0]).buildUpon().appendQueryParameter("api_key", BuildConfig.APIKRY).build();
            Log.i("url_site" , builtUri.toString());
            URL uRl = new URL(builtUri.toString());
            httpURLConnection = (HttpURLConnection) uRl.openConnection();
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            Log.i("ResponseCode", String.valueOf(responseCode));
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            jsonstr = response.toString();

        } catch (Exception e) {
            Log.e(log_tag, "this url has a problem 111");
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            return formatMyJson(jsonstr);
        } catch (Exception e) {
            Log.i(log_tag, "not thing work at alllllll");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> strings) {
        progressDialog.dismiss();
        myCallBack.onPostExcuteCallBack(strings);

    }



}