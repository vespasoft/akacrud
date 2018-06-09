package com.akacrud.entity.api.retrofit;

import com.akacrud.entity.api.Constants;

/**
 * Created by luisvespa on 12/13/17.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static UserRetrofitServices getAPIUserService() {

        return RetrofitClient.getClient(Constants.AKACRUD_API).create(UserRetrofitServices.class);
    }

}
