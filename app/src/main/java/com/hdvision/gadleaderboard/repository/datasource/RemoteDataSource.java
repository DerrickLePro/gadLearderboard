package com.hdvision.gadleaderboard.repository.datasource;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hdvision.gadleaderboard.model.Learner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public class RemoteDataSource implements DataSource<List<Learner>> {

    private static final String  TAG = RemoteDataSource.class.getSimpleName();
    private static final String BASE_URL = "https://gadsapi.herokuapp.com/api/";
    private final Retrofit mRetrofit;
    private final ApiService mService;
    private final MutableLiveData<List<Learner>> mDataLearnerHours = new MutableLiveData<>();
    private final MutableLiveData<List<Learner>> mDataLearnerSkillIQ = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();

    public RemoteDataSource() {
        Gson gson = new GsonBuilder().create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mService = mRetrofit.create(ApiService.class);
    }

    @Override
    public LiveData<List<Learner>> getDataStreamLeanerHours() {
        return mDataLearnerHours;
    }

    @Override
    public LiveData<List<Learner>> getDataStreamLeanerSkillIQ() {
        return mDataLearnerSkillIQ;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mError;
    }

    public void fetchDataLearnerHours() {

        Call<List<Learner>> resp = mService.learnerHours();

        mService.learnerHours().enqueue(new Callback<List<Learner>>() {
                                            @Override
                                            public void onResponse(Call<List<Learner>> call, Response<List<Learner>> response) {
                                                Log.d(TAG, "response learner hours: "+response.toString());
                                                final List<Learner> data = response.body();
                                                mDataLearnerHours.postValue(data);
                                            }

                                            @Override
                                            public void onFailure(Call<List<Learner>> call, Throwable t) {
                                                mError.setValue(t.getMessage());
                                            }
                                        }
        );

    }

    public void fetchDataLearnerSkillIQ() {
        mService.learnerSkillIQ().enqueue(new Callback<List<Learner>>() {
                                            @Override
                                            public void onResponse(Call<List<Learner>> call, Response<List<Learner>> response) {
                                                Log.d(TAG, "response learner skill IQ: "+response.toString());
                                                final List<Learner> data = response.body();
                                                mDataLearnerSkillIQ.postValue(data);
                                            }

                                            @Override
                                            public void onFailure(Call<List<Learner>> call, Throwable t) {
                                                mError.setValue(t.getMessage());
                                            }
                                        }
        );

    }
}
