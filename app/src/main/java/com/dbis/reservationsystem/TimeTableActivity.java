package com.dbis.reservationsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1)
            {
                Toast.makeText(getApplicationContext(), "获取完毕", Toast.LENGTH_LONG).show();
                addList();
                //
                mTimaTableView.startTimeTable(new String[]{"3.28", "3.29", "3.30", "3.31", "4.01", "4.02", "4.03"}, mList);
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
                String params = "";
                params += "begintime=" + "2016-03-28 08:00:00";
                params += "&" + "endTime=" + "2016-04-03 22:00:00";
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
                    String ddate = record.getString("beginTime").substring(0, 10);
                    String beginTime = record.getString("beginTime").substring(11);
                    String endTime = record.getString("endTime").substring(11);
                    //Toast.makeText(getApplicationContext(), ddate + ";" + beginTime + ";" + endTime, Toast.LENGTH_LONG).show();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    long to = 0;
                    long from = 0;
                    try {
                        to = df.parse(ddate).getTime();
                        from = df.parse("2016-03-28").getTime();
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
        onBackPressed();
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
}
