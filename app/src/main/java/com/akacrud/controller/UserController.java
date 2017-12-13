package com.akacrud.controller;

import com.akacrud.model.User;
import com.akacrud.util.CommonUtil;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luisvespa on 12/13/17.
 */

public class UserController {

    List<User> usersList;

    public UserController() {
    }

    public UserController(List<User> usersList) {
        this.usersList = usersList;
    }

    /*
    * Method: getAll
    * Description: Este metodo retorna un listado de usuarios
    * */
    public List<User> getAll() {
        //TODO: se crea un listado estatico para modo de ejemplo
        usersList = new LinkedList<>();
        usersList.add(new User(1, "Usuario prueba 1", CommonUtil.getCurrentTimeStamp()));
        usersList.add(new User(2, "Usuario prueba 2", CommonUtil.getCurrentTimeStamp()));
        usersList.add(new User(3, "Usuario prueba 3", CommonUtil.getCurrentTimeStamp()));
        usersList.add(new User(4, "Usuario prueba 4", CommonUtil.getCurrentTimeStamp()));

        return usersList;
    }
}
