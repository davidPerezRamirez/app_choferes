package com.example.app_choferes.contracts;

import android.content.Intent;
import android.graphics.Bitmap;

import com.example.app_choferes.presenter.BasePresenter;

public interface ExpensesFragmentContract {

    interface View extends BaseView {

        void startActivityForResult(Intent intent, int codeAction);
    }

    interface Presenter extends BasePresenter {

        void openCamera();

        void saveExpense(String description, String typeExpense, Double amount, Bitmap capturedImage);
    }
}
