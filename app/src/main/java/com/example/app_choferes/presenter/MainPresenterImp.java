package com.example.app_choferes.presenter;

import com.example.app_choferes.contracts.MainActivityContract;

import java.lang.ref.WeakReference;

public class MainPresenterImp implements MainActivityContract.Presenter {

    private WeakReference<MainActivityContract.View> mainView;

    public MainPresenterImp(MainActivityContract.View mainActivity) {
        this.mainView = new WeakReference<>(mainActivity);
    }

    public MainActivityContract.View getMainView() {
        return mainView.get();
    }

    @Override
    public void doUnsubscribe() {

    }
}
