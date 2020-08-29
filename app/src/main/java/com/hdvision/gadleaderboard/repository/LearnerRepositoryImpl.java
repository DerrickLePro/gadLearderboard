package com.hdvision.gadleaderboard.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.repository.datasource.RemoteDataSource;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public class LearnerRepositoryImpl implements LearnerRepository {

    private static final String TAG = LearnerRepositoryImpl.class.getSimpleName();

    private ExecutorService mExecutor = Executors.newFixedThreadPool(5);

    private final MediatorLiveData<List<Learner>> mDataLearnerHoursMerger = new MediatorLiveData<>();
    private final MediatorLiveData<List<Learner>> mDataLearnerSkillIQMerger = new MediatorLiveData<>();
    MediatorLiveData<String> mErrorMerger = new MediatorLiveData<>();
    private final RemoteDataSource mRemoteDataSource;

    private LearnerRepositoryImpl(RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;

        mDataLearnerHoursMerger.addSource(this.mRemoteDataSource.getDataStreamLeanerHours(), entities ->
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "list Learner Hours");
                        List<Learner> list = entities;
                        mDataLearnerHoursMerger.postValue(list);
                    }
                })
        );
        mDataLearnerSkillIQMerger.addSource(this.mRemoteDataSource.getDataStreamLeanerHours(), entities ->
                mExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "list Learner Hours");
                        List<Learner> list = entities;
                        mDataLearnerSkillIQMerger.postValue(list);
                    }
                })
        );

        mErrorMerger.addSource(mRemoteDataSource.getErrorStream(), errorStr -> {
            mErrorMerger.setValue(errorStr);
        });
    }

    public static LearnerRepository create() {
        final RemoteDataSource source = new RemoteDataSource();
        return  new LearnerRepositoryImpl(source);
    }

    @Override
    public LiveData<List<Learner>> getLearnerHoursData() {

        return mDataLearnerHoursMerger;
    }

    @Override
    public LiveData<List<Learner>> getLearnerSkillIQData() {
        return mDataLearnerSkillIQMerger;
    }

    @Override
    public LiveData<String> getErrorStream() {
        return mErrorMerger;
    }

    @Override
    public void fetchData() {
        mRemoteDataSource.fetchDataLearnerHours();
        mRemoteDataSource.fetchDataLearnerSkillIQ();
    }


}
