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

    public User(Integer id, Integer idUserType, String name, String lastname) {
        this.id = id;
        this.idUserType = idUserType;
        this.name = name;
        this.lastname = lastname;
    }

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

    public void setIdUserType(Integer idUserType) {
        this.idUserType = idUserType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
