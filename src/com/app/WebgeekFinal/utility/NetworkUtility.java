package com.app.WebgeekFinal.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kdimla on 8/16/14.
 */
public class NetworkUtility {
    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager = null;
        NetworkInfo networkInfo = null;

        if (context != null) {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
