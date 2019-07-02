package com.sankalp.nytimestory;

public class results {


    private  String section;
    private    String title;
    private    String abstrac;
    private    String url;
    private    String byline;
    private    String murl;


    public results(String section, String title, String abstrac, String url, String byline, String murl) {
        this.section = section;
        this.title = title;
        this.abstrac =abstrac;
        this.url= url;
        this.byline=byline;
        this.murl =murl;

    }

    public String getMurl() {
        return murl;
    }
    public String getSection() {
        return section;
    }

    public  String getTitle() {
        return title;
    }

    public  String getAbstrac() {
        return abstrac;
    }

    public String getUrl() {
        return url;
    }

    public String getByline() {
        return byline;
    }

}
