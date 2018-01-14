package com.caizilong.netesase.util;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/1/14.
 */

public abstract class HttpRespon<T> {

    Class<T> t;

    public abstract void error(String msg);

    public abstract void success(T result);


    public HttpRespon(Class<T> tclass){

        this.t = tclass;
    }

    public void parse(String json){

        if (TextUtils.isEmpty(json)){

            error("json解析失败");
            return;
        }

        if (t == String.class){

            success((T)json);
            return;

        }

        T result = JsonUtil.parseJson(json, this.t);

        if (result != null){
            success(result);
        }else {
            error("json解析失败");
        }


    }
}
