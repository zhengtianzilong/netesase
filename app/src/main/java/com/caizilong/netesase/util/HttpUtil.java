package com.caizilong.netesase.util;

import android.content.Intent;
import android.util.Log;

import com.caizilong.netesase.service.DownloadImageService;
import com.caizilong.netesase.splash.activity.SplashActivity;
import com.caizilong.netesase.splash.bean.Ads;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/1/14.
 */

public class HttpUtil {

    static HttpUtil sHttpUtil;

    static OkHttpClient sOkHttpClient;


    private HttpUtil(){

        sOkHttpClient = new OkHttpClient();

    }

    public static HttpUtil getInstance(){
        if (sHttpUtil == null){

            synchronized (HttpUtil.class){
                if (sHttpUtil == null){
                    sHttpUtil = new HttpUtil();
                }
            }

        }

        return sHttpUtil;
    }


    public void getDate(String url, final HttpRespon respon){

        OkHttpClient okHttpClient = new OkHttpClient();

        Request builder = new Request.Builder()
                .url(url)
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

                    respon.error("连接失败");

                }
                String resquest = response.body().string();

                respon.parse(resquest);

            }
        });


    }

}
