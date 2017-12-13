package com.akacrud.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by luisvespa on 12/13/17.
 * Class of the user entity
 */

public class User implements Serializable {

    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("birthdate")
    @Expose
    String birthdate;

    public User() {
    }

    public User(int id, String name, String birthdate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
