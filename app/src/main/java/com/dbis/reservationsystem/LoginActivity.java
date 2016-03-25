package com.dbis.reservationsystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.dbis.reservationsystem.Entity.Teacher;
import com.dbis.reservationsystem.HTTPUtil.PostUtil;

/**
 * Created by nklyp on 2016/3/9.
 */
public class LoginActivity extends Activity {
    private Button btnLogin;
    private Button btnRegister;
    private TextView txtShow;
    private String result;
    private EditText txtAccount,txtPassword;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123)
            {
               // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
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
                        result = "网络异常，请检查您的网络连接";break;
                    case -1:
                        result = "api端的未知错误";break;
                    case 0:
                        result = "用户名不存在";break;
                    case 1:
                        result = "老师登录成功";
                        loginSuccessfully();
                        break;
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
    public void loginSuccessfully()
    {//保存下登录的信息...似乎记录下password是一个不好的行径....
        //全局变量暂时没有用到，因为没有需要直接登记的信息
        Toast.makeText(getApplicationContext(),"登录成功~",Toast.LENGTH_SHORT).show();
        SharedPreferences sp=getSharedPreferences("UserInfo",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        String account = txtAccount.getText().toString();
        String password = txtPassword.getText().toString();
        ed.putString("login","true");
        ed.putString("account",account);
        ed.putString("password",password);
        ed.commit();
        //注意提交
        Teacher.setAccount(account);
        Teacher.setPassword(password);

        loginGotoMain();
    }
    public void loginGotoMain()
    {
        Intent loginToMain =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(loginToMain);
        finish();
        //跳转后销毁，保证退出逻辑
        //如果要注销需要增加按钮功能
    }


}
