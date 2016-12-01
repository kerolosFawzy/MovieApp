package com.massive.movieapp.webservices;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.massive.movieapp.BuildConfig;
import com.massive.movieapp.ICallBack;
import com.massive.movieapp.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WebServiceCall<T extends Object> extends AsyncTask<String, Object, ArrayList<T>> {

    private Context mContext;
    ICallBack myCallBack;
    ArrayList<T> resultObj;
    Type type;

    public WebServiceCall(ICallBack myCallBack, Context mContext , Type type  ) {
        this.myCallBack = myCallBack;
        this.mContext = mContext;
        this.type = type;
    }

    private ArrayList<T> formatMyJson(String jsonstr) throws JSONException {
        JSONObject object = new JSONObject( jsonstr );
        JSONArray mylist = object.getJSONArray( "results" );
        resultObj =  new Gson().fromJson( mylist.toString(), type );
        return resultObj;
    }

    @Override
    protected ArrayList<T> doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;
        String jsonstr = null;
        try {
            Uri builtUri = Uri.parse( params[0] ).buildUpon().appendQueryParameter( Constants.API, BuildConfig.APIKRY ).build();
            URL uRl = new URL( builtUri.toString() );
            httpURLConnection = (HttpURLConnection) uRl.openConnection();
            httpURLConnection.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader( httpURLConnection.getInputStream() ) );
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append( inputLine );
            }
            in.close();
            jsonstr = response.toString();
        } catch (Exception e) {
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
            return formatMyJson( jsonstr );
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<T> MyArrayList) {
        myCallBack.onPostExcuteCallBack( MyArrayList );
    }
}
