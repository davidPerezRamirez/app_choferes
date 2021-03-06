package com.example.app_choferes.contracts;

import android.content.Intent;
import android.graphics.Bitmap;

import com.example.app_choferes.models.ExpenseType;
import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.BasePresenter;

import java.util.List;

public interface ExpensesFragmentContract {

    interface View extends BaseView {

        void startActivityForResult(Intent intent, int codeAction);

        void loadExpenseTypes(List<ExpenseType> expenseTypes);

        void showTemporalMsg(String msg);

        User getCurrentUser();
    }

    interface Presenter extends BasePresenter {

        void openCamera();

        void openGallery();

        String getPathImage();

        void saveNewExpense(String description, int idTypeExpense, Double amount, Bitmap capturedImage);

        void loadExpenseTypes();
    }
}
