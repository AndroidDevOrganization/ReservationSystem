package com.dbis.reservationsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dbis.reservationsystem.sqlite.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//to fill the adaptor, need String[]
public class bookDetailActivity extends AppCompatActivity {
    private Spinner spnRoomName;
    private TextView tvRoomLocation;
    private EditText etSupervisor;
    private EditText etMeetingDate;
    private EditText etBeginTime;
    private EditText etEndTime;
    private EditText etDescription;
    private ImageView ivSave;
    private DBManager dbManager;
    private List<String > roomNames;
    private String[] namesToFill;
    private ArrayAdapter<String > spnAdapter;
    private String roomNameNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int nameNum;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.dbManager = new DBManager(this);
        this.spnRoomName = (Spinner)findViewById(R.id.spSex);
        this.tvRoomLocation = (TextView)findViewById(R.id.tvLocation);
        this.etSupervisor = (EditText) findViewById(R.id.etSupervisor);
        this.etMeetingDate = (EditText) findViewById(R.id.etMeetingDate);
        this.etBeginTime = (EditText) findViewById(R.id.etBeginTime);
        this.etEndTime = (EditText) findViewById(R.id.etEndTime);
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
        // bind the listener
        spnRoomName.setOnItemSelectedListener(new RoomNameItemSelectedListener());

        ivSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String rDate = etMeetingDate.getText().toString();
                String username = etSupervisor.getText().toString();
                String useBegin = rDate + " " + etBeginTime.getText().toString();
                String useEnd = rDate + " " + etEndTime.getText().toString();
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
}
