package com.caizilong.netesase.util;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by caizilong on 2018/1/10.
 */

public class JsonUtil {

    private static Gson mGson;

    // <T> 代表声明使用泛型, 第二个T返回的是类型是我们使用的类型
    // Class<T> 就是 类型.class
    public static <T> T parseJson(String json, Class<T> tClass){
        if (mGson == null){
            mGson = new Gson();

        }

        if (TextUtils.isEmpty(json)){

            return null;

        }

        return mGson.fromJson(json, tClass);


    }


}
