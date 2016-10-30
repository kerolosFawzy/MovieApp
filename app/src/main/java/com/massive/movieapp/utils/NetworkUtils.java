package com.massive.movieapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Minafaw on 30/10/2016.
 */

public class NetworkUtils {

    /**
     * this method to check connection with internet
     * @params context
     * return true :if connected , false otherwise
     */

    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
