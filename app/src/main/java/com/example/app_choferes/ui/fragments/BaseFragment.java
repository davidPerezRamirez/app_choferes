package com.example.app_choferes.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.app_choferes.R;
import com.example.app_choferes.contracts.BaseView;
import com.example.app_choferes.listeners.DialogYesNoListener;
import com.example.app_choferes.presenter.BasePresenter;
import com.example.app_choferes.ui.activities.MainActivity;
import com.example.app_choferes.utils.DialogFactory;
import com.example.app_choferes.utils.FeedbackMessageDisplay;
import com.example.app_choferes.utils.SwitcherFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected T presenter;
    protected MainActivity faActivity;
    protected ProgressDialog progressIndicator;
    protected Unbinder unbinder;

    private SwitcherFragment switcherFragment;
    private FeedbackMessageDisplay feedbackMessageDisplay;

    protected abstract T createPresenter();

    public T getPresenter() {
        return presenter;
    }

    protected boolean setLogoVisible() {
        return true;
    }

    @Override
    public MainActivity getMainActivity() {
        return faActivity;
    }

    public void setUnbinder(BaseFragment fragment, View view) {
        this.unbinder = ButterKnife.bind(fragment, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faActivity = (MainActivity) super.getActivity();
        presenter = createPresenter();
        unbinder = null;
        switcherFragment = new SwitcherFragment(faActivity.getSupportFragmentManager());
        feedbackMessageDisplay = faActivity.getFeedbackMessageDisplay();
    }

    @Override
    public void showProgressIndicator(@StringRes int messageResource) {
        if (progressIndicator == null) {
            progressIndicator = DialogFactory.createProgressDialog(faActivity, messageResource);
        } else {
            progressIndicator.setMessage(faActivity.getString(messageResource));
        }
        progressIndicator.show();
    }

    @Override
    public void setActionBarTitle(String title) {
        //faActivity.setActionBarTitle(title);
    }

    @Override
    public void setActionBarTitle(@StringRes int titleStringId) {
        //faActivity.setActionBarTitle(titleStringId);
    }

    @Override
    public void showKeyboard() {
        InputMethodManager im = (InputMethodManager) faActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(0, 0);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager im = (InputMethodManager) faActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

        im.hideSoftInputFromWindow(faActivity.getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void dismissProgressIndicator() {
        progressIndicator.dismiss();
    }

    @Override
    public void showExitDialog() {
        DialogFactory.showExitDialog(faActivity);
    }

    @Override
    public void switchFragment(Fragment fragment, Boolean addToBackStack) {
        feedbackMessageDisplay.dimissDisplay();
        switcherFragment.switchFragment(fragment, addToBackStack, fragment.getClass().getSimpleName());
    }

    public void removeFragment() {
        switcherFragment.removeFragment(this);
    }

    @Override
    public void reloadFragment(Fragment fragment) {
        feedbackMessageDisplay.dimissDisplay();
        switcherFragment.reloadFragment(fragment);
    }

    @Override
    public void reloadActualFragment(List<Fragment> fragments) {
        faActivity.reloadActualFragment(fragments);
    }


    @Override
    public void switchFragmentBack() {
        feedbackMessageDisplay.dimissDisplay();
        switcherFragment.switchFragmentBack();
    }

    public void removeCurrentAndSwitchFragment(Fragment frag) {
        feedbackMessageDisplay.dimissDisplay();
        switcherFragment.removeCurrentAndSwitchFragment(frag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (progressIndicator != null) {
            progressIndicator.dismiss();
        }
        if (presenter != null) {
            presenter.doUnsubscribe();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        hideProgressBar();
    }

    @Override
    public void showProgressBar() {
        View progressBarContainer = faActivity.getProgressBarContainer();

        if (progressBarContainer != null && progressBarContainer.getVisibility() == View.GONE) {
            progressBarContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        View progressBarContainer = faActivity.getProgressBarContainer();

        if (progressBarContainer != null && progressBarContainer.getVisibility() == View.VISIBLE) {
            progressBarContainer.setVisibility(View.GONE);
        }
    }

}
