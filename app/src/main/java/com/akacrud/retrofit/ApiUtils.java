package com.akacrud.retrofit;

import com.akacrud.BuildConfig;

/**
 * Created by luisvespa on 12/13/17.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static UserServices getAPIUserService() {

        return RetrofitClient.getClient(Constants.AKACRUD_API).create(UserServices.class);
    }

}
