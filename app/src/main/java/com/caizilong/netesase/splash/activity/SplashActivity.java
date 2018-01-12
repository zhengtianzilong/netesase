package com.caizilong.netesase.splash.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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

import com.caizilong.netesase.MainActivity;
import com.caizilong.netesase.R;
import com.caizilong.netesase.service.DownloadImageService;
import com.caizilong.netesase.splash.OnTimeClickListener;
import com.caizilong.netesase.splash.TimeView;
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
import java.lang.ref.WeakReference;
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
    private boolean isAllowed = false;

    private MyHandel mHandler;

    private TimeView timeView;

    private int length = 2 * 1000;

    private int space = 250;

    private int now = 0;
    int total;
//    Handler mHandler;

    @SuppressLint("HandlerLeak")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS);


        setContentView(R.layout.activity_splash);

        ads_image = (ImageView)findViewById(R.id.ads);

//        mHandler = new MyHandel();


        timeView = (TimeView)findViewById(R.id.timeView);

        timeView.setD(200);
        timeView.setListener(new OnTimeClickListener() {
            @Override
            public void onClickTimer(View view) {
                // 直接跳转到mainactivity
                // 当我们点击跳过按钮的时候,就不去触发时间了
                mHandler.removeCallbacks(runnable);
                GotoMain();
            }
        });

        total = length / space;

        mHandler = new MyHandel(this);

        mHandler.post(runnable);




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
                    1);
        }
            getAds();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        isAllowed = true;
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

        if (!TextUtils.isEmpty(cache)){
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
                                    finish();
                                    mHandler.removeCallbacks(runnable);
                                }
                            }
                        });
                    }
                }
            }
        }else {
            mHandler.postDelayed(NoPhotoGotoMain, 300);
        }




    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Message message = mHandler.obtainMessage(0);

            message.arg1 = now;

            mHandler.sendMessage(message);

            mHandler.postDelayed(this, space);
            now++;
        }
    };


    Runnable NoPhotoGotoMain = new Runnable() {
        @Override
        public void run() {
            GotoMain();
        }

    };
    public void GotoMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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

    // 1 使用静态内部类,切断访问activity
    static class MyHandel extends Handler{

        WeakReference<SplashActivity> activity;

        public MyHandel(SplashActivity act) {
            this.activity = new WeakReference<SplashActivity>(act);

        }

        @Override
        public void handleMessage(Message msg) {

            SplashActivity act = activity.get();

            if (act == null) {

                return;

            }

            switch (msg.what){
                case 0:
                    int nows = msg.arg1;

                    if (nows <= act.total){
                        act.timeView.setProgress( act.total, nows);
                    }else {
                        act.mHandler.removeCallbacks(act.runnable);
                        act.GotoMain();
                    }
                    break;
            }

        }
    }



}
