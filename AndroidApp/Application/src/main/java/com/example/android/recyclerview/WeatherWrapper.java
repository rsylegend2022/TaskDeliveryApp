package com.example.android.recyclerview;

public class WeatherWrapper {

    private int status;

    private static WeatherWrapper weatherWrapper;

    public WeatherWrapper() {
        status = 0;
    }

    public static WeatherWrapper getInstance() {
        if (weatherWrapper == null){
            weatherWrapper = new WeatherWrapper();
        }
        return weatherWrapper;
    }

    public void setHotStatus() {
        status = 1;
    }

    public void setColdStatus() {
        status = -1;
    }

    public int getStatus() {
        return status;
    }

}
