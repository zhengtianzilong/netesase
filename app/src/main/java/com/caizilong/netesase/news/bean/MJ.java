package com.caizilong.netesase.news.bean;

import android.util.Log;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  17:18
 * @desc ${TODD}
 */
public class MJ extends Emploee {
    public MJ() {
        super("MJ");
    }

    @Override
    public int limit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void handle() {
        Log.i("it520",name+"同意");
    }
}
