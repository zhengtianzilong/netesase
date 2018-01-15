package com.caizilong.netesase.service;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by caizilong on 2018/1/15.
 */

public class NetEaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        // ImageLoaderConfiguration 配置类
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        ImageLoader.getInstance().init(configuration);


    }
}
