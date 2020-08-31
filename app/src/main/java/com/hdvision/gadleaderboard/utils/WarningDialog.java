package com.hdvision.gadleaderboard.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.hdvision.gadleaderboard.R;

import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * Created by derrick.kaffo on 30/08/2020.
 * kaffoderrick@gmail.com
 */
public class WarningDialog extends DialogFragment {

    private static final String TAG = "WarningDialog";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_warning, container, false);

        return view;
    }

    /**
     * Return true if the @param is null
     *
     * @param string
     * @return
     */
    private boolean isEmpty(String string) {
        return string.equals("");
    }
}
