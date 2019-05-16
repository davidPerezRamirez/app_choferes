package com.example.app_choferes.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_choferes.R;
import com.example.app_choferes.adapters.ListExpenseAdapter;
import com.example.app_choferes.contracts.ListExpenseFragmentContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.ListExpenseFragmentPresenterImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListExpensesFragment extends BaseFragment<ListExpenseFragmentContract.Presenter>
        implements ListExpenseFragmentContract.View, OnBackPressedListener {

    private User currentUser;

    @BindView(R.id.clListContainer)
    CoordinatorLayout clListContainer;
    @BindView(R.id.rvListExpenses)
    RecyclerView rvListExpenses;
    @OnClick(R.id.btn_add)
    public void onClickBtnAdd() {
        ExpensesFragment fragment = new ExpensesFragment();
        switchFragment(fragment, true);
    }

    public static ListExpensesFragment newInstance(User currentUser) {
        ListExpensesFragment fragment = new ListExpensesFragment();

        fragment.setCurrentUser(currentUser);

        return fragment;
    }

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    protected ListExpenseFragmentContract.Presenter createPresenter() {
        return new ListExpenseFragmentPresenterImp(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clListContainer = (CoordinatorLayout) inflater.inflate(R.layout.list_expenses_fragment, container, false);
        unbinder = ButterKnife.bind(this, clListContainer);

        faActivity.setOnBackPressedListener(this);
        this.getPresenter().loadListExpensesFormCurrentUser();

        return clListContainer;
    }

    @Override
    public void initializeRecyclerListExpense(List<Expense> expenses) {
        LinearLayoutManager llm = new LinearLayoutManager(this.getMainActivity());
        ListExpenseAdapter listExpenseAdapter = new ListExpenseAdapter(expenses);

        rvListExpenses.setLayoutManager(llm);
        rvListExpenses.setAdapter(listExpenseAdapter);
    }

    @Override
    public void showFailMsg(String msg) {
        getMainActivity().showTemporalMessage(msg);
    }

    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    @Override
    public boolean doBack() {
        this.switchFragmentBack();
        return false;
    }

}
