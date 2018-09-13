package com.example.aya.cmstask.data_access_layer;

import android.content.Context;
import android.util.Log;

import com.example.aya.cmstask.interfaces.ApiManagerInterface;
import com.example.aya.cmstask.interfaces.ImageUploaderInteractorInterface;
import com.example.aya.cmstask.retrofit_control.pojos.Response;
import com.example.aya.cmstask.retrofit_control.remote.ApiUtils;
import com.example.aya.cmstask.retrofit_control.remote.Service;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Aya on 9/12/2018.
 */

public class ApiManager implements ApiManagerInterface{

    private Context context;
    private ImageUploaderInteractorInterface interactor;

    public ApiManager(Context context, ImageUploaderInteractorInterface interactor) {
        this.context = context;
        this.interactor = interactor;
    }

    public void getData(int id) {
        Service ApiService = ApiUtils.getService();

        ApiService.getData(id).enqueue(new Callback<Response>() {

            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }
}
