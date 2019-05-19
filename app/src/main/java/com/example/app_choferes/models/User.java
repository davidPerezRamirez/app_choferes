package com.example.app_choferes.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("codUsuario")
    int id;
    @SerializedName("codTipoUsuario")
    int idUserType;
    @SerializedName("nombre")
    String name;
    @SerializedName("apellido")
    String lastname;
    @SerializedName("importeViaje")
    double importTravel;

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUserType() {
        return idUserType;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public double getImportTravel() {
        return importTravel;
    }
}
