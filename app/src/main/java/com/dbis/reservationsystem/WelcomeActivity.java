package com.dbis.reservationsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class WelcomeActivity extends Activity {
    //创建handler做页面的跳转
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==1){
                    Intent intent= new Intent(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        new Thread(new WelcomeRun()).start();
    }

    private class WelcomeRun implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            handler.sendEmptyMessage(1);
        }
    }

}
