package com.hdvision.gadleaderboard.repository;

import androidx.lifecycle.LiveData;

import com.hdvision.gadleaderboard.model.Learner;

import java.util.List;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public interface LearnerRepository {

    LiveData<List<Learner>> getLearnerHoursData();

    LiveData<List<Learner>> getLearnerSkillIQData();

    LiveData<String> getErrorStream();

    void fetchData();

    void fetchDataSkill();
}
