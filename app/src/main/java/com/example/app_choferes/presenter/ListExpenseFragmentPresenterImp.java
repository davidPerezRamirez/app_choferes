package com.example.app_choferes.presenter;

import com.example.app_choferes.contracts.ListExpenseFragmentContract;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.User;
import com.example.app_choferes.service.QueriesRestAPIService;
import com.example.app_choferes.service.RetrofitService;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListExpenseFragmentPresenterImp implements ListExpenseFragmentContract.Presenter {

    private QueriesRestAPIService appService;
    private RetrofitService retrofitService;
    private WeakReference<ListExpenseFragmentContract.View> listExpenseView;

    public ListExpenseFragmentPresenterImp(ListExpenseFragmentContract.View listExpenseFragment) {
        this.listExpenseView = new WeakReference<>(listExpenseFragment);
        this.retrofitService = new RetrofitService();
        appService = retrofitService.connectToHerokuApp();
    }

    private ListExpenseFragmentContract.View getListExpenseView() {
        return listExpenseView.get();
    }

    @Override
    public void doUnsubscribe() {

    }

    @Override
    public void loadListExpensesFormCurrentUser() {
        getListExpenseView().showProgressBar();
        final User currentUser = getListExpenseView().getCurrentUser();
        Call<List<Expense>> users = appService.getExpensesUser(currentUser.getId());

        users.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                getListExpenseView().initializeRecyclerListExpense(response.body());
                getListExpenseView().hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                getListExpenseView().hideProgressBar();
                getListExpenseView().showTemporalMsg("No pudieron cargarse los gastos de usuario " + currentUser.getName());
            }
        });
    }
}
