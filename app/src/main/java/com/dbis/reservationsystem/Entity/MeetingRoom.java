package com.dbis.reservationsystem.Entity;

import com.dbis.reservationsystem.R;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by shi12 on 2016/3/12.
 */
public class MeetingRoom implements Serializable{
    private int rid;
    private String roomName;
    private int capacity;
    private boolean isNeedConfirm;
    private int beginTime;
    private int endTime;
    private String description;
    private String location;
    private int confirmTime;
    private int inAdvanceTime;
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

    public MeetingRoom(int rid, String roomName, int capacity, boolean isNeedConfirm, int beginTime, int endTime, String location, int confirmTime) {
        this.rid = rid;
        this.roomName = roomName;
        this.capacity = capacity;
        this.isNeedConfirm = isNeedConfirm;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.location = location;
        this.confirmTime = confirmTime;
    }

    public MeetingRoom(int rid, String roomName, int capacity, boolean isNeedConfirm, int beginTime, int endTime, String description,
                       String location, int confirmTime, int inAdvanceTime, int authorityId) {
        this.rid = rid;
        this.roomName = roomName;
        this.capacity = capacity;
        this.isNeedConfirm = isNeedConfirm;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
        this.confirmTime = confirmTime;
        this.inAdvanceTime = inAdvanceTime;
        this.authorityId = authorityId;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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

    public int getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(int confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getInAdvanceTime() {
        return inAdvanceTime;
    }

    public void setInAdvanceTime(int inAdvanceTime) {
        this.inAdvanceTime = inAdvanceTime;
    }
}
