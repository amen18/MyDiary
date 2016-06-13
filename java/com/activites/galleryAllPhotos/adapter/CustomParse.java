package com.activites.galleryAllPhotos.adapter;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by AMEN on 12.06.2016.
 */
public class CustomParse {
    public static final String PATH_EXTERNAL_IMAGE =  Environment.getExternalStorageDirectory() + "/MyFile/";

    public Uri uriParse(String path , String pathNewImage){

        File source = new File(path);
        File destination = new File(pathNewImage);
        try {
            if (source.exists()) {
                if (source.exists()) {
                    FileChannel src = new FileInputStream(source).getChannel();
                    FileChannel dst = new FileOutputStream(destination).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(new File(pathNewImage));
        return uri;
    }

}
