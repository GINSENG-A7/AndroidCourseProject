package com.example.androidcourseproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class PictureHandler {
    /**
     * Saves Bitmap picture to specified directory on device storage and returns full path of saved image
     * @param finalBitmap
     * @param filesDirectory
     * @return
     */
    public String SaveImage(Bitmap finalBitmap, String filesDirectory) {
        File myDir = new File(filesDirectory);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(filesDirectory + fname);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filesDirectory + fname;
    }

    /**
     * Converts file located in device storage by specified path to Bitmap
     * @param filePath
     * @return
     */
    public Bitmap ConvertPathToBitmap(String filePath) {
        File image = new File(filePath);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
        return bitmap;
    }
}
