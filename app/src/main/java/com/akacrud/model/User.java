package com.akacrud.model;

import java.sql.Timestamp;

/**
 * Created by luisvespa on 12/13/17.
 * Class of the user entity
 */

public class User {

    int Id;
    String Name;
    Timestamp Birthdate;

    public User() {
    }

    public User(int id, String name, Timestamp birthdate) {
        Id = id;
        Name = name;
        Birthdate = birthdate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Timestamp getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        Birthdate = birthdate;
    }
}
