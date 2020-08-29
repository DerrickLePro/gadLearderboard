package com.hdvision.gadleaderboard.repository.datasource;

import androidx.lifecycle.LiveData;

import com.hdvision.gadleaderboard.model.Learner;

import java.util.List;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public interface DataSource<T> {

    LiveData<T> getDataStreamLeanerHours();

    LiveData<T> getDataStreamLeanerSkillIQ();

    LiveData<String> getErrorStream();
}
