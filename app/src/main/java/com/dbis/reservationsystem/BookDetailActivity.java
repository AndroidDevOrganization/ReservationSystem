package com.dbis.reservationsystem;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.dbis.reservationsystem.Entity.MeetingRoom;
import com.dbis.reservationsystem.Entity.Teacher;
import com.dbis.reservationsystem.HTTPUtil.PostUtil;


//to fill the adaptor, need String[]

public class BookDetailActivity extends AppCompatActivity {

    private boolean fromMain;
    private int roomId;
    private int BookingId;
    private int inAdvancedTime;
    private Spinner spnBeginTime;
    private Spinner spnEndTime;
    private Spinner spnRoomName;
   // private TextView tvRoomName;
    private TextView tvRoomLocation;
    private EditText etSupervisor;
    private EditText etMeetingDate;
    private EditText etDescription;
    private ArrayAdapter<String > spnAdapter;
    private ArrayAdapter<String > spnstartTimeAdapter;
    private ArrayAdapter<String > spnendTimeAdapter;
    private String roomNameNow;
    private String[] namesToFill;
    private String insertResult;
    private String deleteResult;
    private String useBegin;//预约开始时间
    private String useEnd;//预约结束时间
    private String description;//描述文字
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "正在加载，请耐心等待……", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1) {
                int insertR = Integer.parseInt(insertResult);
                switch (insertR) {
                    case -1:
                        Toast.makeText(getApplicationContext(), "请检查你的网络状况", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(getApplicationContext(), "预约提交成功~", Toast.LENGTH_SHORT).show();
                        goToMyResActivity();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        //代码逻辑无误下不会返回该提示，已经在安卓端判断过
                        Toast.makeText(getApplicationContext(), "开始时间不能比会议室的开门时间早", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //不会出现该提示
                        Toast.makeText(getApplicationContext(), "结束时间不能比会议室的关门时间晚", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "该时段已被占用，请查看该会议室的预约情况或更换会议室", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "请不要重复预约", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        //不会出现该提示
                        Toast.makeText(getApplicationContext(), "不能预约过去的日期", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getApplicationContext(), "你没有预约该会议室的权限", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getApplicationContext(), "只能提前预约" + inAdvancedTime +"天以内时间", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else if(msg.what == 2) {
                int deleteR = Integer.parseInt(deleteResult);
                if (deleteR == 0) {
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    goToMyResActivity();
                } else
                    Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

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

        // this.tvRoomName = (TextView)findViewById(R.id.tvRoomName);
        this.tvRoomLocation = (TextView)findViewById(R.id.tvLocation);
        this.etSupervisor = (EditText) findViewById(R.id.etSupervisor);
        this.etMeetingDate = (EditText) findViewById(R.id.etMeetingDate);
        this.spnRoomName = (Spinner) findViewById(R.id.spRoomNames);
        this.spnBeginTime = (Spinner) findViewById(R.id.spBeginTime);
        this.spnEndTime = (Spinner) findViewById(R.id.spEndTime);
        this.etDescription = (EditText) findViewById(R.id.etDescription);

        // get the parameters delivered from the MainActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle.getString("from").equals("Main"))
        {
            fromMain = true;
            inAdvancedTime = bundle.getInt("inAdvanceTime");
            roomId = bundle.getInt("rid");
            SroomName = bundle.getString("room_name");
            tvRoomLocation.setText(bundle.getString("location"));
            etSupervisor.setText(Teacher.getName());
        } else if(bundle.getString("from").equals("MyReservation")) {
            fromMain = false;
            BookingId = bundle.getInt("id");
            SroomName = bundle.getString("room_name");
            tvRoomLocation.setText(bundle.getString("location"));
            etSupervisor.setText(bundle.getString("user_name"));
            etMeetingDate.setText(bundle.getString("date"));
            SbeginTime = bundle.getString("begin_time");
            SendTime = bundle.getString("end_time");
            etDescription.setText(bundle.getString("description"));
        }

        // Menu item click 的監聽事件一樣要設定在 setSupportActionBar 才有作用
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        nameNum = Teacher.getMrlist().size();
        namesToFill = new String [nameNum];

        for (int i = 0;i < nameNum ;i++)
            namesToFill[i] = Teacher.getMrlist().get(i).getRoomName();
        spnAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,namesToFill);
        // define the mode by ourselves
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRoomName.setAdapter(spnAdapter);

        //初始化的名字是从上面承接而来的参数
        spnRoomName.setSelection(spnAdapter.getPosition(SroomName));
        // bind the listener
        spnRoomName.setOnItemSelectedListener(new RoomNameItemSelectedListener());
        //adjust the style for startTime
        spnstartTimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,startTime);
        spnstartTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBeginTime.setAdapter(spnstartTimeAdapter);
        spnBeginTime.setSelection(spnstartTimeAdapter.getPosition(SbeginTime));
        //for endTime
        spnendTimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,endTime);
        spnendTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEndTime.setAdapter(spnendTimeAdapter);
        spnEndTime.setSelection(spnendTimeAdapter.getPosition(SendTime));

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

    class RoomNameItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //get the roomName, will also be triggered at first ,do not have to worry about the initializing
            roomNameNow = parent.getItemAtPosition(position).toString();
            String roomLocation = null;
            for (MeetingRoom mroom:Teacher.getMrlist()) {
                if(mroom.getRoomName().equals(roomNameNow)) {
                    roomLocation = mroom.getLocation();
                    roomId = mroom.getRid();
                }
            }

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
                        useBegin = rDate + " " + spnBeginTime.getSelectedItem().toString() + ":00";
                        useEnd = rDate + " " + spnEndTime.getSelectedItem().toString() + ":00";
                        String state = "1";
                        //备注不能为空
                        description = etDescription.getText().toString();

                        //can't allow the null rDate
                        if (rDate.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "请选择预约日期", Toast.LENGTH_LONG).show();
                            break;
                        }
                        if(description.isEmpty()){
                            Toast.makeText(getApplicationContext(), "请填写备注信息", Toast.LENGTH_LONG).show();
                            break;
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                //handler.sendEmptyMessage(0);
                                String url = "http://202.113.25.200:8090/api/insertbooking";
                                String params = "begintime=" + useBegin + "&endTime="+ useEnd +"&teacherid=" + Teacher.getId() + "&meetingroomid="+ roomId +"&description=" + description;
                                insertResult = PostUtil.sendPost(url, params);
                                handler.sendEmptyMessage(1);
                            }
                        }.start();
                    } else {
                        Toast.makeText(getApplicationContext(), "我的预约方法~", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.Delete:
                    if(fromMain) {
                        break;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailActivity.this);
                        builder.setTitle("删除预约");
                        builder.setMessage("是否确定删除此预约？");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        String url = "http://202.113.25.200:8090/api/deletebooking";
                                        String params = "bookingid=" + BookingId  ;
                                        deleteResult = PostUtil.sendPost(url, params);
                                       // System.out.println("delete = " + deleteResult);
                                        //System.out.println("booingid = " +BookingId);
                                        handler.sendEmptyMessage(2);
                                    }
                                }.start();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }


            }
            return true;
        }
    };

    //在这里对菜单项进行初始化
    //must need this function to inflate menu . Called immediately when created.
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_bookdetail, menu);
        //需要填充后menu才有元素
        if(fromMain)
            menu.findItem(R.id.Delete).setVisible(false);
        else
            menu.findItem(R.id.Save).setVisible(false);
        return true;
    }

    public void goToMyResActivity() {
        Intent bookToMyReservation =new Intent(BookDetailActivity.this,MyReservationActivity.class);
        startActivity(bookToMyReservation);
        //close this Activity
        finish();
    }

    public void btnToTimeTable(View v) {
        Intent bookToTimeTable =new Intent(BookDetailActivity.this,TimeTableActivity.class);

        bookToTimeTable.putExtra("roomName", spnRoomName.getSelectedItem().toString());
        startActivity(bookToTimeTable);
    }
}
