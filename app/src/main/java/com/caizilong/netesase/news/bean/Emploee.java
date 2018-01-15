package com.caizilong.netesase.news.bean;

import android.util.Log;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  17:12
 * @desc ${TODD}
 */
public abstract class Emploee {
    //下一个处理者
    Emploee next;

    String name;

    public Emploee(String name) {
        this.name = name;
    }

    public void setNext(Emploee next) {
        this.next = next;
    }

    public void handleRequest(int money){
        Log.i("it520",name+"开始处理");
       if(money <= limit()){
              //自己处理
               handle();
       }
        else{
           next.handleRequest(money);
       }
    }

    public abstract int limit();

    public abstract void handle();

}
