package com.example.aya.cmstask.retrofit_control.remote;

/**
 * Created by Aya on 9/12/2018.
 */

public class ApiUtils {
    public static final String BASE_URL = "http://";

    public static Service getService() {

        return RetrofitClient.getClient(BASE_URL).create(Service.class);
    }
}
