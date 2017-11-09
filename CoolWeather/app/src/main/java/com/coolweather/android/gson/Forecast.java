package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danish on 2017/11/9.
 */

public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Tempature tempature;

    @SerializedName("cond")
    public More more;


    public class Tempature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}
