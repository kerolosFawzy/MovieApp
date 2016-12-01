//package com.massive.movieapp.network;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.AsyncTask;
//
//import com.massive.movieapp.BuildConfig;
//import com.massive.movieapp.ICallBack;
//import com.massive.movieapp.model.MovieTrailer;
//import com.massive.movieapp.utils.Constants;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
///**
// * Created by Minafaw on 28/11/2016.
// */
//
//public class FetchTrailars extends AsyncTask<String, Object, ArrayList<MovieTrailer>> {
//    private Context mContext;
//    ICallBack myCallBack;
//    ArrayList<MovieTrailer> resultObj = new ArrayList<>();
//
//    public FetchTrailars(ICallBack myCallBack, Context mContext) {
//        this.myCallBack = myCallBack;
//        this.mContext = mContext;
//    }
//
//    private ArrayList<MovieTrailer> formatMyJson(String jsonstr) throws JSONException {
//        JSONObject object = new JSONObject(jsonstr);
//        JSONArray mylist = object.getJSONArray("results");
//        MovieTrailer mMovie;
//        for (int i = 1; i < mylist.length(); i++) {
//            mMovie = new MovieTrailer();
//            JSONObject mynewob = mylist.getJSONObject(i);
//            mMovie.setId(mynewob.getString("id"));
//            mMovie.setKey(mynewob.getString("key"));
//            mMovie.setSite(mynewob.getString("site"));
//            resultObj.add(mMovie);
//        }
//        return resultObj;
//    }
//
//    @Override
//    protected ArrayList<MovieTrailer> doInBackground(String... params) {
//        HttpURLConnection httpURLConnection = null;
//        BufferedReader reader = null;
//        String jsonstr = null;
//        try {
//            Uri builtUri = Uri.parse(params[0]).buildUpon().appendQueryParameter(Constants.API, BuildConfig.APIKRY).build();
//            URL uRl = new URL(builtUri.toString());
//            httpURLConnection = (HttpURLConnection) uRl.openConnection();
//            httpURLConnection.connect();
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(httpURLConnection.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//            jsonstr = response.toString();
//        } catch (Exception e) {
//        } finally {
//            if (httpURLConnection != null) {
//                httpURLConnection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        try {
//            return formatMyJson(jsonstr);
//        } catch (Exception e) {
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<MovieTrailer> movieTrailers) {
//        myCallBack.onPostExcuteCallBack(movieTrailers);
//    }
//}
