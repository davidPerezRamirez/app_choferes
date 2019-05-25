package com.example.app_choferes.models;

import android.util.SparseIntArray;

import com.example.app_choferes.R;

public class IconByTypeExpenseRepositoy {

    private SparseIntArray typeExpenseDescription;

    public IconByTypeExpenseRepositoy() {
        this.typeExpenseDescription = new SparseIntArray();

        this.initialize();
    }

    private void initialize() {
        this.typeExpenseDescription.put(1, R.drawable.ic_tax);
        this.typeExpenseDescription.put(2, R.drawable.ic_gasoil);
        this.typeExpenseDescription.put(3, R.drawable.ic_restaurant);
    }

    public Integer getIcon(int idTypeExpense) {
        return this.typeExpenseDescription.get(idTypeExpense);
    }
}
