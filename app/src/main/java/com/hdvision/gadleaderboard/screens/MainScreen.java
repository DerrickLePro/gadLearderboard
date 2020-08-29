package com.hdvision.gadleaderboard.screens;

import com.hdvision.gadleaderboard.model.Learner;

import java.util.List;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public interface MainScreen {
    void updateData(List<Learner> data);
    void setError(String msg);
}
