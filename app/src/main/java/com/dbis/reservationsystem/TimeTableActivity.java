package com.dbis.reservationsystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dbis.reservationsystem.Entity.Record;
import com.dbis.reservationsystem.HTTPUtil.PostUtil;
import com.dbis.reservationsystem.UI.TimeTableModel;
import com.dbis.reservationsystem.UI.TimeTableView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimeTableActivity extends Activity {
    private String result;
    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;
    private JSONArray records;
    private String roomName;
    private String beginTime;
    private String endTime;
    private static Calendar dayBegin = Calendar.getInstance();
    private static Calendar dayEnd = Calendar.getInstance();
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1)
            {
                Toast.makeText(getApplicationContext(), "获取完毕", Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplicationContext(), beginTime, Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), endTime, Toast.LENGTH_LONG).show();
                addList();
                List <String > daytoFill = fillDays();
                mTimaTableView.startTimeTable(new String[]{daytoFill.get(0), daytoFill.get(1), daytoFill.get(2), daytoFill.get(3), daytoFill.get(4), daytoFill.get(5), daytoFill.get(6)}, mList);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);

        Intent intent = getIntent();
        roomName = (String)intent.getExtras().get("roomName");
        new Thread() {
            @Override
            public void run() {
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                String today=format.format(date);
                //dayBegin= Calendar.getInstance();
                //dayEnd = Calendar.getInstance();
                //在此之前一定已经完成对dayBegin和dayEnd的赋值，且两对象值相等
                dayEnd.add(Calendar.DATE,  6);
                String todayPlusSeven = format.format(dayEnd.getTime());
                beginTime = today + " 08:00:00";
                endTime = todayPlusSeven + " 22:00:00";


                String params = "";
                params += "begintime=" + beginTime;
                params += "&" + "endTime=" + endTime;
                result = PostUtil.sendPost(
                        "http://202.113.25.200:8090/api/AllBookingsWithTime",
                        params);
                try {
                    records = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }.start();


    }

    private void addList() {

        for (int i = 0; i < records.length(); i++) {
            JSONObject record = null;
            try {
                record = (JSONObject) records.get(i);
                if(!record.getString("name").equals(roomName))
                    continue;
                else {
                    //ddate,beginTime ,endTime 从记录中获取，dayBegin一直是记录当前开始日期的日历变量
                    String ddate = record.getString("beginTime").substring(0, 10);
                    String beginTime = record.getString("beginTime").substring(11);
                    String endTime = record.getString("endTime").substring(11);
                    //Toast.makeText(getApplicationContext(), ddate + ";" + beginTime + ";" + endTime, Toast.LENGTH_LONG).show();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    long to = 0;
                    long from = 0;
                    try {
                        to = df.parse(ddate).getTime();
                        from = df.parse(df.format(dayBegin.getTime())).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    mList.add(new TimeTableModel(0, fromTimeToIndex(beginTime), fromTimeToIndex(endTime), (int) ((to - from) / (1000 * 60 * 60 * 24)) + 1, "8:20", "10:10", record.getString("teachername"),
                            record.getString("teachername"), record.getString("name"), "2-13"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
//        mList.add(new TimeTableModel(0, 1, 2, 1, "8:20", "10:10", "张莹",
//                "张莹", "会议室553", "2-13"));
    }

    //注意按钮的叠放次序，相对布局下应该叠放在最上层才有效果
    public void btnBackToBook(View v)
    {
        //退出时使日期重新回归当日
        dayBegin = Calendar.getInstance();
        dayEnd = Calendar.getInstance();

        onBackPressed();

    }
    public void btnWeekPlusSeven(View v)
    {
        dayEnd.add(Calendar.DATE, -6);//先减去6和dayBegin相等，再同时+7

        dayBegin.add(Calendar.DATE,  7);
        dayEnd.add(Calendar.DATE,  7);
        //使Activity 重启
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    public void btnWeekMinusSeven(View v)
    {
        dayEnd.add(Calendar.DATE, -6);//先减去6和dayBegin相等，再同时+7

        dayBegin.add(Calendar.DATE,  -7);
        dayEnd.add(Calendar.DATE,  -7);
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    public int fromTimeToIndex(String from)
    {
        if(from.equals("08:00:00"))
            return 1;
        if(from.equals("09:00:00"))
            return 2;
        if(from.equals("10:00:00"))
            return 3;
        if(from.equals("11:00:00"))
            return 4;
        if(from.equals("12:00:00"))
            return 5;
        if(from.equals("13:00:00"))
            return 6;
        if(from.equals("14:00:00"))
            return 7;
        if(from.equals("15:00:00"))
            return 8;
        if(from.equals("16:00:00"))
            return 9;
        if(from.equals("17:00:00"))
            return 10;
        if(from.equals("18:00:00"))
            return 11;
        if(from.equals("19:00:00"))
            return 12;
        if(from.equals("20:00:00"))
            return 13;
        if(from.equals("21:00:00"))
            return 14;
        if(from.equals("22:00:00"))
            return 15;
        else
            return 1;
    }
    public List <String > fillDays ()
    {
        List <String > dayReturn = new ArrayList<String >();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        dayReturn.add(format.format(dayBegin.getTime()).substring(5, 10));
        for ( int i =0;i<6;i++) {
            dayBegin.add(Calendar.DATE, 1);
            dayReturn.add(format.format(dayBegin.getTime()).substring(5,10));
        }
        dayBegin.add(Calendar.DATE, -6);
        return dayReturn;
    }
}
