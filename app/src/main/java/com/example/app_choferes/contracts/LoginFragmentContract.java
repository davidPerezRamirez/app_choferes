package com.example.app_choferes.contracts;

import android.support.v4.app.Fragment;

import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.BasePresenter;

import java.util.List;

public interface LoginFragmentContract {

    interface View extends BaseView {

        void loadUsers(List<User> users);

        void showTemporalMsg(String msg);

        void switchFragment(Fragment fragment, Boolean addToBackStack);

        User getCurrentUser();

        void navigateToListExpenseFragment();
    }

    interface Presenter extends BasePresenter {

        void doLogin(String waiterCode);

        void doLogout();

        void validateUserLogin(String password);

        void getUsers();
    }
}
