package com.hdvision.gadleaderboard.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.repository.LearnerRepository;
import com.hdvision.gadleaderboard.repository.LearnerRepositoryImpl;

import java.util.List;

public class LearnerSkillIQViewModel extends ViewModel {

    private static final String TAG = LearnerSkillIQViewModel.class.getSimpleName();
    private LearnerRepository mLearnerRepository;

    public LearnerSkillIQViewModel() {
        mLearnerRepository = LearnerRepositoryImpl.create();
    }


    public LiveData<List<Learner>> getLearnerSkillIQData() {
        return mLearnerRepository.getLearnerSkillIQData();
    }

    public LiveData<String> getErrorUpdates() {
        return mLearnerRepository.getErrorStream();
    }

    public void fetchData() {
        mLearnerRepository.fetchDataSkill();
    }


    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared() called");
        super.onCleared();
    }

}