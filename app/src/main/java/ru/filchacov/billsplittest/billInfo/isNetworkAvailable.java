package ru.filchacov.billsplittest.billInfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class isNetworkAvailable {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.d("NetworkCheck", "isNetworkAvailable: No");
            return false;
        }

        // get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
        NetworkInfo[] info = connectivity.getAllNetworkInfo();

        // make sure that there is at least one interface to test against
        // iterate through the interfaces
        for (NetworkInfo networkInfo : info) {
            // check this interface for a connected state
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                Log.d("NetworkCheck", "isNetworkAvailable: Yes");
                return true;
            }
        }
        return false;
    }
}