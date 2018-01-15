package com.caizilong.netesase.news.bean;

import android.util.Log;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  17:16
 * @desc ${TODD}
 */
public class BZR extends Emploee {
    public BZR() {
        super("班主任");
    }

    @Override
    public int limit() {
        return 500;
    }

    @Override
    public void handle() {
      Log.i("it520",name+"自己处理");
    }
}
