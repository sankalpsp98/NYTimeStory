package com.sankalp.nytimestory;

public class results {


    private  String section;
    private    String title;
    private    String abstrac;
    private    String url;
    private    String byline;


    public results(String section, String title, String abstrac, String url, String byline) {
        this.section = section;
        this.title = title;
        this.abstrac =abstrac;
        this.url= url;
        this.byline=byline;

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
