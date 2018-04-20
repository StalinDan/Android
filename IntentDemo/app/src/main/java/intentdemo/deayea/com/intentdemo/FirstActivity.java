package intentdemo.deayea.com.intentdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        Intent intent = getIntent();


//        //获取数组
//        Bundle bundle = intent.getExtras();
//        String[] strings = bundle.getStringArray("array");
//        for (String string : strings) {
//            Log.i("FirstActivity",string + "\n");
//        }


        //获取集合
        List<String > list = intent.getStringArrayListExtra("aa");
        for (String string : list) {
            Log.i("FirstActivity",string + "\n");
        }

        Log.i("FirstActivity",YhdSingleton.getInstance().get("ABC").toString());


    }
}
