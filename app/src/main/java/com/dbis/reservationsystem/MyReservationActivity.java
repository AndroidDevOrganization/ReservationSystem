package com.dbis.reservationsystem;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dbis.reservationsystem.Entity.MyReservation;
import com.dbis.reservationsystem.HTTPUtil.PostManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyReservationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView mRecyclerView;
    private List<MyReservation> mrlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mrlist = PostManager.Booking(getApplicationContext());
        //mrlist = new DBManager(this).getMyReservationList();

        // for recyclerView of room list
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyReservationRecyclerViewAdapter(this, mrlist));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // for navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_myReservation);
        navigationView.setNavigationItemSelectedListener(this);

    }

    // function for goto back
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
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

    public static class MyReservationRecyclerViewAdapter
            extends RecyclerView.Adapter<MyReservationRecyclerViewAdapter.ViewHolder> {

        private TypedValue mTypedValue = new TypedValue();
        //private int mBackground;            // color of background
        private List<MyReservation> mValues;  // list of item details

        public static class ViewHolder extends RecyclerView.ViewHolder {
            // define bundle to transfer between two activities.
            public Bundle mBundle = new Bundle();

            public final View mView;

            public final TextView myres_address, myres_username, myres_description, myres_state, myres_meetingTime;

            public ViewHolder(View view) {
                super(view);
                mView = view;

                //myres_time = (TextView) view.findViewById(R.id.myres_time);
                myres_address = (TextView) view.findViewById(R.id.myres_address);
                myres_username = (TextView) view.findViewById(R.id.myres_username);
                myres_description = (TextView) view.findViewById(R.id.myres_description);
                myres_state = (TextView) view.findViewById(R.id.myres_state);
                myres_meetingTime = (TextView) view.findViewById(R.id.myres_meetingTime);
            }
        }

        public MyReservation getValueAt(int position) {
            return mValues.get(position);
        }

        public MyReservationRecyclerViewAdapter(Context context, List<MyReservation> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            //mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item, parent, false);
            //view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            MyReservation tmpMyResItem = mValues.get(position);
            //holder.myres_time.setText(tmpMyResItem.getBookingTime());
            holder.myres_username.setText(tmpMyResItem.getUserName());
            holder.myres_address.setText(tmpMyResItem.getRoomName());
            holder.myres_description.setText(tmpMyResItem.getDescription());
            String [] dateAndBeginTime = tmpMyResItem.getUseBeginTime().split("T");
            String [] beginTime = dateAndBeginTime[1].split(":");
            String [] endTime = tmpMyResItem.getUseEndTime().split("T")[1].split(":");
            holder.myres_meetingTime.setText(dateAndBeginTime[0] + ", " + Integer.parseInt(beginTime[0]) + ":00-" + Integer.parseInt(endTime[0]) + ":00");
            holder.myres_state.setText(tmpMyResItem.getState());

            // put parameters to transfer
            holder.mBundle.putInt("id", tmpMyResItem.getId());
            holder.mBundle.putString("from","MyReservation");
            holder.mBundle.putString("room_name", tmpMyResItem.getRoomName());
            holder.mBundle.putString("user_name", tmpMyResItem.getUserName());
            holder.mBundle.putString("date", dateAndBeginTime[0]);
            holder.mBundle.putString("begin_time", beginTime[0] + ":" + beginTime[1]);
            holder.mBundle.putString("end_time", endTime[0] + ":" + endTime[1]);
            holder.mBundle.putString("description", tmpMyResItem.getDescription());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateNow = new Date();
            String tmpNow = sdf.format(dateNow);
            String tmpBegin = dateAndBeginTime[0] + " " + dateAndBeginTime[1];
            if(tmpNow.compareTo(tmpBegin) >= 0) {
                holder.mView.setBackgroundResource(R.drawable.item_frame_outtime);
                holder.mView.setPadding(2,2,2,2);
                holder.mView.setOnClickListener(null);
            }else {
                holder.mView.setBackgroundResource(R.drawable.item_frame);
                holder.mView.setPadding(2,2,2,2);
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, BookDetailActivity.class);
                        intent.putExtras(holder.mBundle);

                        context.startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            finish();
        } else if (id == R.id.nav_myReservation) {
            if (this != MyReservationActivity.this) {
                Intent intent = new Intent(this, MyReservationActivity.class);
                this.startActivity(intent);
                finish();
            }
        } else if (id == R.id.nav_collection) {

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void userHandler(View source) {

    }

    public void logout(View source) {

    }

    public void clickBt1(View source) {
        Intent mainToBook =new Intent(MyReservationActivity.this,BookDetailActivity.class);
        startActivity(mainToBook);
    }
}
