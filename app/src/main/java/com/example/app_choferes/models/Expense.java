package com.example.app_choferes.models;

import com.google.gson.annotations.SerializedName;

public class Expense {

    @SerializedName("descriptipogasto")
    private String typeExpenseDescription;
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
        return /*photoUrl*/
                "https://isuzu.com.ar/wp-content/uploads/2018/03/Serie-EV.jpg";
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }
}
