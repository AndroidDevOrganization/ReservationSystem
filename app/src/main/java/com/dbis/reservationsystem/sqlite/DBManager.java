package com.dbis.reservationsystem.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waho on 2016/3/8.
 */
public class DBManager {
    private SQLiteDBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new SQLiteDBHelper(context);
    }
    public List<String> getAllRoomName()
    {
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
    public String  getLocationByName(String roomName)
    {
        db = helper.getReadableDatabase();
        String roomLocation = null ;
        Cursor c = db.rawQuery("select roomlocation from meetingRoomInfo where roomname = ?",new String[]{roomName});
        while(c.moveToNext())
            roomLocation = c.getString(0);

        c.close();
        db.close();
        return roomLocation;
    }
    public void insertIntoRecord(String roomName,String username ,String useBegin ,String useEnd ,String state ,String description ,String reservetime)
    {
        db = helper.getWritableDatabase();
        // do not to have ' ' outside the ?
        String sql = "insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
                "values(?,?,? ,? ,? ,? ,?)";
        db.execSQL(sql ,new Object[]{roomName, username ,useBegin ,useEnd ,state ,description ,reservetime});
        db.close();
    }
}
