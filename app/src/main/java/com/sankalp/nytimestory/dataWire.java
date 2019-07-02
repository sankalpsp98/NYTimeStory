package com.sankalp.nytimestory;

import java.util.ArrayList;
import java.util.List;

public class dataWire  {
    private static    String url;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        dataWire.url = url;
    }

    private static List<results> resultsDataWire =new ArrayList<>();

    public static List<results> getResultsDataWire() {
        return resultsDataWire;
    }

    public static void setResultsDataWire(List<results> resultsDataWire) {
        dataWire.resultsDataWire = resultsDataWire;
    }

}
