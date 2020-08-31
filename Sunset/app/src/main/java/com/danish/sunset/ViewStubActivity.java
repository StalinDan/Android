package com.danish.sunset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewStubActivity extends AppCompatActivity {


    @BindView(R.id.viewStub)
    ViewStub viewStub;
    @BindView(R.id.show)
    Button show;
    @BindView(R.id.hide)
    Button hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);
        ButterKnife.bind(this);

        long interval = 654;
        double seconds = 1000;
//        double num = (double)(Math.round(seconds*1000)/1000.0);
        BigDecimal b1 = new BigDecimal(Long.toString(interval));
        BigDecimal b2 = new BigDecimal(Double.toString(seconds));
        double sec = b1.divide(b2, 1, BigDecimal.ROUND_HALF_UP).doubleValue();

//        DecimalFormat decimalFormat = new DecimalFormat(".##");
//        String str = decimalFormat.format(seconds);
        Log.i("aa","seconds===" + sec );

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(View.INVISIBLE);
                Log.i("aa", "hide");

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    viewStub.inflate();
                    Log.i("aa", "show");

                } catch (Exception e) {

                    viewStub.setVisibility(View.VISIBLE);
                    Log.i("aa", "catch--->show");
                } finally {

                }
            }
        });

        int [] nums = {2,7,11,15};
        twoSum(nums,9);

        String version1 = "";
        String []arr = version1.split(".");


    }

    private int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        int [] values = new int[2];

        for (int i=0;i < nums.length;i++) {
            int a = target- nums[i];
            if (map.containsKey(a)) {
                Log.i("aa","map.get(a)==" + map.get(a) + "\n" + "i==" + i);
                values[0] = map.get(a);
                values[1] = i;
//                return new int[]{map.get(a),i};
                return values;
            }
            map.put(nums[i],i);
        }
        return new int[]{};
    }


}
