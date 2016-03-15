package com.dbis.reservationsystem.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBHelper extends SQLiteOpenHelper {

	public SQLiteDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public SQLiteDBHelper(Context context) {
		super(context, "reservedb", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// when creating database ,creating default tables automatically
		arg0.execSQL("create table user(id integer primary key autoincrement,username text(30),password text(30),picture text(30),realname text(30)," +
						"gender text(10),department text(30)) ");
		arg0.execSQL("create table meetingRoomInfo(id integer primary key autoincrement,roomname text(30),roomlocation text(30),capacity integer," +
				"authority integer ,beginTime integer ,endTime integer ,description text(50),needauthentication integer) ");
		arg0.execSQL("create table reserveRecord(id integer primary key autoincrement,roomname text(30),username text(30),useBegin text(30),useEnd text(30) ,state text(30)," +
				"description text(50),reservetime text(30)) ");
		arg0.execSQL("insert into meetingRoomInfo(roomname,roomlocation,capacity,authority,beginTime ,endTime ,description,needauthentication) " +
				"values('会议室553','信息东楼',40 ,1 ,1 ,24 ,'会议室音像设备齐全，房间宽敞明亮，干净整洁，足够进行大型会议，讲座等使用。总之，一句话棒棒哒哒哒哒哒哒',0)");
		arg0.execSQL("insert into meetingRoomInfo(roomname,roomlocation,capacity,authority,beginTime ,endTime ,description,needauthentication) " +
				"values('会议室448','信息东楼',12 ,1 ,1 ,24 ,'',1)");
		arg0.execSQL("insert into meetingRoomInfo(roomname,roomlocation,capacity,authority,beginTime ,endTime ,description,needauthentication) " +
				"values('会议室523','信息东楼',70 ,1 ,1 ,24 ,'',0)");
		arg0.execSQL("insert into meetingRoomInfo(roomname,roomlocation,capacity,authority,beginTime ,endTime ,description,needauthentication) " +
				"values('会议室545','信息东楼',40 ,1 ,1 ,24 ,'会议室干净整体，宽敞明亮，适合小型会议及各种例会。',1)");
		arg0.execSQL("insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
				"values('会议室553','大鹏哥','2015-11-21 08:00:00' ,'2015-11-21 15:00:00' ,'1' ,'周二例会' ,'2015-11-20 15:55:01')");
		arg0.execSQL("insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
				"values('会议室448','张三','2015-11-27 10:00:00' ,'2015-11-27 12:00:00' ,'1' ,'中科院山世光老师' ,'2015-11-20 21:42:23')");
		arg0.execSQL("insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
				"values('会议室523','李四','2015-12-01 08:00:00' ,'2015-12-01 12:00:00' ,'1' ,'周二例会' ,'2015-11-23 09:54:51')");
		arg0.execSQL("insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
				"values('会议室545','王五','2015-12-04 09:00:00' ,'2015-12-04 12:00:00' ,'1' ,'学位分委员会' ,'2015-11-27 15:22:01')");
		arg0.execSQL("insert into reserveRecord(roomname,username,useBegin,useEnd,state ,description ,reservetime) " +
				"values('会议室553','敖广','2015-11-27 19:00:00' ,'2015-11-27 22:00:00' ,'1' ,'组会' ,'2015-11-27 18:02:21')");
		arg0.execSQL("insert into user(username ,password ,picture ,realname ,gender ,department) " +
				"values('123','123','' ,'敖广' ,'男' ,'计控学院')");
	//	arg0.execSQL("insert into user(username,password) values('tom','t111')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
