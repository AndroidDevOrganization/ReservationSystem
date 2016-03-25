package com.dbis.reservationsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.dbis.reservationsystem.UI.TimeTableModel;
import com.dbis.reservationsystem.UI.TimeTableView;

public class TimeTableActivity extends Activity {
    private TimeTableView mTimaTableView;
    private List<TimeTableModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        addList();
        //
        mTimaTableView.startTimeTable(new String[]{"3.19","3.20","3.21","3.22","3.23","3.24","3.25"},mList);

    }

    private void addList() {
        mList.add(new TimeTableModel(0, 1, 2, 1, "8:20", "10:10", "张莹",
                "张莹", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 3, 4, 1, "8:20", "10:10", "温延龙",
                "温延龙", "会议室545", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 1, "8:20", "10:10", "宫晓利",
                "宫晓利", "会议室523", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 2, "8:20", "10:10", "李忠伟",
                "李忠伟", "会议室448", "2-13"));
        mList.add(new TimeTableModel(0, 8, 9, 2, "8:20", "10:10", "温延龙",
                "温延龙", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 1, 2, 3, "8:20", "10:10", "李忠伟",
                "李忠伟", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 6, 7, 3, "8:20", "10:10", "温延龙",
                "温延龙", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 3, 5, 4, "8:20", "10:10", "张莹",
                "张莹", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 8, 9, 4, "8:20", "10:10", "李忠伟",
                "李忠伟", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 3, 5, 5, "8:20", "10:10", "张莹",
                "张莹", "会议室553", "2-13"));
        mList.add(new TimeTableModel(0, 6, 8, 5, "8:20", "10:10", "李忠伟",
                "李忠伟", "会议室553", "2-13"));
    }
    //注意按钮的叠放次序，相对布局下应该叠放在最上层才有效果
    public void btnBackToBook(View v)
    {
        onBackPressed();
    }
}
