package com.caizilong.netesase.splash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/12  10:31
 * @desc ${TODD}
 */
public class Ads implements Serializable {
    int next_req ;
    List<AdsDetail> ads;

    public List<AdsDetail> getAds() {
        return ads;
    }

    public void setAds(List<AdsDetail> ads) {
        this.ads = ads;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "next_req=" + next_req +
                ", ads=" + ads +
                '}';
    }
}
