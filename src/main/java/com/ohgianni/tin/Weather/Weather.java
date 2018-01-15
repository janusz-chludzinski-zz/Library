package com.ohgianni.tin.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private String name;

    private Map<String, Double> main;

    private Map<String, Integer> clouds;

    private String iconUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getMain() {
        return main;
    }

    public void setMain(Map<String, Double> main) {
        this.main = main;
    }

    public Map<String, Integer> getClouds() {
        return clouds;
    }

    public void setClouds(Map<String, Integer> clouds) {
        this.clouds = clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", main=" + main +
                ", clouds=" + clouds +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }

    public void resolveImage() {
        Double humidity = main.get("humidity");
        Double clouds = main.get("clouds");



//        if(humidity >= 100 && clouds >= 100) {
//
//        }
//        else if() {
//
//        }
//        else if() {
//
//        }
    }
}
