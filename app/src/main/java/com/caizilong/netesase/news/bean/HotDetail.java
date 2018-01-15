package com.caizilong.netesase.news.bean;

import java.util.List;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  9:25
 * @desc ${TODD}
 */
public class HotDetail {
    List<Banner> ads;
    String img;
    String title;
    String source;
    int replyCount;
    String specialID;

    public List<Banner> getAds() {
        return ads;
    }

    public void setAds(List<Banner> ads) {
        this.ads = ads;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getSpecialID() {
        return specialID;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    @Override
    public String toString() {
        return "HotDetail{" +
                "ads=" + ads +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", replyCount=" + replyCount +
                ", specialID='" + specialID + '\'' +
                '}';
    }
}
