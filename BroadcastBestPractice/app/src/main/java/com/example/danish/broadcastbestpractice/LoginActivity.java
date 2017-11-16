package com.example.danish.broadcastbestpractice;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        login = findViewById(R.id.login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        checkBox = findViewById(R.id.remeber_pass);
        final boolean isRemember = pref.getBoolean("remember_password",false);

        if (isRemember) {
            String count = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(count);
            passwordEdit.setText(password);
            checkBox.setChecked(true);
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (account.equals("admin")   && password.equals("123456")) {

                    editor = pref.edit();
                    if (checkBox.isChecked()) {
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
