package com.example.app_choferes.ui.fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.app_choferes.R;
import com.example.app_choferes.adapters.DriverListAdapter;
import com.example.app_choferes.contracts.LoginFragmentContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.models.Driver;
import com.example.app_choferes.presenter.LoginFragmentPresenterImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends BaseFragment<LoginFragmentContract.Presenter> implements OnBackPressedListener {

    @BindView(R.id.clContainer)
    CoordinatorLayout clContainer;
    @BindView(R.id.spDriverList)
    Spinner spDriverList;

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
        List<Driver> drivers = new ArrayList<>();

        drivers.add(new Driver("David"));
        drivers.add(new Driver("Pedro"));
        drivers.add(new Driver("Lucas"));
        drivers.add(new Driver("Jorge"));

        spDriverList.setAdapter(new DriverListAdapter(this.getMainActivity(), R.layout.driver_item_list, drivers));
    }

    @Override
    public boolean doBack() {
        showExitDialog();
        return false;
    }
}
