package com.example.app_choferes.contracts;

import android.support.v4.app.Fragment;

import com.example.app_choferes.presenter.BasePresenter;

public interface LoginFragmentContract {

    interface View extends BaseView {
        void switchFragment(Fragment fragment, Boolean addToBackStack);
    }

    interface Presenter extends BasePresenter {
        void doLogin(String waiterCode);
        void doLogout();
        boolean validateUserLogin();
    }
}
