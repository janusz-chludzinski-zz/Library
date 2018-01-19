package com.ohgianni.tin.Weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Weather {

    private String name;

    private Map<String, Double> main;

    private Map<String, Integer> clouds;

    private String iconUrl;


    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", main=" + main +
                ", clouds=" + clouds +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
