package com.example.app_choferes.service;

import com.example.app_choferes.constants.ConstantRestApi;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface QueriesRestAPIService {

    @GET(ConstantRestApi.GET_USERS)
    Call<List<User>> userList();

    @FormUrlEncoded
    @GET(ConstantRestApi.LIST_EXPENSES_CURRENT_USER)
    Call<List<Expense>> listExpensesFromCurrentUser(@Field("idUser") int idUser);

    @FormUrlEncoded
    @POST(ConstantRestApi.VALIDATE_USER)
    Call<Map<String, String>> validateUser(@Field("idUser") int idUser, @Field("password") String pass);

}
