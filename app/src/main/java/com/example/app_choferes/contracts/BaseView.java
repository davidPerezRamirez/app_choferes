package com.example.app_choferes.contracts;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.example.app_choferes.ui.activities.MainActivity;

import java.util.List;

public interface BaseView {

    void showProgressIndicator(@StringRes int messageResource);

    void showKeyboard();

    void hideKeyboard();

    MainActivity getMainActivity();

    void dismissProgressIndicator();

    void showExitDialog();

    void switchFragment(Fragment fragment, Boolean addToBackStack);

    void switchFragmentBack();

    void reloadFragment(Fragment fragment);

    void reloadActualFragment(List<Fragment> fragments);

    void setActionBarTitle(@StringRes int titleStringId);

    void setActionBarTitle(String title);

    void showProgressBar();

    void hideProgressBar();

}
