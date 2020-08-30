package com.hdvision.gadleaderboard.utils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        mBean = getArguments().getParcelable(getString(R.string.field_bean));
        if (mBean != null) {
            Log.d(TAG, "onCreate: submit bean: " + mBean);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirmation, container, false);
        mConfirm = view.findViewById(R.id.btn_confirm);
        mClose = view.findViewById(R.id.imageView2);

        mClose.setOnClickListener(view1 -> dismiss());

        mConfirm.setOnClickListener(v -> {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService service = retrofit.create(ApiService.class);
            //Todo:: for test
            mBean = new SubmitBean();
            Call<ResponseBody> call = service.submitProject(mBean.getFirstName(), mBean.getLastName(), mBean.getBusinessEmail(), mBean.getProjectLink());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    SuccessDialog dialog = new SuccessDialog();
                    dialog.show(getParentFragmentManager(), getString(R.string.dialog_success));
                    dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "onFailure: error message: " + t.getMessage());
                    WarningDialog dialog = new WarningDialog();
                    dialog.show(getParentFragmentManager(), getString(R.string.dialog_warning_submit));
                    dismiss();

                }
            });
        });

        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
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

















