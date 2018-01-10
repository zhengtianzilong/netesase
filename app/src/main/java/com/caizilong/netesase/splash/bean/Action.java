package com.caizilong.netesase.splash.bean;

import java.io.Serializable;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/12  10:34
 * @desc ${TODD}
 */
public class Action implements Serializable{
   public String link_url;

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "Action{" +
                "link_url='" + link_url + '\'' +
                '}';
    }
}
