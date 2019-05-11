package com.example.app_choferes.presenter;

import com.example.app_choferes.contracts.LoginFragmentContract;
import com.example.app_choferes.ui.fragments.LoginFragment;

public class LoginFragmentPresenterImp implements LoginFragmentContract.Presenter {

    public LoginFragmentPresenterImp(LoginFragment loginFragment) {
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
}
