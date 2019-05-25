package com.example.app_choferes.models;

import com.google.gson.annotations.SerializedName;

public class Expense {

    @SerializedName("descripTipoGasto")
    private String typeExpenseDescription;
    @SerializedName("idTipoGasto")
    private int idTypeExpense;
    @SerializedName("descripcion")
    private String expenseDescription;
    @SerializedName("importe")
    private Double amount;
    @SerializedName("urlImagen")
    private String photoUrl;

    public String getTypeExpenseDescription() {
        return typeExpenseDescription;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public int getIdTypeExpense() {
        return idTypeExpense;
    }
}
