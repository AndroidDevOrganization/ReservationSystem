package com.dbis.reservationsystem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.dbis.reservationsystem.sqlite.SQLiteDBHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
//    ToggleButton toggleButton;
//    Switch switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        // create SQLite DB
        createmyDB();

        // for navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        toggleButton = (ToggleButton) findViewById(R.id.toggle);
//        switcher = (Switch) findViewById(R.id.switcher);
//        final LinearLayout test = (LinearLayout) findViewById(R.id.test);
//        OnCheckedChangeListener listener = new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    //设置LinearLayout垂直布局
//                    test.setOrientation(LinearLayout.VERTICAL);
//                    toggleButton.setChecked(true);
//                    switcher.setChecked(true);
//                }
//                else {
//                    //设置LinearLayout水平布局
//                    test.setOrientation(LinearLayout.HORIZONTAL);
//                    toggleButton.setChecked(false);
//                    switcher.setChecked(false);
//                }
//            }
//        };
//        toggleButton.setOnCheckedChangeListener(listener);
//        switcher.setOnCheckedChangeListener(listener);
    }

    // function for goto back
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        } else if (id == R.id.nav_myReservation) {

        } else if (id == R.id.nav_collection) {

        } else if (id == R.id.nav_theme) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void userHandler(View source) {

    }

    public void logout(View source) {

    }

    public void clickBt1(View source) {
        Intent mainToBook =new Intent(MainActivity.this,bookdetailActivity.class);
        startActivity(mainToBook);
    }
}
