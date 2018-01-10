package com.caizilong.netesase.splash.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/12  10:32
 * @desc ${TODD}
 */
public class AdsDetail implements Serializable {
    Action action_params;
    List<String> res_url;

    public Action getAction_params() {
        return action_params;
    }

    public void setAction_params(Action action_params) {
        this.action_params = action_params;
    }

    public List<String> getRes_url() {
        return res_url;
    }

    public void setRes_url(List<String> res_url) {
        this.res_url = res_url;
    }

    @Override
    public String toString() {
        return "AdsDetail{" +
                "action_params=" + action_params +
                ", res_url=" + res_url +
                '}';
    }
}
