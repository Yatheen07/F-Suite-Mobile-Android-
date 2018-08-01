package com.example.kmy07.samplru;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;

public class ImageUploader {

    private String filePath;
    private String fileName;
    private String referenceName;
    private StorageReference mStorageRef;
    private static boolean status = true;

    public ImageUploader(String filePath , String fileName , String referenceName ){
        this.fileName = fileName;
        this.filePath = filePath;
        this.referenceName = referenceName;
    }

    public boolean upload2Firebase(){

        mStorageRef = FirebaseStorage.getInstance().getReference();

        FileInputStream in = null;
        File fileIn = new File(filePath,fileName);
        Uri uri = Uri.fromFile(fileIn);
        StorageReference imageRef = mStorageRef.child("Images/"+referenceName);

        imageRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        //Toast.makeText(MainActivity.this, "Success!" + downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        status = false;
                        // Handle unsuccessful uploads
                        // ...
                        //Toast.makeText(getApplicationContext(), "Error Here!" + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        return status;
    }
}
