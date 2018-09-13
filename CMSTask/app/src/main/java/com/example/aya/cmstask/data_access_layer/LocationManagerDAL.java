package com.example.aya.cmstask.data_access_layer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.aya.cmstask.interfaces.ImageUploaderInteractorInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Aya on 9/12/2018.
 */

public class LocationManagerDAL {

    Context context;
    ImageUploaderInteractorInterface interactor;
    LocationManager loc;
    Geocoder geocoder;
    List<Address> allAddress;
    private double longitudes;
    private double latitudes;
    String locationCity;


    public LocationManagerDAL(Context context, ImageUploaderInteractorInterface interactor) {
        this.interactor = interactor;
        this.context = context;
    }

    public void getLocation() {
        loc = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            loc.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitudes = location.getLatitude();
                    longitudes = location.getLongitude();

                    try {
                        allAddress = geocoder.getFromLocation(latitudes, longitudes, 1);

                        if (allAddress != null) {
                            locationCity = allAddress.get(0).getAdminArea();

                        }

                        interactor.returnLocation(location, locationCity);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    loc.removeUpdates(this);

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }

            });
        }

    }

}
