package com.dbis.reservationsystem;

import android.app.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dbis.reservationsystem.sqlite.DBManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//to fill the adaptor, need String[]
public class bookdetailActivity extends Activity {
    private Spinner spnRoomName;
    private Spinner spnBeginTime;
    private Spinner spnEndTime;
    private TextView tvRoomLocation;
    private EditText etSupervisor;
    private EditText etMeetingDate;
    private EditText etDescription;
    private ImageView ivSave;
    private DBManager dbManager;
    private List<String > roomNames;
    private String[] namesToFill;
    private ArrayAdapter<String > spnAdapter;
    private ArrayAdapter<String > spnstartTimeAdapter;
    private ArrayAdapter<String > spnendTimeAdapter;
    private String roomNameNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int nameNum;
        String startTime [] ={"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00"};
        String endTime[] = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"};

        super.onCreate(savedInstanceState);
        // set the system own bar free
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bookdetail);

        this.dbManager = new DBManager(this);
        this.spnRoomName = (Spinner)findViewById(R.id.spSex);
        this.tvRoomLocation = (TextView)findViewById(R.id.tvLocation);
        this.etSupervisor = (EditText) findViewById(R.id.etSupervisor);
        this.etMeetingDate = (EditText) findViewById(R.id.etMeetingDate);
        this.spnBeginTime = (Spinner) findViewById(R.id.spBeginTime);
        this.spnEndTime = (Spinner) findViewById(R.id.spEndTime);
        this.etDescription = (EditText) findViewById(R.id.etDescription);
        this.ivSave = (ImageView) findViewById(R.id.ivSave);

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
        //bind the listener
        spnRoomName.setOnItemSelectedListener(new RoomNameItemSelectedListener());
        //adjust the style for startTime
        spnstartTimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,startTime);
        spnstartTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBeginTime.setAdapter(spnstartTimeAdapter);
        //for endTime
        spnendTimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,endTime);
        spnendTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEndTime.setAdapter(spnendTimeAdapter);

        final Calendar c =Calendar.getInstance();
        etMeetingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(bookdetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year,monthOfYear,dayOfMonth);
                        SimpleDateFormat dfChosen = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat dfNow = new SimpleDateFormat("yyyy-MM-dd");
                        //compare if the chose date is earlier than now
                        if(dfChosen.format(c.getTime()).compareTo(dfNow.format(new Date())) <0)
                            Toast.makeText(getApplicationContext(),"无法预约该天，请重新选择日期~" , Toast.LENGTH_LONG).show();
                        else
                        etMeetingDate.setText(dfChosen.format(c.getTime()));
                    }
                },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        ivSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String rDate = etMeetingDate.getText().toString();
                String username = etSupervisor.getText().toString();
                String useBegin = rDate + " " + spnBeginTime.getSelectedItem().toString()+":00";
                String useEnd = rDate + " " + spnEndTime.getSelectedItem().toString()+":00";
                String state = "1";
                String description  = etDescription.getText().toString();
                //to get the now date
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String reserveTime = df.format(new Date());// new Date()为获取当前系统时间
                dbManager.insertIntoRecord(roomNameNow , username ,useBegin ,useEnd ,state ,description ,reserveTime);
                Toast.makeText(getApplicationContext(),"预约成功~" , Toast.LENGTH_LONG).show();
            }
        });
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
}
