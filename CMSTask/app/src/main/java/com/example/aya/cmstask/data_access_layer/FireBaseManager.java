package com.example.aya.cmstask.data_access_layer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.aya.cmstask.interfaces.ImageUploaderInteractorInterface;
import com.example.aya.cmstask.view.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static com.example.aya.cmstask.view.MainActivity.counter;

/**
 * Created by Aya on 9/12/2018.
 */

public class FireBaseManager {

    private Context context;
    private ImageUploaderInteractorInterface interactor;
    private List<String> imagesUrls= new ArrayList<>();

    public FireBaseManager(Context context, ImageUploaderInteractorInterface interactor) {
        this.context = context;
        this.interactor = interactor;
    }

    public void uploadImage(Uri uri, final Context context) {

        StorageReference myrefernce = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath = myrefernce.child("Photos").child(uri.getLastPathSegment());

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                counter++;

                if (counter == 6) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("All six images are successfully uploaded !")
                            .setTitle("Upload")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.i("tagg", "onSuccess: uri= " + uri.toString());
                        imagesUrls.add(uri.toString());
                        Log.i("tagg",imagesUrls+"");

                    }
                });
                Toast.makeText(context, "Uploading finished ...", Toast.LENGTH_LONG).show();
                interactor.uploadSucceeded();
            }
        });

        filepath.putFile(uri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("image failed to be uploaded !")
                        .setTitle("Upload")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
