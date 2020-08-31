package com.danish.sunset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danish.sunset.fragment.TestActivity0;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity3 extends AppCompatActivity {

    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity3.this, TestActivity0.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity3.this, TestActivity2.class);
                startActivity(intent);
            }
        });
    }
}
