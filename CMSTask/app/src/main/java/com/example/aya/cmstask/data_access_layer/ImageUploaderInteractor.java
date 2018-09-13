package com.example.aya.cmstask.data_access_layer;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.example.aya.cmstask.interfaces.ImageUploaderInteractorInterface;
import com.example.aya.cmstask.interfaces.ImageUploaderPresenterInterface;

/**
 * Created by Aya on 9/12/2018.
 */

public class ImageUploaderInteractor implements ImageUploaderInteractorInterface {


    private ImageUploaderPresenterInterface presenter;
    private LocationManagerDAL locationManager;
    private FireBaseManager fireBaseManager;
    private ApiManager apiManager;
    private Context context;

    public ImageUploaderInteractor(ImageUploaderPresenterInterface presenter, Context context) {
        this.presenter = presenter;
        this.locationManager = new LocationManagerDAL(context, this);
        this.apiManager = new ApiManager(context, this);
        this.fireBaseManager = new FireBaseManager(context, this);
    }

    public void getLocation() {
        locationManager.getLocation();

    }

    public void returnLocation(Location currentLocation, String City) {
        presenter.returnLocation(currentLocation, City);
    }

    public void uploadImage(Uri uri, Context context) {
        fireBaseManager.uploadImage(uri, context);
    }

    public void uploadSucceeded() {
        presenter.uploadSucceeded();
    }
}
