package com.sankalp.nytimestory;

public class results {


    private static String section;
    private  static  String title;
    private  static  String abstrac;
    private  static  String url;
    private  static  String byline;


    public results(String section, String title, String abstrac, String url, String byline) {
        this.section = section;
        this.title = title;
        this.abstrac =abstrac;
        this.url= url;
        this.byline=byline;

    }

    public static String getSection() {
        return section;
    }

    public static String getTitle() {
        return title;
    }

    public static String getAbstrac() {
        return abstrac;
    }

    public static String getUrl() {
        return url;
    }

    public static String getByline() {
        return byline;
    }

}
