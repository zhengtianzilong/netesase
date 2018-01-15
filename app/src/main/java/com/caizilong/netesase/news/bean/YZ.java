package com.caizilong.netesase.news.bean;

import android.util.Log;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  17:17
 * @desc ${TODD}
 */
public class YZ extends Emploee {
    public YZ() {
        super("院长");
    }

    @Override
    public int limit() {
        return 2000;
    }

    @Override
    public void handle() {
        Log.i("it520",name+"自己处理");
    }
}
