package com.example.danish.listviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"Apple","Apple","Apple","Apple","Apple","Apple"};

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                MainActivity.this,android.R.layout.simple_list_item_1,data);
//        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter(adapter);

        initFruitsF();


    }
}
