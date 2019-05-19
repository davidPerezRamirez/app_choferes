package com.example.app_choferes.contracts;

import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.BasePresenter;

import java.util.List;

public interface ListExpenseFragmentContract {

    interface View extends BaseView {

        void initializeRecyclerListExpense(List<Expense> expenses);

        User getCurrentUser();
    }

    interface Presenter extends BasePresenter {

        void loadListExpensesFormCurrentUser();
    }
}
