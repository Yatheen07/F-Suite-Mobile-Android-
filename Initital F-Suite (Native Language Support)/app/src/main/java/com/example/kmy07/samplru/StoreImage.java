package com.example.kmy07.samplru;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoreImage {

    private String filePath;
    private String fileName;
    private Bitmap image;

    public StoreImage(String filePath, String fileName, Bitmap image){
        this.fileName = fileName;
        this.filePath = filePath;
        this.image = image;
    }

    public boolean storeImage(){
        boolean status = true;

        FileOutputStream out = null;
        File file = new File(filePath, fileName);
        try {
            out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Edasdasd",e.getMessage());
            status = false;

        } finally {
            try {
                if (out != null) {
                    out.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
                status = false;
            }
        }

        return status;
    }


}
