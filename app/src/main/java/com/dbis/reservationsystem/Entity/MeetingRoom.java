package com.dbis.reservationsystem.Entity;

import java.util.Date;
import java.util.Random;

/**
 * Created by shi12 on 2016/3/12.
 */
public class MeetingRoom {
    private String roomName;
    private int capacity;
    private boolean isNeedConfirm;
    private Date beginTime;
    private Date endTime;
    private String description;
    private String location;
    private String authority;

    public static int getRandomRoomDrawable() {
        Random rand = new Random();
        switch (rand.nextInt(5)) {
            case 0:
                return
        }
    }

    public MeetingRoom(String roomName, int capacity, boolean isNeedConfirm, Date beginTime, Date endTime, String description, String location) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.isNeedConfirm = isNeedConfirm;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
    }

    public MeetingRoom(String roomName, int capacity, boolean isNeedConfirm, Date beginTime, Date endTime, String description, String location, String authority) {
        this.roomName = roomName;
        this.capacity = capacity;
        this.isNeedConfirm = isNeedConfirm;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.description = description;
        this.location = location;
        this.authority = authority;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
