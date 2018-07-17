package com.danish.attendance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.danish.attendance.R;
import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.utils.SharedPreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyHostActivity extends BaseActivity {

    @BindView(R.id.ipAddrss)
    EditText ipAddrss;
    @BindView(R.id.portNum)
    EditText portNum;
    @BindView(R.id.confirmBtn)
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_host);
        ButterKnife.bind(this);

        String hostStr = SharedPreferenceManager.getHost();
        String[] array = hostStr.split(":");

        ipAddrss.setText(array[0] + ":" + array[1]);
        portNum.setText(array[2]);


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ipAddrss.getText().length() > 0 && portNum.getText().length() > 0) {

                    SharedPreferenceManager.setHost(ipAddrss.getText().toString() + ":" + portNum.getText().toString());

                    AsyncRequestRunner.HOST = SharedPreferenceManager.getHost();
                    MainActivity.isAlterHost = true;

                    finish();
                } else {
                    Toast.makeText(ModifyHostActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
