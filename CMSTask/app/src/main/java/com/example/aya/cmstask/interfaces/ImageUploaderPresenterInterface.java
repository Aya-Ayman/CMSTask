package com.example.aya.cmstask.interfaces;

import android.content.Context;
import android.location.Location;
import android.net.Uri;

/**
 * Created by Aya on 9/12/2018.
 */

public interface ImageUploaderPresenterInterface {
    public void getLocation();
    public void returnLocation(Location currentLocation , String City);

    public void uploadImage(Uri uri, Context context);
    public void uploadSucceeded();

}
