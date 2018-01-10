package com.caizilong.netesase.splash.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.caizilong.netesase.R;
import com.caizilong.netesase.service.DownloadImageService;
import com.caizilong.netesase.splash.bean.Action;
import com.caizilong.netesase.splash.bean.Ads;
import com.caizilong.netesase.splash.bean.AdsDetail;
import com.caizilong.netesase.util.Constant;
import com.caizilong.netesase.util.ImageUtil;
import com.caizilong.netesase.util.JsonUtil;
import com.caizilong.netesase.util.Md5Helper;
import com.caizilong.netesase.util.SharePrenceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private ImageView ads_image;
    private static final String TAG = "SplashActivity";
    private static final String JSON_CACHE = "ads_Json";
    private static final String JSON_CACHE_TIME_OUT = "ads_Json_TIME_OUT";
    private static final String JSON_CACHE_LAST_SUCCESS = "ads_Json_last";
    private static final String LAST_IMAGE_INDEX = "image_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        ads_image = (ImageView)findViewById(R.id.ads);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }

        getAds();

    }


    // 判断是否需要保存缓存
    public void getAds(){
        String cache = SharePrenceUtil.getString(SplashActivity.this, JSON_CACHE);

        if (TextUtils.isEmpty(cache)){
            httpRequest();
        }else {

            int time_out = SharePrenceUtil.getInt(SplashActivity.this, JSON_CACHE_TIME_OUT);

            long last = SharePrenceUtil.getLong(SplashActivity.this, JSON_CACHE_LAST_SUCCESS);

            long now = System.currentTimeMillis();

            if ((now - last) > time_out * 60 * 1000){
                httpRequest();
            }


        }
        showImage();
    }

    public void showImage(){

        String cache = SharePrenceUtil.getString(SplashActivity.this, JSON_CACHE);

        int index = SharePrenceUtil.getInt(SplashActivity.this, LAST_IMAGE_INDEX);

        Ads ads = JsonUtil.parseJson(cache, Ads.class);

        if (null == ads){
            return;
        }

        List<AdsDetail> adsDetails = ads.getAds();

        if (null != adsDetails && adsDetails.size() > 0){
            final AdsDetail detail = adsDetails.get(index % ads.getAds().size());

            List<String> res_url = detail.getRes_url();

            if (null != res_url && !TextUtils.isEmpty(res_url.get(0))){

                String url = res_url.get(0);
                String imageName = Md5Helper.toMD5(url);

                File image = ImageUtil.getFileByName(imageName);

                if (image.exists()){
                    Bitmap bitmap= ImageUtil.getBitmapByFile(image);
                    ads_image.setImageBitmap(bitmap);
                    index++;

                    SharePrenceUtil.saveInt(SplashActivity.this, LAST_IMAGE_INDEX, index);

                    ads_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Action action = detail.getAction_params();

                            if (action != null && !TextUtils.isEmpty(action.getLink_url())){
                                Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                                intent.putExtra(WebViewActivity.ACTION_NAME,action.getLink_url());
                                startActivity(intent);

                            }

                        }
                    });

                }


            }

        }



    }


    // 网络请求
    public void httpRequest(){

        OkHttpClient okHttpClient = new OkHttpClient();

        Request builder = new Request.Builder()
                .url(Constant.SPLASH_URL)
                .build()
                ;

        okHttpClient.newCall(builder).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()){
                    // 请求失败

                }
                String resquest = response.body().string();
                Ads ads = JsonUtil.parseJson(resquest, Ads.class);

                if (null != ads){
                    // 请求成功
                    Log.i(TAG, "onResponse: " + ads.toString());

                    SharePrenceUtil.saveString(SplashActivity.this, JSON_CACHE,resquest );
                    SharePrenceUtil.saveInt(SplashActivity.this, JSON_CACHE_TIME_OUT, ads.getNext_req());

                    SharePrenceUtil.saveLong(SplashActivity.this, JSON_CACHE_LAST_SUCCESS, System.currentTimeMillis());

                    Intent intent = new Intent(SplashActivity.this, DownloadImageService.class);
                    intent.putExtra(DownloadImageService.ADS_DATE, ads);
                    startService(intent);
                }else {
                    // 请求失败

                }


            }
        });


    }


}
