package com.example.app_choferes.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.app_choferes.R;
import com.example.app_choferes.adapters.UserListAdapter;
import com.example.app_choferes.contracts.LoginFragmentContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.LoginFragmentPresenterImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment<LoginFragmentContract.Presenter> implements OnBackPressedListener, LoginFragmentContract.View {

    @BindView(R.id.clContainer)
    CoordinatorLayout clContainer;
    @BindView(R.id.spDriverList)
    Spinner spDriverList;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @OnClick(R.id.btn_accept)
    public void onClickBtnAccept() {
        String pass = etPassword.getText().toString();

        getPresenter().validateUserLogin(pass);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected LoginFragmentContract.Presenter createPresenter() {
        return new LoginFragmentPresenterImp(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clContainer = (CoordinatorLayout) inflater.inflate(R.layout.login_fragment, container, false);
        unbinder = ButterKnife.bind(this, clContainer);
        faActivity.setOnBackPressedListener(this);

        this.initializeSpinnerDriver();
        presenter.doLogout();

        return clContainer;
    }

    private void initializeSpinnerDriver() {
        getPresenter().getUsers();
    }

    @Override
    public void loadUsers(List<User> users) {
        spDriverList.setAdapter(new UserListAdapter(this.getMainActivity(), R.layout.spinner_item_list, users));
    }

    @Override
    public boolean doBack() {
        showExitDialog();
        return false;
    }

    @Override
    public User getCurrentUser() {
        return (User) spDriverList.getSelectedItem();
    }

    @Override
    public void navigateToListExpenseFragment() {
        ListExpensesFragment fragment = ListExpensesFragment.newInstance(getCurrentUser());
        switchFragment(fragment, true);
    }
}
