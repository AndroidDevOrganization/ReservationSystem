package com.dbis.reservationsystem;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

import com.dbis.reservationsystem.HTTPUtil.PostUtil;

/**
 * Created by nklyp on 2016/3/9.
 */
public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnRegister;
    TextView txtShow;
    String result;
    EditText txtAccount,txtPassword;

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123)
            {
                int rValue = -2;
                try {
                    rValue = Integer.parseInt(result);
                }catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
                switch (rValue)
                {
                    case -2:
                        result = "爬字错误";break;
                    case -1:
                        result = "api端的未知错误";break;
                    case 0:
                        result = "用户名不存在";break;
                    case 1:
                        result = "老师登录成功";break;
                    case 2:
                        result = "密码错误";break;
                    case 3:
                        result = "学生登录成功";break;
                }

                txtShow.setText(result);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById (R.id.btnLogin);
        btnRegister = (Button) findViewById (R.id.btnRegister);
        txtShow = (TextView) findViewById (R.id.txtShow);
        txtAccount = (EditText) findViewById (R.id.txtAccount);
        txtPassword = (EditText) findViewById (R.id.txtPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread()
                {
                    @Override
                    public void run() {
                        String params = "";
                        params += "userid=" + txtAccount.getText().toString().trim();
                        params += "&" + "password=" + txtPassword.getText().toString().trim();


                        result = PostUtil.sendPost(
                                "http://202.113.25.200:8090/api/login",
                                params);
                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtShow.setText("注册");
            }
        });

    }
}
