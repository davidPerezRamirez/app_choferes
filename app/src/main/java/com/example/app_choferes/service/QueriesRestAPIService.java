package com.example.app_choferes.service;

import com.example.app_choferes.constants.ConstantRestApi;
import com.example.app_choferes.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueriesRestAPIService {

    @GET(ConstantRestApi.GET_USERS)
    Call<List<User>> userList();

}
