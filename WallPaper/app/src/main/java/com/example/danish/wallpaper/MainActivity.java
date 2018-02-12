package com.example.danish.wallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.selectWallBtn);

        WallpaperManager wpManager = WallpaperManager.getInstance(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseIntent = new Intent(Intent.ACTION_WALLPAPER_CHANGED);
                startActivity(Intent.createChooser(chooseIntent,"选择壁纸"));
            }
        });
    }
}
