package com.example.aya.cmstask.presenter;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.example.aya.cmstask.data_access_layer.ImageUploaderInteractor;
import com.example.aya.cmstask.interfaces.ImageUploaderInteractorInterface;
import com.example.aya.cmstask.interfaces.ImageUploaderPresenterInterface;
import com.example.aya.cmstask.interfaces.ImageUploaderViewInterface;

/**
 * Created by Aya on 9/12/2018.
 */

public class ImageUploaderPresenter implements ImageUploaderPresenterInterface {

    private ImageUploaderInteractorInterface Interactor;
    private ImageUploaderViewInterface View;
    private Context context;

    public ImageUploaderPresenter(ImageUploaderViewInterface View, Context context) {
        Interactor = new ImageUploaderInteractor(this, context);
        this.View = View;
        this.context = context;
    }

    public void getLocation() {
        Interactor.getLocation();
        Log.i("tagg","getLocation Preseneter");

    }

    public void returnLocation(Location currentLocation, String City) {
        Log.i("tagg","returnLocationprseneter");

        View.returnLocation(currentLocation, City);
    }

    public void uploadImage(Uri uri, Context context) {
        Interactor.uploadImage(uri, context);
    }

    public void uploadSucceeded() {
        View.uploadSucceeded();
    }
}
