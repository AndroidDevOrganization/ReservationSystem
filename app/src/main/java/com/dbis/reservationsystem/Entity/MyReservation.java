package com.dbis.reservationsystem.Entity;

/**
 * Created by CocoSong on 2016/3/15.
 */
public class MyReservation {

    private String roomname;
    private String username;
    private String state;
    private String description;
    private String useBegin;
    public MyReservation(String roomname,String username,String useBegin,String state,String description){
        this.roomname=roomname;
        this.username=username;
        this.state=state;
        this.description=description;
        this.useBegin=useBegin;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUseBegin() {
        return useBegin;
    }

    public void setUseBegin(String useBegin) {
        this.useBegin = useBegin;
    }
}
