package com.example.app_choferes.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("codUsuario")
    private int id;
    @SerializedName("codTipoUsuario")
    private int idUserType;
    @SerializedName("nombre")
    private String name;
    @SerializedName("apellido")
    private String lastname;
    @SerializedName("importeViaje")
    private double importTravel;
    @SerializedName("idViaje")
    private int idTravel;

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

    public int getIdTravel() {
        return idTravel;
    }
}
