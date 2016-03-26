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
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by nklyp on 2016/3/9.
 */
public class LoginActivity extends Activity {
    private Button btnLogin;
    private Button btnRegister;
    private TextView txtShow;
    private EditText txtAccount,txtPassword;
    private int backValue;
    private String teacherID,teacherName,result;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123)
            {
                String resultInfo=null;
            //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                int rValue = -2;
                    rValue = backValue;

                switch (rValue)
                {
                    case -2:
                        resultInfo = "网络异常，请检查您的网络连接";break;
                    case -1:
                        resultInfo = "api端的未知错误";break;
                    case 0:
                        resultInfo = "用户名不存在";break;
                    case 1:
                        resultInfo = "老师登录成功";
                        loginSuccessfully();
                        break;
                    case 2:
                        resultInfo = "密码错误";break;
                    case 3:
                        resultInfo = "学生登录成功";break;
                    default:
                        resultInfo = "网络异常，请检查您的网络连接";break;
                }

                txtShow.setText(resultInfo);
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
                new Thread() {
                    @Override
                    public void run() {
//                        RequestParams rp=new RequestParams();
//                        HttpUtils hu=new HttpUtils(3000);
//                        hu.configDefaultHttpCacheExpiry(1);
//                        //post请求时使用addBodyParameter方法添加参数
//                        //get请求时使用addQueryStringParameter方法添加参数
//                        rp.addBodyParameter("userid", txtAccount.getText().toString());
//                        rp.addBodyParameter("password",txtPassword.getText().toString());
//                        hu.send(HttpRequest.HttpMethod.POST, "http://202.113.25.200:8090/api/login", rp, new RequestCallBack<String >() {
//                            @Override
//                            public void onSuccess(ResponseInfo<String> responseInfo) {
//                                JSONObject jo = null;
//                                try {
//                                    jo = new JSONObject(responseInfo.result);
//                                    backValue=jo.getInt("returnvalue");
//                                    if(backValue ==1)
//                                    {
//                                        teacherID = jo.getString("id");
//                                        teacherName = jo.getString("name");
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(HttpException e, String s) {
//                                Toast.makeText(getApplicationContext(),"网络连接失败，请检查您的网络",Toast.LENGTH_LONG).show();
//                            }
//                        });
                        String params = "";
                        params += "userid=" + txtAccount.getText().toString().trim();
                        params += "&" + "password=" + txtPassword.getText().toString().trim();
                        result = PostUtil.sendPost(
                                "http://202.113.25.200:8090/api/login",
                                params);
                        JSONObject jo = null;
                                try {
                                    jo = new JSONObject(result);
                                    backValue=jo.getInt("returnvalue");
                                    if(backValue ==1)
                                    {
                                        teacherID = jo.getString("id");
                                        teacherName = jo.getString("name");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                        handler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtShow.setText("暂时不提供注册服务~");
                Toast.makeText(getApplicationContext(), "暂时不提供注册服务~", Toast.LENGTH_SHORT).show();
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
        ed.putString("name",teacherName);
        ed.putString("id",teacherID);
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
