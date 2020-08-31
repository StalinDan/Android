package com.danish.sunset.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danish.sunset.R;
import com.danish.sunset.TestActivity1;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity0 extends AppCompatActivity {

    @BindView(R.id.btn0)
    Button btn0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test0);
        ButterKnife.bind(this);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity0.this, TestActivity1.class);
                startActivity(intent);
            }
        });
    }
}
