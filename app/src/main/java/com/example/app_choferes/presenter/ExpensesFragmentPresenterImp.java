package com.example.app_choferes.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.app_choferes.contracts.ExpensesFragmentContract;

import java.io.File;
import java.lang.ref.WeakReference;

import static com.example.app_choferes.constants.AppConstants.TAKE_PHOTO;

public class ExpensesFragmentPresenterImp implements ExpensesFragmentContract.Presenter {

    private WeakReference<ExpensesFragmentContract.View> expensesView;

    public ExpensesFragmentPresenterImp(ExpensesFragmentContract.View expensesFragment) {
        this.expensesView = new WeakReference<>(expensesFragment);
    }

    private ExpensesFragmentContract.View getExpensesView() {
        return this.expensesView.get();
    }

    @Override
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getExpensesView().startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void saveExpense(String description, String typeExpense, Double amount, Bitmap capturedImage) {
        /*TODO: guardar datos en base de datos*/
    }

    @Override
    public void doUnsubscribe() {

    }
}
