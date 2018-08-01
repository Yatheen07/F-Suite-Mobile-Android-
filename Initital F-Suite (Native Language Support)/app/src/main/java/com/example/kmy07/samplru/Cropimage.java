package com.example.kmy07.samplru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Cropimage extends AppCompatActivity {


    ImageView image;
    Button choose, upload;
    int PICK_IMAGE_REQUEST = 111;

    Bitmap bitmap;
    ProgressDialog progressDialog;

    private String filePath = Environment.getExternalStorageDirectory().toString();
    private String fileName = "sampleImage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropimage);

        image = (ImageView)findViewById(R.id.image);
        choose = (Button)findViewById(R.id.b1);


        //opening image chooser option
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filepath = data.getData();
            //Toast.makeText(this, filePath.getPath(), Toast.LENGTH_LONG).show();
            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);

                StoreImage object = new StoreImage(filePath,fileName,bitmap);
                if(object.storeImage()){
                    Toast.makeText(this, "Image Succesfully Saved!\nUploading Image...", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Error in saving Image!", Toast.LENGTH_SHORT).show();
                }

                //Setting image to ImageView
                image.setImageBitmap(bitmap);
                ImageUploader uploader = new ImageUploader(filePath,fileName,fileName);
                if(uploader.upload2Firebase()){
                    Toast.makeText(this, "Image Succesfully Uploaded!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error in uploading Image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("This ONe",e.getMessage());
            }
        }

        //Code to upload image to firebase

    }
}


