package com.hdvision.gadleaderboard.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.InetAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by derrick.kaffo on 06/09/2020.
 * kaffoderrick@gmail.com
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private Context mContext;

    public NetworkUtils(Context context) {

        mContext = context;
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean result = false;
        if (activeNetwork != null) {
            result = activeNetwork.isConnected();
        }

        return result;
    }

    public boolean isInternetAvailable(long timeOut) {
        if (isConnected()) {
            InetAddress inetAddress = null;
            try {
                Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(() -> {
                    try {
                        return InetAddress.getByName("www.google.com");
                    } catch (Exception e) {
                        Log.d(TAG, e.getMessage());
                        return null;
                    }
                });
                inetAddress = future.get(timeOut, TimeUnit.MILLISECONDS);
                future.cancel(true);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Log.d(TAG, e.getMessage());
            }

            return inetAddress != null && !inetAddress.equals("");
        } else {
            return false;
        }
    }
}
