package com.hdvision.gadleaderboard.repository.datasource;

import com.hdvision.gadleaderboard.model.Learner;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public interface ApiService {

    @GET("hours")
    Call<List<Learner>> learnerHours();

    @GET("skilliq")
    Call<List<Learner>> learnerSkillIQ();
}
