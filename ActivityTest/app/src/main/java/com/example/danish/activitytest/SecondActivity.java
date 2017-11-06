package com.example.danish.activitytest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.EventObject;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);

        Log.d("SecondActivity",this.toString());

        Log.d("SecondActivity","Task id is" + getTaskId());

//        Intent intent = getIntent();
//        String data = intent.getStringExtra("extra_data");
//        Log.d("SecondActivity",data);


        Button btn2 = (Button) findViewById(R.id.button_2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent1 = new Intent();
//                intent1.putExtra("data_return","Hello,FirstActivity");
//                setResult(RESULT_OK,intent1);
//                finish();

//                Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
//                startActivity(intent);

                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data_return","Hello FirstActivity");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity","onDestroy");
    }

    public static void actionStart(Context context,String data1, String data2) {

        Log.d("SecondActivity",data1 + "+"+ data2);
        Intent intent = new Intent(context,SecondActivity.class);
        intent.putExtra("param1",data1);
        intent.putExtra("param2",data2);
        context.startActivity(intent);
    }
}
