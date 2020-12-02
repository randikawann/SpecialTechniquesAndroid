package com.rancreation.specialtechniquesandroid.viewmodel;

import android.util.Log;

import com.rancreation.specialtechniquesandroid.remote.RetrofitClient;
import com.rancreation.specialtechniquesandroid.remote.UserService;

public class UserViewModel {

//    private String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static UserService getusers(String BASE_URL){

        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }



}
