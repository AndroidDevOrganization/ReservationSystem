package com.dbis.reservationsystem.Entity;

/**
 * Created by waho on 2016/3/16.
 */
public class RecordTime {
    private String beginTime;
    private String endTime;

    public RecordTime(String beginTime, String endTime) {
        this.endTime = endTime;
        this.beginTime = beginTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
