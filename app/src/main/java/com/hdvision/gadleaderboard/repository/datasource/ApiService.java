package com.hdvision.gadleaderboard.repository.datasource;

import com.hdvision.gadleaderboard.model.Learner;
import com.hdvision.gadleaderboard.model.SubmitBean;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by derrick.kaffo on 29/08/2020.
 * kaffoderrick@gmail.com
 */
public interface ApiService {

    @GET("hours")
    Call<List<Learner>> learnerHours();

    @GET("skilliq")
    Call<List<Learner>> learnerSkillIQ();

    @FormUrlEncoded
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    Call<ResponseBody> submitProject(
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String businessEmail,
            @Field("entry.284483984") String projectLink

    );

}
