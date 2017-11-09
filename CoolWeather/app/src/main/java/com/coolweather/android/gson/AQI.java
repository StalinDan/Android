package com.coolweather.android.gson;

/**
 * Created by danish on 2017/11/9.
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
