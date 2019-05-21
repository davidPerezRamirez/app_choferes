package com.example.app_choferes.service;

import com.example.app_choferes.constants.ConstantRestApi;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.ExpenseType;
import com.example.app_choferes.models.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface QueriesRestAPIService {

    @GET(ConstantRestApi.GET_USERS)
    Call<List<User>> userList();

    @GET(ConstantRestApi.GET_EXPENSE_TYPES)
    Call<List<ExpenseType>> getExpenseTypes();

    @FormUrlEncoded
    @POST(ConstantRestApi.GET_EXPENSES_USER)
    Call<List<Expense>> getExpensesUser(@Field("idUser") int idUser);

    @FormUrlEncoded
    @POST(ConstantRestApi.VALIDATE_USER)
    Call<Map<String, String>> validateUser(@Field("idUser") int idUser, @Field("password") String pass);

    @Multipart
    @POST(ConstantRestApi.SAVE_IMAGE)
    Call<ResponseBody> saveImage(@Part MultipartBody.Part file, @Part("image") RequestBody requestBody);

}
