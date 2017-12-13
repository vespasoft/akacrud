package com.akacrud.util;

import java.sql.Timestamp;

/**
 * Created by luisvespa on 12/13/17.
 */

public class CommonUtil {

    /*
    * Este metodo retorna la fecha y la hora actual del dispositivo movil
    * */
    public static Timestamp getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }
}
