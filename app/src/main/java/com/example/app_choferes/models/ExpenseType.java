package com.example.app_choferes.models;

import com.google.gson.annotations.SerializedName;

public class ExpenseType {

    @SerializedName("codTipoGasto")
    int id;
    @SerializedName("descripcion")
    String description;

    public ExpenseType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
