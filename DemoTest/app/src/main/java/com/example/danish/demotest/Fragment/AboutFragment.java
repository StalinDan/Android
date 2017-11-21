package com.example.danish.demotest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.WindowManager;
import android.content.Context;

import com.example.danish.demotest.R;
import com.example.danish.demotest.Widgets.MyRowTitle;
import com.example.danish.demotest.Widgets.My_iconItem;

/**
 * Created by danish on 2017/11/17.
 */

public class AboutFragment extends BaseFragment {

    String[] name = {"1","2","3","4","5"};
    String [] title = {"a","b","c"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_about,null);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = (wm.getDefaultDisplay().getWidth())/5;

        LinearLayout.LayoutParams  lineParams =  new LinearLayout.LayoutParams(width,200);
        LinearLayout.LayoutParams  titleLineParams =  new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int m = 0; m < title.length; m++) {
            MyRowTitle rowTitle = new MyRowTitle(getContext());

            //titleLineParams.topMargin = m*200;
            rowTitle.setLayoutParams(titleLineParams);
            view.addView(rowTitle);
            rowTitle.rowTitle.setText(title[m]);


            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams( new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200) );
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            view.addView(linearLayout);
            for (int i = 0; i<name.length;i++) {
                My_iconItem iconItem = new My_iconItem(getContext());
                Log.d("AboutFragment","name" + name[i]);

                iconItem.setLayoutParams(lineParams);
                iconItem.itemText.setText(name[i]+"m="+m);
                linearLayout.addView(iconItem);
            }
        }


        return view;
    }
}
