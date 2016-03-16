package com.dbis.reservationsystem.Entity;

import java.util.Date;

/**
 * Created by CocoSong on 2016/3/15.
 */
public class MyReservation {

    private String roomName;
    private String userName;
    private String state;
    private String description;
    private String useBeginTime;
    private String useEndTime;
    private String bookingTime;

    public MyReservation(String roomName, String userName, String state, String description, String useBeginTime, String useEndTime, String bookingTime) {
        this.roomName = roomName;
        this.userName = userName;
        this.state = state;
        this.description = description;
        this.useBeginTime = useBeginTime;
        this.useEndTime = useEndTime;
        this.bookingTime = bookingTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUseBeginTime() {
        return useBeginTime;
    }

    public void setUseBeginTime(String useBeginTime) {
        this.useBeginTime = useBeginTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(String useEndTime) {
        this.useEndTime = useEndTime;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }
}
