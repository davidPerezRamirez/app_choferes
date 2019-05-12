package com.example.app_choferes.service;

import com.example.app_choferes.constants.ConstantRestApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    public QueriesRestAPIService connectToHerokuApp() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantRestApi.ROOT_URL_API_REST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(QueriesRestAPIService.class);
    }
}
