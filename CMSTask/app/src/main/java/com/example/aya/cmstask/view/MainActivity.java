package com.example.aya.cmstask.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aya.cmstask.R;
import com.example.aya.cmstask.Utilities;
import com.example.aya.cmstask.interfaces.ImageUploaderViewInterface;
import com.example.aya.cmstask.presenter.ImageUploaderPresenter;
import com.instacart.library.truetime.TrueTime;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements ImageUploaderViewInterface {

    @BindView(R.id.imageView) ImageView imageView;
    @BindView(R.id.imageView2) ImageView imageView2;
    @BindView(R.id.imageView3) ImageView imageView3;
    @BindView(R.id.imageView4) ImageView imageView4;
    @BindView(R.id.imageView5) ImageView imageView5;
    @BindView(R.id.imageView6) ImageView imageView6;

    @BindView(R.id.location) TextView currentLocation;
    @BindView(R.id.latitude) TextView latitude;
    @BindView(R.id.longitude) TextView longitude;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.txtContent) TextView txtContent;

    private Utilities utilities;
    private ImageUploaderPresenter presenter;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private ProgressDialog progressDialog;
    public static int counter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getLocation();
        progressDialog = new ProgressDialog(MainActivity.this);
        presenter = new ImageUploaderPresenter(this, MainActivity.this);
        utilities = new Utilities();

        //Thread for getting current time every second wether there is internet connection or not


        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        TrueTime.build().initialize();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                boolean isConnected = utilities.checkConnectivity(MainActivity.this);
                                if (!isConnected) {
                                    String localTime = utilities.getCurrentTime();
                                    time.setText(localTime);
                                } else {
                                    Date noReallyThisIsTheTrueDateAndTime = TrueTime.now();
                                    String currentTime = noReallyThisIsTheTrueDateAndTime.toString().substring(11, 20);  // gives "How ar"
                                    time.setText(currentTime);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
    }

    //handling clicking on the imageviews to open the camera intent
    public void onClick(View v) {

       if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
           return;
       }
       else{
           Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            switch (v.getId()) {

                case R.id.imageView:
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 1);
                    }
                    break;

                case R.id.imageView2:
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 2);
                    }
                    break;

                case R.id.imageView3:
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 3);
                    }
                    break;

                case R.id.imageView4:
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 4);
                    }
                    break;
                case R.id.imageView5:
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 5);
                    }
                    break;

                case R.id.imageView6:
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, 6);
                    }
                    break;
            }
        }
    }

    //requesting the current location from LocationManagerDAL
    public void getLocation() {
        ImageUploaderPresenter presenter = new ImageUploaderPresenter(this, MainActivity.this);
        presenter.getLocation();
        Log.i("tagg","getloaction");
    }

    //returning the current location
    public void returnLocation(Location userLocation, String City) {
        longitude.setText("longitude: " + userLocation.getLongitude());
        Log.i("tagg",userLocation.getLongitude()+"");
        latitude.setText("latitude: " + userLocation.getLatitude());
        Log.i("tagg",userLocation.getLatitude()+"");
        currentLocation.setText(City);
    }

    //uploading the captured image on firebase
    public void uploadImage(Uri uri, Context context) {
        presenter.uploadImage(uri, context);
    }

    //uploading process is completed and updating the images counter
    public void uploadSucceeded() {
        progressDialog.dismiss();
        txtContent.setText(counter + " / 6 uploaded images");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        boolean isConnected = utilities.checkConnectivity(this);
        if (resultCode != RESULT_CANCELED && resultCode == RESULT_OK ) {
            Uri uri = null;
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Log.i("tagg",imageBitmap+"");
            switch (requestCode) {
                case 1:
                    imageView.setImageBitmap(imageBitmap);
                    uri = utilities.getImageUri(this, imageBitmap);
                    break;
                case 2:
                    imageView2.setImageBitmap(imageBitmap);
                    uri = utilities.getImageUri(this, imageBitmap);
                    break;

                case 3:
                    imageView3.setImageBitmap(imageBitmap);
                    uri = utilities.getImageUri(this, imageBitmap);
                    break;

                case 4:
                    imageView4.setImageBitmap(imageBitmap);
                    uri = utilities.getImageUri(this, imageBitmap);
                    break;

                case 5:
                    imageView5.setImageBitmap(imageBitmap);
                    uri = utilities.getImageUri(this, imageBitmap);
                    break;

                case 6:
                    imageView6.setImageBitmap(imageBitmap);
                    uri = utilities.getImageUri(this, imageBitmap);
                    break;
            }
            progressDialog.setMessage("Uploading ...");
            progressDialog.show();
            if(isConnected) {
                uploadImage(uri, this);
            }
            else{
                progressDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Image cannot be uploaded Check your internet connection !")
                        .setTitle("Upload Failed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                progressDialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
}