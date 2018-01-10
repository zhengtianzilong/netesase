package com.caizilong.netesase.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Administrator on 2018/1/10.
 */

public class ImageUtil {


    public static boolean checkImageIsDownLoad(String imageName){

        File image = getFileByName(imageName);

        if (image.exists()){

            Bitmap bitmap = getBitmapByFile(image);

            if (bitmap != null){
                return true;
            }
        }else {
            return false;
        }

        return false;
    }

    public static Bitmap getBitmapByFile(File image) {
        return BitmapFactory.decodeFile(image.getAbsolutePath());
    }

    @NonNull
    public static File getFileByName(String imageName) {
        File SD = Environment.getExternalStorageDirectory();

        File cacheFile = new File(SD, Constant.CACHE);

        return new File(cacheFile, imageName + ".jpg");
    }

}
