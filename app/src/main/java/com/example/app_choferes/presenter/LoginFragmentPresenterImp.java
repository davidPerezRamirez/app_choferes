package com.example.app_choferes.presenter;

import com.example.app_choferes.contracts.LoginFragmentContract;
import com.example.app_choferes.models.User;
import com.example.app_choferes.service.QueriesRestAPIService;
import com.example.app_choferes.service.RetrofitService;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragmentPresenterImp implements LoginFragmentContract.Presenter {

    private QueriesRestAPIService appService;
    private RetrofitService retrofitService;
    private WeakReference<LoginFragmentContract.View> loginView;

    public LoginFragmentPresenterImp(LoginFragmentContract.View loginFragment) {
        this.loginView = new WeakReference<>(loginFragment);
        this.retrofitService = new RetrofitService();
        appService = retrofitService.connectToHerokuApp();
    }

    private LoginFragmentContract.View getLoginFragment() {
        return this.loginView.get();
    }

    @Override
    public void doLogin(String waiterCode) {

    }

    @Override
    public void doLogout() {

    }

    @Override
    public boolean validateUserLogin() {
        return false;
    }

    @Override
    public void doUnsubscribe() {

    }

    @Override
    public void getUsers() {
        getLoginFragment().showProgressBar();
        Call<List<User>> users = appService.userList();
        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                getLoginFragment().loadUsers(response.body());
                getLoginFragment().hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                getLoginFragment().showFailMsg("No pudieron cargarse los usuarios");
            }
        });
    }
}
