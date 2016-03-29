package com.dbis.reservationsystem;


import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbis.reservationsystem.Entity.Teacher;
import com.dbis.reservationsystem.Entity.MeetingRoom;
import com.dbis.reservationsystem.sqlite.DBManager;
import com.dbis.reservationsystem.sqlite.SQLiteDBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView mRecyclerView;
    private List<MeetingRoom> mrlist;
    private long exitTime=0;//按两次退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create SQLite DB
        createmyDB();
        mrlist = new DBManager(this).getMeetingRoomList();

        // for recyclerView of room list
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MeetingRoomRecyclerViewAdapter(this, mrlist));

        // for navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(this);

    }

    // function for goto back
    //2s内按两次退出 3.25 by WuChen
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再次按下返回键退出预约系统",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return ;
            } else {
                super.onBackPressed();
            }
        }
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
        } else if (id == R.id.abouts) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MeetingRoomRecyclerViewAdapter
            extends RecyclerView.Adapter<MeetingRoomRecyclerViewAdapter.ViewHolder> {

        private TypedValue mTypedValue = new TypedValue();
        //private int mBackground;            // color of background
        private List<MeetingRoom> mValues;  // list of item details

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // define bundle to transfer between two activities.
            public Bundle mBundle = new Bundle();

            public final View mView;
            public final ImageView mAuthority;
            public final TextView mName, mLocation, mCapacity, mOpentime, mDescription, mConfirm;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mName = (TextView) view.findViewById(R.id.room_name);
                mLocation = (TextView) view.findViewById(R.id.room_location);
                mCapacity = (TextView) view.findViewById(R.id.room_capacity);
                mOpentime = (TextView) view.findViewById(R.id.room_openTime);
                mDescription = (TextView) view.findViewById(R.id.room_description);
                mAuthority = (ImageView) view.findViewById(R.id.room_authority);
                mConfirm = (TextView) view.findViewById(R.id.room_confirm);
            }
        }

        public MeetingRoom getValueAt(int position) {
            return mValues.get(position);
        }

        public MeetingRoomRecyclerViewAdapter(Context context, List<MeetingRoom> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            //mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.room_item, parent, false);
            //view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            MeetingRoom tmpRoomItem = mValues.get(position);
            holder.mName.setText(tmpRoomItem.getRoomName());
            holder.mLocation.setText(tmpRoomItem.getLocation());
            holder.mCapacity.setText(tmpRoomItem.getCapacity()+"");
            holder.mOpentime.setText("每天，" + tmpRoomItem.getBeginTime() + ":00 - " + tmpRoomItem.getEndTime() + ":00");
            holder.mDescription.setText(tmpRoomItem.getDescription());
            if(tmpRoomItem.isNeedConfirm())
                holder.mConfirm.setVisibility(View.VISIBLE);
            else
                holder.mConfirm.setVisibility(View.INVISIBLE);

            // put parameters to transfer
            holder.mBundle.putString("from","Main");
            holder.mBundle.putString("room_name", tmpRoomItem.getRoomName());
            holder.mBundle.putString("location", tmpRoomItem.getLocation());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, BookDetailActivity.class);
                    intent.putExtras(holder.mBundle);

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mAuthority.getContext())
                    .load(MeetingRoom.getRandomRoomDrawable())
                    .fitCenter()
                    .into(holder.mAuthority);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    // create SQLite DB
    public void createmyDB() {
        SQLiteDBHelper sdh = new SQLiteDBHelper(getApplicationContext());
        //must do this ,to produce the database file
        SQLiteDatabase sdb = sdh.getWritableDatabase();

        sdb.close();
        sdh.close();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (this != MainActivity.this) {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                finish();
            }
        } else if (id == R.id.nav_myReservation) {
            Intent intent = new Intent(this, MyReservationActivity.class);
            this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_collection) {

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        return true;
    }

    public void userHandler(View source) {
        Intent intent=new Intent(MainActivity.this,MyReservationActivity.class);
        startActivity(intent);
    }

    public void logout(View source) {
        SharedPreferences sp=getSharedPreferences("UserInfo",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("login","false");
        ed.putString("account","");
        ed.putString("password","");
        ed.putString("id","");
        ed.putString("name","");
        ed.commit();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public String  testLogin(){
        SharedPreferences sp=getSharedPreferences("UserInfo",MODE_PRIVATE);
        String result=sp.getString("login","false");
        return result;
    }
    protected void onResume() {
        super.onResume();

        if(!testLogin().equals("true")){
            Toast.makeText(getApplicationContext(),"请先登录~",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            //必须结束，否则在未登录情况下会有无法退出的情况
            finish();
        }else {
            SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
            Teacher.setAccount(sp.getString("account", ""));
            Teacher.setPassword(sp.getString("password", ""));
            Teacher.setId(sp.getString("id", ""));
            Teacher.setName(sp.getString("name",""));
        }
    }
}
