package com.example.ivonneortega.myfitnessapp.Routines;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.DatabaseTableNames;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoutinesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RoutinesFragment.RoutinesFragmentInterface,
        WeeksFragment.WeekFragmentListener{

    private RecyclerView mRecyclerview;
    private String[] mMondaysShort, mMondaysLong;
    private int[] mCalendarArray;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view = findViewById(R.id.fragment_container);

        RoutinesFragment routineFragment = RoutinesFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, routineFragment)
                .commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.routines, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickedOnRoutine(final String id) {

        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        builder.setTitle("Set a workout")
                .setItems(R.array.alertDialogEditRoutineOrSetRoutine, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                WeeksFragment routineFragment = WeeksFragment.newInstance(id);
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, routineFragment)
                                        .commit();
                                break;
                            case 1:

                                try {
                                    getListOfMondays();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                final View update_layout = getLayoutInflater().inflate(
                                    R.layout.set_routine_start_day, null);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle("Choose a start day");
                                final Spinner spinner = (Spinner) update_layout.findViewById(R.id.spinner);

                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            setRoutine(spinner.getSelectedItemPosition(),id);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoutinesActivity.this,
                                        android.R.layout.simple_spinner_dropdown_item, mMondaysShort);

                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);


                                builder.setView(update_layout);



                                AlertDialog alert = builder.create();
                                alert.show();
                                break;
                        }
                    }
                });
        builder.create();
        builder.show();
    }

    public void setRoutine(int which, String id) throws ParseException {
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE,mCalendarArray[which]);


        Routines routines = FitnessDBHelper.getInstance(this).getRoutineById(id);
        for (Week week: routines.getWeeks()) {
            for (String day: DatabaseTableNames.LIST_OF_DAYS) {
                String newDay = (String)(formattedDate.format(c.getTime()));

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date startDate = df.parse(newDay);

                newDay = startDate.toString();
                FitnessDBHelper.getInstance(this).insertUserDay(newDay,week.getDayHashMap().get(day).getWeekId(),null);
                c.add(Calendar.DATE,1);

                System.out.println(newDay);
            }

        }
        Toast.makeText(this, "Routine set as current routine", Toast.LENGTH_SHORT).show();
    }



    public void getListOfMondays() throws ParseException {

        Date hoy = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();

        mCalendarArray = new int[10];
        mMondaysShort = new String[10];
        mMondaysLong = new String[10];

//            c.add(Calendar.DATE, 1);
        String newDay = (String)(formattedDate.format(c.getTime()));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = df.parse(newDay);

        newDay = startDate.toString();
        String newDayShort = newDay.substring(0,3);

        int index=0;
        while(!newDayShort.equalsIgnoreCase(DatabaseTableNames.LIST_OF_DAYS_THREE_LETTERS[index]) && index<7) {
            index++;
        }
        index = 7-index;

        c.add(Calendar.DATE,index);
        newDay = (String)(formattedDate.format(c.getTime()));
        startDate = df.parse(newDay);
        newDay = startDate.toString();
        mMondaysLong[0] = newDay;
        mCalendarArray[0] = index;
        mMondaysShort[0] = newDay.substring(0,10);
        for(int i=1;i<10;i++)
        {
            c.add(Calendar.DATE,7);
            newDay = (String)(formattedDate.format(c.getTime()));
            startDate = df.parse(newDay);
            newDay = startDate.toString();
            mMondaysLong[i] = newDay;
            mMondaysShort[i] = newDay.substring(0,10);
            mCalendarArray[i] = 7;
        }

    }



    @Override
    public void clickedOnWeek(String id) {
        Intent intent = new Intent(RoutinesActivity.this,ExercisesPerDaysActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }


}
