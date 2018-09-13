package com.example.aya.cmstask.retrofit_control.remote;

import com.example.aya.cmstask.retrofit_control.pojos.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Aya on 9/12/2018.
 */

public interface Service {
    @GET("getEmployee/employeeID={id}")
    @Headers({"Accept: application/json","Content-Type: application/json"})
    Call<Response> getData(@Path("id") int id);
}
