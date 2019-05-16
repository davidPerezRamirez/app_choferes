package com.example.app_choferes.service;

import com.example.app_choferes.constants.ConstantRestApi;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface QueriesRestAPIService {

    @GET(ConstantRestApi.GET_USERS)
    Call<List<User>> userList();

    @FormUrlEncoded
    @GET(ConstantRestApi.LIST_EXPENSES_CURRENT_USER)
    Call<List<Expense>> listExpensesFromCurrentUser(@Field("idUser")int idUser);

}
