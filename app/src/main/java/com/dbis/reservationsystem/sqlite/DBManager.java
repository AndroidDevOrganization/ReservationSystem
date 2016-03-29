package com.dbis.reservationsystem.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dbis.reservationsystem.Entity.MeetingRoom;
import com.dbis.reservationsystem.Entity.RecordTime;
import com.dbis.reservationsystem.Entity.MyReservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by waho on 2016/3/8.
 */
public class DBManager {
    private SQLiteDBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new SQLiteDBHelper(context);
    }

    public List<String> getAllRoomName() {
        db = helper.getReadableDatabase();
        ArrayList<String> roomNames = new ArrayList<String>();
        Cursor c = db.rawQuery("select roomname from meetingRoomInfo",null);
        while(c.moveToNext())
        {
            String roomName =
            c.getString(0);
            roomNames.add(roomName);
        }
        c.close();
        db.close();
        return roomNames;
    }

    public String  getLocationByName(String roomName) {
        db = helper.getReadableDatabase();
        String roomLocation = null ;
        Cursor c = db.rawQuery("select roomlocation from meetingRoomInfo where roomname = ?",new String[]{roomName});
        while(c.moveToNext())
            roomLocation = c.getString(0);

        c.close();
        db.close();
        return roomLocation;
    }

    public void insertIntoRecord(String roomName,String username ,String useBegin ,String useEnd ,String state ,String description ,String reservetime) {
        db = helper.getWritableDatabase();
        // do not to have ' ' outside the ?
        String sql = "insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
                "values(?,?,? ,? ,? ,? ,?)";
        db.execSQL(sql, new Object[]{roomName, username, useBegin, useEnd, state, description, reservetime});
        db.close();
    }

    public List<MeetingRoom> getMeetingRoomList() {
        db = helper.getReadableDatabase();
        List<MeetingRoom> mrlist = new ArrayList<MeetingRoom>();
        Cursor c = db.rawQuery("select * from meetingRoomInfo", null);
        while(c.moveToNext()) {
            int rid = c.getInt(0);
            String room_name = c.getString(1);
            String room_location = c.getString(2);
            int capacity = c.getInt(3);
            int authority_id = c.getInt(4);
            int begin_time = c.getInt(5);
            int end_time = c.getInt(6);
            String description = c.getString(7);
            if(description == null || description.equals(""))
                description = "（暂无）";
            int confirm = c.getInt(8);
            boolean isNeedConfirm = (c.getInt(8)!=0);
            MeetingRoom mr = new MeetingRoom(rid, room_name, capacity, isNeedConfirm, begin_time, end_time, room_location, 0);
            mrlist.add(mr);
        }
        for(int i = 0; i < 4; i++)
            mrlist.add(mrlist.get(i));
        c.close();
        db.close();
        return mrlist;
    }

    public List<MyReservation> getMyReservationList() {
        db = helper.getReadableDatabase();
        List<MyReservation> mrlist = new ArrayList<MyReservation>();
        Cursor c = db.rawQuery("select * from reserveRecord", null);
        while(c.moveToNext()) {
            int id = c.getInt(0);
            String roomName = c.getString(1);
            String userName = c.getString(2);
            String useBeginTime = c.getString(3);
            String userEndTime = c.getString(4);
            String state = c.getString(5);
            String description = c.getString(6);
            String bookingTime = c.getString(7);
            if(description == null || description.equals(""))
                description = "（暂无）";

            MyReservation mr = new MyReservation(id, roomName, userName, state, description, useBeginTime, userEndTime, bookingTime);
            mrlist.add(mr);
        }
        for(int i = 0; i < 4; i++)
            mrlist.add(mrlist.get(i));
        c.close();
        db.close();
        return mrlist;
    }
    public List<RecordTime > getRecordTimebyRoomNameAndDate(String roomName ,String date)
    {
        db = helper.getReadableDatabase();
        String Qdate = date + "%";
        ArrayList<RecordTime > recordTimes = new ArrayList<RecordTime>();
        Cursor c = db.rawQuery("select useBegin,useEnd from reserveRecord where roomname = ? and useBegin LIKE ?",new String []{roomName,Qdate});
        while(c.moveToNext())
        {
            String beginTime = c.getString(0);
            String endTime = c.getString(1);
            RecordTime recordTime = new RecordTime(beginTime,endTime);
            recordTimes.add(recordTime);
        }
        c.close();
        db.close();
        return recordTimes;
    }
}
