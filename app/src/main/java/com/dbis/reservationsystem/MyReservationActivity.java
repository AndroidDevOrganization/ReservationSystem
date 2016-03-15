package com.dbis.reservationsystem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyReservationActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        SimpleAdapter adapter=new SimpleAdapter(this,getData(),R.layout.item,new String[]{"booking_date","booking_state","booking_address","director","meeting_date","meeting_time","description"},new int[]{R.id.booking_date,R.id.booking_state,R.id.booking_address,R.id.director,R.id.meeting_date,R.id.meeting_time,R.id.description});
        setListAdapter(adapter);
        ListView lv=getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TextView tv= (TextView) view.findViewById(R.id.booking_date);
//                Toast.makeText(getApplicationContext(),tv.getText(),Toast.LENGTH_SHORT).show();
                //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                Intent intent =new Intent(MyReservationActivity.this, BookDetailActivity.class);

                //用Bundle携带数据
                Bundle bundle=new Bundle();
                //传递参数
                TextView tv1=(TextView)view.findViewById(R.id.booking_address);
                TextView tv2=(TextView)view.findViewById(R.id.director);
                TextView tv3=(TextView)view.findViewById(R.id.meeting_date);
                TextView tv4=(TextView)view.findViewById(R.id.meeting_time);
                TextView tv5=(TextView)view.findViewById(R.id.description);
                bundle.putString("booking_address", (String) tv1.getText());
                bundle.putString("director", (String) tv2.getText());
                bundle.putString("meeting_date", (String) tv3.getText());
                bundle.putString("meeting_time", (String) tv4.getText());
                bundle.putString("description", (String) tv5.getText());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    public List<Map<String,Object>> getData()
    {
        Map<String,Object> value =new HashMap<String,Object>();
        List<Map<String,Object>> lm =new ArrayList<Map<String,Object>>();
        value.put("booking_date","20160303");
        value.put("booking_state","已预约");
        value.put("booking_address","主楼小礼堂");
        value.put("director","张莹");
        value.put("meeting_date","20160303");
        value.put("meeting_time","下午3点-5点");
        value.put("description", "灯光亮起来音乐响起来！！！");
        lm.add(value);

        value =new HashMap();
        value.put("booking_date","20160304");
        value.put("booking_state","已预约");
        value.put("booking_address","二主");
        value.put("director","张莹");
        value.put("meeting_date","20160304");
        value.put("meeting_time","下午2点-5点");
        value.put("description","灯光亮起来音乐响起来！！！");
        lm.add(value);

        value =new HashMap();
        value.put("booking_date","20160305");
        value.put("booking_state","已预约");
        value.put("booking_address","计控楼");
        value.put("director","张莹");
        value.put("meeting_date","20160305");
        value.put("meeting_time","下午3点-4点");
        value.put("description","灯光亮起来音乐响起来！！！");
        lm.add(value);

        return  lm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
