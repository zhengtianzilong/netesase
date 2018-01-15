package com.caizilong.netesase.news.bean;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/15  9:29
 * @desc ${TODD}
 */
public class Banner {

    /**
     * imgsrc : http://cms-bucket.nosdn.127.net/9828e72f12674aca89f3ff21c765e0f520161015073126.jpeg
     * subtitle :
     * tag : photoset
     * title : 北京通州一居民区旁现11层锥形墓地
     * url : 00AP0001|2205028
     */

    private String imgsrc;
    private String subtitle;
    private String tag;
    private String title;
    private String url;

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "imgsrc='" + imgsrc + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
