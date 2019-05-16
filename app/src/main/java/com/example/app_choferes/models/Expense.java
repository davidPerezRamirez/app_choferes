package com.example.app_choferes.models;

public class Expense {

    private String typeExpenseDescription;
    private Double amount;
    private String photoUrl;

    public Expense(String typeExpenseDescription, Double amount, String photoUrl) {
        this.typeExpenseDescription = typeExpenseDescription;
        this.amount = amount;
        this.photoUrl = photoUrl;
    }

    public String getTypeExpenseDescription() {
        return typeExpenseDescription;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
