package com.hdvision.gadleaderboard.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.hdvision.gadleaderboard.R;
import com.hdvision.gadleaderboard.model.SubmitBean;
import com.hdvision.gadleaderboard.repository.datasource.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ConfirmationDialog extends DialogFragment {

    private static final String TAG = "ConfirmationDialog";
    private static final String BASE_URL = "https://docs.google.com/forms/d/e/";


    //widgets
    private Button mConfirm;
    private SubmitBean mBean;
    private ImageView mClose;
    private FragmentManager mFragmentManager;
    private NetworkUtils mNetworkUtils;
    private ProgressBar mProgressBar;

    public ConfirmationDialog(FragmentManager supportFragmentManager) {
        mFragmentManager = supportFragmentManager;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        mBean = getArguments().getParcelable(getString(R.string.field_bean));
        if (mBean != null) {
            Log.d(TAG, "onCreate: submit bean: " + mBean);
        }

        mNetworkUtils = new NetworkUtils(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirmation, container, false);
        mConfirm = view.findViewById(R.id.btn_confirm);
        mClose = view.findViewById(R.id.imageView2);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mClose.setOnClickListener(view1 -> dismiss());

        mConfirm.setOnClickListener(v -> {

                mProgressBar.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService service = retrofit.create(ApiService.class);
            Log.d(TAG, "onCreateView: " + mBean.toString());

            if (mNetworkUtils.isInternetAvailable(1000)) {
                Log.d(TAG, "isConnected");
                Call<ResponseBody> call = service.submitProject(mBean.getFirstName(), mBean.getLastName(), mBean.getBusinessEmail(), mBean.getProjectLink());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d(TAG, "onResponse: " + (response.body() != null ? response.body().toString() : "null response"));
                        if (response.isSuccessful()) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "onResponse: response: " + response.message());
                            SuccessDialog dialog = new SuccessDialog();
                            dialog.show(mFragmentManager, getString(R.string.dialog_success));
                            dismiss();

                        } else {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "onResponse: error submit: " + response.message());
                            showWarningDialog("WarningDialog: error attached context");
                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onFailure: error message: " + t.getMessage());
                        showWarningDialog("onFailure: error ConfirmationDialog not attached to a context");
                    }

                });

            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                showWarningDialog("No internet");
            }
        });

        return view;
    }

    private void showWarningDialog(String message) {
        Log.d(TAG, message);
        WarningDialog dialog = new WarningDialog();
        try {
            dialog.show(mFragmentManager, getString(R.string.dialog_warning_submit));
            dismiss();
        } catch (IllegalStateException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
