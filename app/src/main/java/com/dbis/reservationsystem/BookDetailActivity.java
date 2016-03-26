package com.dbis.reservationsystem;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dbis.reservationsystem.Entity.RecordTime;
import com.dbis.reservationsystem.Entity.Teacher;
import com.dbis.reservationsystem.sqlite.DBManager;


//to fill the adaptor, need String[]

public class BookDetailActivity extends AppCompatActivity {

    private Spinner spnRoomName;
    private Spinner spnBeginTime;
    private Spinner spnEndTime;
    private TextView tvRoomLocation;
    private EditText etSupervisor;
    private EditText etMeetingDate;
    private EditText etDescription;
    private DBManager dbManager;
    private List<String > roomNames;
    private String[] namesToFill;
    private ArrayAdapter<String > spnAdapter;
    private ArrayAdapter<String > spnstartTimeAdapter;
    private ArrayAdapter<String > spnendTimeAdapter;
    private String roomNameNow;
    private boolean fromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int nameNum;
        String SroomName = "会议室553";
        String SbeginTime = "08:00";
        String SendTime = "09:00";
        String startTime [] ={"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"};
        String endTime[] = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.dbManager = new DBManager(this);
        this.spnRoomName = (Spinner)findViewById(R.id.spSex);
        this.tvRoomLocation = (TextView)findViewById(R.id.tvLocation);
        this.etSupervisor = (EditText) findViewById(R.id.etSupervisor);
        this.etMeetingDate = (EditText) findViewById(R.id.etMeetingDate);
        this.spnBeginTime = (Spinner) findViewById(R.id.spBeginTime);
        this.spnEndTime = (Spinner) findViewById(R.id.spEndTime);
        this.etDescription = (EditText) findViewById(R.id.etDescription);

        // get the parameters delivered from the MainActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.getString("from").equals("Main"))
        {
            fromMain = true;
            SroomName = bundle.getString("room_name");
            tvRoomLocation.setText(bundle.getString("location"));
            etSupervisor.setText(Teacher.getName());
        }
        else if(bundle.getString("from").equals("MyReservation"))
        {
            fromMain = false;
            SroomName = bundle.getString("room_name");
            etSupervisor.setText(bundle.getString("user_name"));
            etMeetingDate.setText(bundle.getString("date"));
            SbeginTime = bundle.getString("begin_time");
            SendTime = bundle.getString("end_time");
            etDescription.setText(bundle.getString("description"));
        }


        roomNames = dbManager.getAllRoomName();
        nameNum = roomNames.size();
        namesToFill = new String [nameNum];

        for (int i = 0;i < nameNum ;i++)
            namesToFill[i] = roomNames.get(i);
        spnAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,namesToFill);
        // define the mode by ourselves
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRoomName.setAdapter(spnAdapter);

        spnRoomName.setSelection(spnAdapter.getPosition(SroomName));
        // bind the listener
        spnRoomName.setOnItemSelectedListener(new RoomNameItemSelectedListener());
        //adjust the style for startTime
        spnstartTimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,startTime);
        spnstartTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBeginTime.setAdapter(spnstartTimeAdapter);
        spnBeginTime.setSelection(spnAdapter.getPosition(SbeginTime));
        //for endTime
        spnendTimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,endTime);
        spnendTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEndTime.setAdapter(spnendTimeAdapter);
        spnEndTime.setSelection(spnAdapter.getPosition(SendTime));

        final Calendar c =Calendar.getInstance();
        etMeetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(BookDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat dfChosen = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat dfNow = new SimpleDateFormat("yyyy-MM-dd");
                        //compare if the chose date is earlier than now
                        if (dfChosen.format(c.getTime()).compareTo(dfNow.format(new Date())) < 0)
                            Toast.makeText(getApplicationContext(), "无法预约该天，请重新选择日期~", Toast.LENGTH_LONG).show();
                        else
                            etMeetingDate.setText(dfChosen.format(c.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class RoomNameItemSelectedListener implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //get the roomName, will also be triggered at first ,do not have to worry about the initializing
            roomNameNow = parent.getItemAtPosition(position).toString();
            String roomLocation = dbManager.getLocationByName(roomNameNow);

            tvRoomLocation.setText(roomLocation);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.Save:
                    if(fromMain) {
                        String rDate = etMeetingDate.getText().toString();
                        String username = etSupervisor.getText().toString();
                        String useBegin = rDate + " " + spnBeginTime.getSelectedItem().toString() + ":00";
                        String useEnd = rDate + " " + spnEndTime.getSelectedItem().toString() + ":00";
                        String state = "1";
                        String description = etDescription.getText().toString();

                        //can't allow the null rDate
                        if (rDate.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "请选择预约日期", Toast.LENGTH_LONG).show();
                            break;
                        }

                        //to test if have conflicts
                        List<RecordTime> recordTimes = dbManager.getRecordTimebyRoomNameAndDate(roomNameNow, rDate);
                        RecordTime recordNow = new RecordTime(useBegin, useEnd);
                        boolean haveConflict1 = false;
                        for (RecordTime recordTime : recordTimes) {
                            if (haveConflict(recordTime, recordNow))
                                haveConflict1 = true;
                        }
                        if (haveConflict1)
                            Toast.makeText(getApplicationContext(), "该天本时间段已被预约，请重新选择预约时间或日期", Toast.LENGTH_LONG).show();
                        else {
                            if (username.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "请填写负责人信息", Toast.LENGTH_LONG).show();
                                break;
                            }
                            //to get the now date
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            String reserveTime = df.format(new Date());// new Date()为获取当前系统时间
                            dbManager.insertIntoRecord(roomNameNow, username, useBegin, useEnd, state, description, reserveTime);
                            Toast.makeText(getApplicationContext(), "预约成功~", Toast.LENGTH_LONG).show();
                            goToMainActivity();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "我的预约方法~", Toast.LENGTH_LONG).show();
                    }
                    break;

            }

            return true;
        }
    };
    //must need this function to inflate menu . Called immediately when created.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bookdetail, menu);
        return true;
    }
    //to deal with the conflict reserve problem

    public boolean haveConflict(RecordTime t1,RecordTime t2)
    {
        String A1 = t1.getBeginTime();
        String A2 = t1.getEndTime();
        String B1 = t2.getBeginTime();
        String B2 = t2.getEndTime();
        String begin = getMax(A1, B1);
        String end = getMin(A2,B2);
        if (end.compareTo(begin) < 0 )
            return false;
        else
            return true;
    }
    public String getMax (String A1,String A2)
    {
        if(A1.compareTo(A2) < 0)
            return A2;
        else return A1;
    }
    public String getMin (String A1,String A2)
    {
        if(A1.compareTo(A2) < 0)
            return A1;
        else return A2;
    }
    public void goToMainActivity()
    {
        Intent bookToMain =new Intent(BookDetailActivity.this,MainActivity.class);
        startActivity(bookToMain);
        //close this Activity
        finish();
    }
    public void btnToTimeTable(View v)
    {
        Intent bookToTimeTable =new Intent(BookDetailActivity.this,TimeTableActivity.class);
        startActivity(bookToTimeTable);
    }
}
