package com.example.app_choferes.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_choferes.R;
import com.example.app_choferes.adapters.ListExpenseAdapter;
import com.example.app_choferes.contracts.ListExpenseFragmentContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.ListExpenseFragmentPresenterImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListExpensesFragment extends BaseFragment<ListExpenseFragmentContract.Presenter>
        implements ListExpenseFragmentContract.View, OnBackPressedListener, SwipeRefreshLayout.OnRefreshListener {

    private User currentUser;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvRemainingAmount)
    TextView tvRemainingAmount;
    @BindView(R.id.tvSpentAmount)
    TextView tvSpentAmount;
    @BindView(R.id.clListContainer)
    CoordinatorLayout clListContainer;
    @BindView(R.id.rvListExpenses)
    RecyclerView rvListExpenses;
    @BindView(R.id.srlContainer)
    SwipeRefreshLayout srlContainer;

    @OnClick(R.id.btn_add)
    public void onClickBtnAdd() {
        ExpensesFragment fragment = ExpensesFragment.newInstance(this.currentUser);
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
        this.initializeSwipeRefreshLayout();
        this.initializeActionBar();
        this.getPresenter().loadListExpensesFormCurrentUser();

        return clListContainer;
    }

    private void initializeSwipeRefreshLayout() {
        srlContainer.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        reloadFragment(this);
    }

    private void initializeActionBar() {
        getMainActivity().setSupportActionBar(toolbar);
    }

    @Override
    public void initializeRecyclerListExpense(List<Expense> expenses) {
        GridLayoutManager glm = new GridLayoutManager(this.getMainActivity(), 2);
        ListExpenseAdapter listExpenseAdapter = new ListExpenseAdapter(expenses, this.getMainActivity().getFragmentManager());

        rvListExpenses.setLayoutManager(glm);
        rvListExpenses.setAdapter(listExpenseAdapter);
    }

    @Override
    public void updateRemainingImport(Double totalSpentAmount) {
        User currentUser = getCurrentUser();

        tvSpentAmount.setText("$ " + totalSpentAmount.toString());
        tvRemainingAmount.setText("$ " + (currentUser.getImportTravel() - totalSpentAmount));
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
