package com.dbis.reservationsystem.Entity;

import com.dbis.reservationsystem.R;

import java.util.Random;

/**
 * Created by shi12 on 2016/3/12.
 */
public class MeetingRoom {
    private String roomName;
    private int capacity;
    private boolean isNeedConfirm;
    private int beginTime;
    private int endTime;
    private String description;
    private String location;
    private int authorityId;
    //private String authority;

    public static int getRandomRoomDrawable() {
        Random rand = new Random();
        switch (rand.nextInt(6)) {
            default:
            case 0:
                return R.drawable.auth_head_1;
            case 1:
                return R.drawable.auth_head_2;
            case 2:
                return R.drawable.auth_head_3;
            case 3:
                return R.drawable.auth_head_4;
            case 4:
                return R.drawable.auth_head_5;
            case 5:
                return R.drawable.auth_head_6;
        }
    }

    public MeetingRoom(String roomName, int capacity, boolean isNeedConfirm, int beginTime, int endTime, String description, String location) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.isNeedConfirm = isNeedConfirm;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
    }

    public MeetingRoom(String roomName, int capacity, boolean isNeedConfirm, int beginTime, int endTime, String description, String location, int authorityId) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.isNeedConfirm = isNeedConfirm;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
        this.authorityId = authorityId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isNeedConfirm() {
        return isNeedConfirm;
    }

    public void setIsNeedConfirm(boolean isNeedConfirm) {
        this.isNeedConfirm = isNeedConfirm;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/*
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
*/

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
