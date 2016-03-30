package com.dbis.reservationsystem.HTTPUtil;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.dbis.reservationsystem.Entity.MeetingRoom;
import com.dbis.reservationsystem.Entity.MyReservation;
import com.dbis.reservationsystem.Entity.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shi12 on 2016/3/29.
 */
public class PostManager {
    private static final String HOST_URL = "http://202.113.25.200:8090/api/";
    private static String url;
    private static String params;
    private static JSONArray jsonArray;
    private static JSONObject jsonObject;
    private static Handler handler;

    public static List<MeetingRoom> AllMeetingRoom(final Context context) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Toast.makeText(context, "正在加载，请耐心等待……", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 1) {
                    Toast.makeText(context, "获取完毕~", Toast.LENGTH_SHORT).show();
                }
            }
        };
        final List<MeetingRoom> mrlist = new ArrayList<MeetingRoom>();
        new Thread() {
            @Override
            public void run() {
//                handler.sendEmptyMessage(0);
                url = "allmeetingroom";
                params = null;
                String result = PostUtil.sendPost(HOST_URL + url, params);
                try {
                    jsonArray = new JSONArray(result);
                    int i = 0;
                    while(i < jsonArray.length()) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        int rid = jsonObject.getInt("id");
                        String roomName = jsonObject.getString("name");
                        int capacity = jsonObject.getInt("capacity");
                        boolean isNeedConfirm = jsonObject.getBoolean("isNeedConfirm");
                        int beginTime = jsonObject.getInt("beginTime");
                        int endTime = jsonObject.getInt("endTime");
                        String description = jsonObject.getString("description");
                        String location = jsonObject.getString("location");
                        int confirmTime = jsonObject.getInt("confirmTime");
                        int inAdvanceTime = jsonObject.getInt("inAdvanceTime");
                        int authorityId = jsonObject.getInt("authority");
                        MeetingRoom mRoom = new MeetingRoom(rid, roomName, capacity, isNeedConfirm,
                                beginTime, endTime, description, location, confirmTime, inAdvanceTime, authorityId);
                        mrlist.add(mRoom);
                        i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
        return mrlist;
    }

    public static List<MyReservation> Booking(final Context context) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Toast.makeText(context, "正在加载，请耐心等待……", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 1) {
                    Toast.makeText(context, "获取完毕~", Toast.LENGTH_SHORT).show();
                }
            }
        };
        final List<MyReservation> mrlist = new ArrayList<MyReservation>();
        new Thread() {
            @Override
            public void run() {
//                handler.sendEmptyMessage(0);
                url = "booking";
                params = "teacherid=" + Teacher.getId();
                String result = PostUtil.sendPost(HOST_URL + url, params);
                try {
                    jsonArray = new JSONArray(result);
                    int i = 0;
                    while(i < jsonArray.length()) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        int id = jsonObject.getInt("id");
                        String roomName = jsonObject.getString("name");
                        String userName = Teacher.getName();
                        String state = jsonObject.getString("state");
                        String description = jsonObject.getString("description");
                        String useBeginTime = jsonObject.getString("beginTime");
                        String useEndTime = jsonObject.getString("endTime");
                        String bookingTime = jsonObject.getString("bookingTime");
                        MyReservation mReservation = new MyReservation(id, roomName, userName,
                                state, description, useBeginTime, useEndTime, bookingTime);
                        mrlist.add(mReservation);
                        i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
        return mrlist;
    }

}
