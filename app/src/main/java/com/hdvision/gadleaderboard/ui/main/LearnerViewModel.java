package com.hdvision.gadleaderboard.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.repository.LearnerRepository;
import com.hdvision.gadleaderboard.repository.LearnerRepositoryImpl;

import java.util.List;

public class LearnerViewModel extends ViewModel {

    private static final String TAG = LearnerViewModel.class.getSimpleName();
    private LearnerRepository mLearnerRepository;

    public LearnerViewModel() {
        mLearnerRepository = LearnerRepositoryImpl.create();
    }

    public LiveData<List<Learner>> getLearnerHoursData() {
        return mLearnerRepository.getLearnerHoursData();
    }


    public LiveData<String> getErrorUpdates() {
        return mLearnerRepository.getErrorStream();
    }

    public void fetchData() {
        mLearnerRepository.fetchData();
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }
}