package my.app.ivonneortega.myfitnessapp.Routines;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import my.app.ivonneortega.myfitnessapp.AddWorkout.AddWorkoutActivity;
import my.app.ivonneortega.myfitnessapp.Challenges.ChallengesActivity;
import my.app.ivonneortega.myfitnessapp.Data.Day;
import my.app.ivonneortega.myfitnessapp.Data.Routines;
import my.app.ivonneortega.myfitnessapp.Data.Week;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.DatabaseTableNames;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.Friends.FriendsActivity;
import my.app.ivonneortega.myfitnessapp.LoginActivity;
import my.app.ivonneortega.myfitnessapp.MainActivity;
import my.app.ivonneortega.myfitnessapp.R;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RoutinesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RoutinesFragment.RoutinesFragmentInterface,
        WeeksFragment.WeekFragmentListener,
        AddRoutineFragmentFromRoutineActivity.RoutineInterface,
        AddWeekWorkoutFragmentFromRoutineActivity.addWeekWorkoutInterface{

    private RecyclerView mRecyclerview;
    private String[] mMondaysShort, mMondaysLong;
    private int[] mCalendarArray;
    private FirebaseAuth mAuth;
    private String[] mListOfDays;

    private View view;
    private HashMap<String, Day> mWorkoutHashMap;
    private List<Week> mWeekList;
    private FloatingActionButton fab;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(my.app.ivonneortega.myfitnessapp.R.layout.activity_routines);
        Toolbar toolbar = (Toolbar) findViewById(my.app.ivonneortega.myfitnessapp.R.id.toolbar);
        setSupportActionBar(toolbar);


        mListOfDays = DatabaseTableNames.LIST_OF_DAYS;

        fab = (FloatingActionButton) findViewById(my.app.ivonneortega.myfitnessapp.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRoutine();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, my.app.ivonneortega.myfitnessapp.R.string.navigation_drawer_open, my.app.ivonneortega.myfitnessapp.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(my.app.ivonneortega.myfitnessapp.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view = findViewById(my.app.ivonneortega.myfitnessapp.R.id.fragment_container);

        RoutinesFragment routineFragment = RoutinesFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container, routineFragment)
                .commit();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(my.app.ivonneortega.myfitnessapp.R.menu.routines, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == my.app.ivonneortega.myfitnessapp.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_routines) {
            startActivity(new Intent(getApplication(),RoutinesActivity.class));
        } else if (id == R.id.nav_workouts) {
            startActivity(new Intent(getApplication(), AddWorkoutActivity.class));
        } else if (id == R.id.nav_cardio) {

        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(getApplication(), FriendsActivity.class));
        } else if (id == R.id.nav_challenges) {

            startActivity(new Intent(getApplication(), ChallengesActivity.class));
        } else if (id == R.id.nav_settings) {
            this.deleteDatabase(FitnessDBHelper.DATABASE_NAME);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
//                            startActivity(new Intent(getApplication(), LoginActivity.class));
//                            finish();
                        }
                    });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickedOnRoutine(final String id, final String name) {

        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        builder.setTitle("Set a workout")
                .setItems(my.app.ivonneortega.myfitnessapp.R.array.alertDialogEditRoutineOrSetRoutine, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case 0:
                                WeeksFragment routineFragment = WeeksFragment.newInstance(id, name);
                                getSupportFragmentManager().beginTransaction()
                                        .addToBackStack(null)
                                        .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container, routineFragment)
                                        .commit();
                                fab.setVisibility(View.GONE);
                                break;
                            case 1:

                                try {
                                    getListOfMondays();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                final View update_layout = getLayoutInflater().inflate(
                                    my.app.ivonneortega.myfitnessapp.R.layout.set_routine_start_day, null);
                                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle("Choose a start day");
                                final Spinner spinner = (Spinner) update_layout.findViewById(my.app.ivonneortega.myfitnessapp.R.id.spinner);

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

    public void setRoutine(final int which, final String thisId) throws ParseException {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to set this as you current routine? " +
                "This will override you current routine ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            setRoutineAfterUserSaidYes(which,thisId);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create();
        builder.show();

    }

    public void setRoutineAfterUserSaidYes(int which, String id) throws ParseException
    {
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE,mCalendarArray[which]);
        FitnessDBHelper db = FitnessDBHelper.getInstance(this);

        Routines routines = FitnessDBHelper.getInstance(this).getRoutineById(id);
        db.removeRoutineFromUserDay();
        for (Week week: routines.getWeeks()) {
            for (String day: DatabaseTableNames.LIST_OF_DAYS) {
                String newDay = (String)(formattedDate.format(c.getTime()));

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date startDate = df.parse(newDay);

                newDay = startDate.toString();
                if(db.doesTodayExitInDatabase(newDay)) {
                    db.updateWorkoutForToday(newDay,week.getDayHashMap().get(day).getWorkout().getId());
                }
                else {
                    System.out.println("WORKOUT ID: "+week.getDayHashMap().get(day).getWorkout().getId());
                    db.insertUserDay(newDay, week.getDayHashMap().get(day).getWorkout().getId(), null);
                }
                c.add(Calendar.DATE,1);
            }

        }

        Snackbar snackbar =  Snackbar.make(findViewById(android.R.id.content), "Routine set as current routine", Snackbar.LENGTH_LONG);
        snackbar.show();
//        Toast.makeText(this, "Routine set as current routine", Toast.LENGTH_SHORT).show();
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
    public void clickedOnWeek(String id, String routineId) {
        Intent intent = new Intent(RoutinesActivity.this,ExercisesPerDaysActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("routineId",routineId);
        startActivity(intent);
    }

    @Override
    public void removeRoutine(String id) {
        FitnessDBHelper.getInstance(this).removeRoutine(id);
        Toast.makeText(this, "Routine Removed", Toast.LENGTH_SHORT).show();
        RoutinesFragment routineFragment = RoutinesFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container, routineFragment)
                .commit();
    }

    public void addRoutine()
    {
        mWorkoutHashMap = new HashMap<>();
        mWeekList = new ArrayList<>();
        setTheme(my.app.ivonneortega.myfitnessapp.R.style.AddRoutineActivityTheme);
        AddRoutineFragmentFromRoutineActivity userInfoFragment = AddRoutineFragmentFromRoutineActivity.newInstance(mWeekList);
        getSupportFragmentManager().beginTransaction()
                .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container,userInfoFragment)
                .commit();
        fab.setVisibility(View.GONE);

    }


    @Override
    public void AddWeekToRoutine() {
        AddWeekWorkoutFragmentFromRoutineActivity userInfoFragment = AddWeekWorkoutFragmentFromRoutineActivity.newInstance("Monday", null);
        getSupportFragmentManager().beginTransaction()
                .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container,userInfoFragment)
                .commit();
    }

    @Override
    public void saveRoutine(Routines routines) {

        FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        routines = db.insertRoutine(routines);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DatabaseTableNames.ROUTINES);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        routines.setUserId(currentUser.getUid());
        routines.setId(String.valueOf(routines.getId()));
        myRef.push().setValue(routines);
        
        startActivity(new Intent(getApplication(), RoutinesActivity.class));
        finish();
    }

    @Override
    public void changeDay(String day, Workout workout, String currentDay) {
        insertDay(workout,currentDay);
        Day currentDayObject = mWorkoutHashMap.get(day);
        AddWeekWorkoutFragmentFromRoutineActivity userInfoFragment = AddWeekWorkoutFragmentFromRoutineActivity.newInstance(day,currentDayObject);
        getSupportFragmentManager().beginTransaction()
                .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container,userInfoFragment)
                .commit();
    }

    public void insertDay(Workout workout,String currentDay)
    {
        Day day_aux = new Day(workout);
        mWorkoutHashMap.put(currentDay,day_aux);
    }

    @Override
    public void clickedOnFab(String lastDay, Workout lastWorkout) {
        boolean isValid=true;

        insertDay(lastWorkout,lastDay);
        String day="Monday";
        Workout workout=new Workout();
        int num=0;
        for(int i=0;i<mWorkoutHashMap.size();i++)
        {
            day = mListOfDays[i];
            System.out.println("CLICKED ON FAB: "+day);
            workout = mWorkoutHashMap.get(day).getWorkout();
            if(workout.getNameOfWorkout()==null || workout.getExercises().size()<=0) {
                isValid = false;
                num=i;
                i=mWorkoutHashMap.size();
            }
        }
        if(!isValid)
        {
            AddWeekWorkoutFragmentFromRoutineActivity userInfoFragment = AddWeekWorkoutFragmentFromRoutineActivity.newInstance(day, mWorkoutHashMap.get(day));
            getSupportFragmentManager().beginTransaction()
                    .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container,userInfoFragment)
                    .commit();
        }
        else
        {
            mWeekList.add(new Week(mWorkoutHashMap));
            AddRoutineFragmentFromRoutineActivity userInfoFragment = AddRoutineFragmentFromRoutineActivity.newInstance(mWeekList);
            getSupportFragmentManager().beginTransaction()
                    .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container,userInfoFragment)
                    .commit();
        }
    }
}
