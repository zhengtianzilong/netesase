package com.caizilong.netesase.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.caizilong.netesase.splash.bean.Ads;
import com.caizilong.netesase.splash.bean.AdsDetail;
import com.caizilong.netesase.util.Constant;
import com.caizilong.netesase.util.Md5Helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by caizilong on 2018/1/10.
 */

public class DownloadImageService extends IntentService {

    public static final String ADS_DATE = "ads";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadImageService() {
        super("DownloadImageService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Ads ads = (Ads) intent.getSerializableExtra(ADS_DATE);

        // 下载图片

        List<AdsDetail> list = ads.getAds();

        for (int i = 0; i < list.size(); i++){

            AdsDetail detail = list.get( i);
            List<String> imags = detail.getRes_url();

            if (null != imags){
                String img_url = imags.get(0);

                if (!TextUtils.isEmpty(img_url)){
                    // 图片地址转成唯一的MD5文件名
                    String catch_name = Md5Helper.toMD5(img_url);

                    // 先判断图片是否存在
                    if (!checkImageIsDownLoad(catch_name)){
                        // 下载图片
                        downloadImage(img_url, catch_name);
                    }


                }

            }
        }
    }


    public void downloadImage(String url, String MD5_name){

        try {
            URL uri = new URL(url);
            URLConnection urlConnection = uri.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());

            saveToSD(bitmap, MD5_name);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveToSD(Bitmap bitmap, String MD5_name){

        if (null == bitmap){
            return;
        }
        // 判断手机SD卡是否装载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            File sdCard = Environment.getExternalStorageDirectory();

            File cacheFile = new File(sdCard, Constant.CACHE);

            if (!cacheFile.exists()){
                cacheFile.mkdirs();
            }

            File image = new File(cacheFile, MD5_name + ".jpg");

            if (image.exists()){
                return;
            }

            try {
                FileOutputStream outputStream = new FileOutputStream(image);

                // 将图片压缩到SD卡
                bitmap.compress(Bitmap.CompressFormat.JPEG,60, outputStream );

                outputStream.flush();;

                outputStream.close();

                Log.d("saveToSD", "saveToSD: completed");

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public boolean checkImageIsDownLoad(String imageName){

        File SD = Environment.getExternalStorageDirectory();

        File cacheFile = new File(SD, Constant.CACHE);

        File image = new File(cacheFile, imageName);

        if (image.exists()){

            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());

            if (bitmap != null){
                return true;
            }
        }else {
            return false;
        }

        return false;
    }


}
