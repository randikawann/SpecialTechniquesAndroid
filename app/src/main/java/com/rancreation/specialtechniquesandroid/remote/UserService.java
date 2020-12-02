package com.rancreation.specialtechniquesandroid.remote;

import com.rancreation.specialtechniquesandroid.model.Feed;
import com.rancreation.specialtechniquesandroid.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    @GET("users")
    Call<List<User>> getAPIusers();

}
