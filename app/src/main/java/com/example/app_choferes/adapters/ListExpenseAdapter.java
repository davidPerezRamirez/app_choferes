package com.example.app_choferes.adapters;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_choferes.R;
import com.example.app_choferes.models.Expense;
import com.example.app_choferes.models.IconByTypeExpenseRepositoy;
import com.example.app_choferes.ui.DialogFraments.ZoomImagePopup;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListExpenseAdapter extends RecyclerView.Adapter<ListExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenses;
    private FragmentManager fragmentManager;
    private IconByTypeExpenseRepositoy iconByTypeExpenseRepositoy;

    public ListExpenseAdapter(List<Expense> expenses, FragmentManager fragManager) {
        this.expenses = expenses;
        this.fragmentManager = fragManager;
        this.iconByTypeExpenseRepositoy = new IconByTypeExpenseRepositoy();
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_expense, parent, false);

        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        final Expense expense = expenses.get(position);
        int iconTypeExpense = iconByTypeExpenseRepositoy.getIcon(expense.getIdTypeExpense());

        holder.tvAmount.setText("$" + expense.getAmount().toString());
        holder.tvExpenseDescription.setText(expense.getExpenseDescription());
        holder.tvTypeExpense.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconTypeExpense, 0);
        holder.tvTypeExpense.setText(expense.getTypeExpenseDescription());
        Picasso.with(holder.expensePhoto.getContext())
                .load(expense.getPhotoUrl())
                .error(R.drawable.no_imagen)
                .resize(80, 80)
                .into(holder.expensePhoto);

        holder.expensePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showZoomImagePopup(expense.getPhotoUrl());
            }
        });
    }

    private void showZoomImagePopup(String urlImage) {
        ZoomImagePopup popup = ZoomImagePopup.getInstance(urlImage);

        popup.show(fragmentManager, "Popup zoom image");
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tvExpenseDescription)
        TextView tvExpenseDescription;
        @BindView(R.id.tvTypeExpense)
        TextView tvTypeExpense;
        @BindView(R.id.expense_photo)
        ImageView expensePhoto;

        private ExpenseViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
