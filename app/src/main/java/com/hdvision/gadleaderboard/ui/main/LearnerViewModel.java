package com.hdvision.gadleaderboard.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.repository.LearnerRepository;
import com.hdvision.gadleaderboard.repository.LearnerRepositoryImpl;

import java.util.List;

public class LearnerViewModel extends ViewModel {

    private static final String TAG = LearnerViewModel.class.getSimpleName();
    private LearnerRepository mLearnerRepository;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    public LearnerViewModel() {
        mLearnerRepository = LearnerRepositoryImpl.create();
    }


    //    private LiveData<Learner> mText = Transformations.map(mIndex, new Function<Integer, String>() {
//        @Override
//        public String apply(Integer input) {
//            return "Hello world from section: " + input;
//        }
//    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<List<Learner>> getLearnerHoursData() {
        return mLearnerRepository.getLearnerHoursData();
    }

    public LiveData<List<Learner>> getLearnerSkillIQData() {
        return mLearnerRepository.getLearnerSkillIQData();
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
//    public LiveData<String> getText() {
//        return mText;
//    }
}