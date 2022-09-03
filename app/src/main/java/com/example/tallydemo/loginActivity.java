package com.example.tallydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private EditText login_name;
    private EditText login_pwd;
    private Button login_ok;
    private Button login_weChat;
    private ImageButton login_eye;
    boolean showPwd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intViews();
    }

    private void intViews() {
        login_name = findViewById(R.id.login_name);
        login_pwd = findViewById(R.id.login_pwd);
        login_ok = findViewById(R.id.login_ok);
        login_weChat = findViewById(R.id.rad_weChat);
        login_eye = findViewById(R.id.login_eye);

        login_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(! showPwd){
                    showPwd = true;
                    login_eye.setImageResource(R.mipmap.open);
                    //显示密码
                    login_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    showPwd = false;
                    login_eye.setImageResource(R.mipmap.down);
                    //隐藏密码
                    login_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = login_name.getText().toString();
                String pwd = login_pwd.getText().toString();
                if (name.equals("")){
                    Toast.makeText(loginActivity.this,"请输入正确的用户名",Toast.LENGTH_SHORT).show();
                }else if (pwd.equals("")){
                    Toast.makeText(loginActivity.this,"请输入正确的密码",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("flag",1);
                    intent.putExtra("name",name);
                    intent.setClass(loginActivity.this, MyActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
