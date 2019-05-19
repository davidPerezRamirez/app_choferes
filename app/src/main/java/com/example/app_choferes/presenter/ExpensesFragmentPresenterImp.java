package com.example.app_choferes.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.example.app_choferes.contracts.ExpensesFragmentContract;
import com.example.app_choferes.models.ExpenseType;
import com.example.app_choferes.service.QueriesRestAPIService;
import com.example.app_choferes.service.RetrofitService;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.app_choferes.constants.AppConstants.TAKE_PHOTO;

public class ExpensesFragmentPresenterImp implements ExpensesFragmentContract.Presenter {

    private WeakReference<ExpensesFragmentContract.View> expensesView;
    private QueriesRestAPIService appService;
    private RetrofitService retrofitService;

    public ExpensesFragmentPresenterImp(ExpensesFragmentContract.View expensesFragment) {
        this.expensesView = new WeakReference<>(expensesFragment);
        this.retrofitService = new RetrofitService();
        this.appService = retrofitService.connectToHerokuApp();
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
    public void saveExpense(String description, int idTypeExpense, Double amount, Bitmap capturedImage) {
        /*TODO: guardar datos en base de datos*/
    }

    @Override
    public void loadExpenseTypes() {
        getExpensesView().showProgressBar();
        Call<List<ExpenseType>> users = appService.getExpenseTypes();
        users.enqueue(new Callback<List<ExpenseType>>() {
            @Override
            public void onResponse(Call<List<ExpenseType>> call, Response<List<ExpenseType>> response) {
                getExpensesView().hideProgressBar();
                getExpensesView().loadExpenseTypes(response.body());
            }

            @Override
            public void onFailure(Call<List<ExpenseType>> call, Throwable t) {
                getExpensesView().hideProgressBar();
                getExpensesView().showTemporalMsg("Ocurrió un error al obtener los tipos de gastos.");
            }
        });
    }

    @Override
    public void doUnsubscribe() {

    }
}
