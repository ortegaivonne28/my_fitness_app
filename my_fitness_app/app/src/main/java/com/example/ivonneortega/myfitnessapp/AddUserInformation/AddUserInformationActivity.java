package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ivonneortega.myfitnessapp.Data.Day;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.Data.User;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.DatabaseTableNames;
import com.example.ivonneortega.myfitnessapp.MainActivity;
import com.example.ivonneortega.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddUserInformationActivity extends AppCompatActivity
        implements AddUserInformationFragment.UserInformationInterface,
        AddRoutineFragment.RoutineInterface,
        AddWeekWorkoutFragment.addWeekWorkoutInterface{

    public static final String TAG = "ACTIVITY";

    private FirebaseAuth mAuth;
    private HashMap<String, Day> mWorkoutHashMap;
    private List<String> mListOfDays;
    private List<Week> mWeekList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_information);

        AddUserInformationFragment userInfoFragment = AddUserInformationFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();

        mListOfDays = new ArrayList<>();
        mListOfDays.add("Monday");
        mListOfDays.add("Tuesday");
        mListOfDays.add("Wednesday");
        mListOfDays.add("Thrusday");
        mListOfDays.add("Friday");
        mListOfDays.add("Saturday");
        mListOfDays.add("Sunday");
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void addUser(String name, String lastName, int age, float weight, float weightGoal) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.USER);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        User current = new User(currentUser.getUid(),currentUser.getEmail(),name,lastName,null,weight,weightGoal,8);
        myRef.push().setValue(current);

        mWorkoutHashMap = new HashMap<>();
        mWeekList = new ArrayList<>();
        setTheme(R.style.AddRoutineActivityTheme);
        AddRoutineFragment userInfoFragment = AddRoutineFragment.newInstance(mWeekList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();


    }

    @Override
    public void AddWeekToRoutine() {
        AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance("Monday", null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();
    }

    @Override
    public void saveRoutine(Routines routines) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.ROUTINES);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        routines.setId(currentUser.getUid());
        myRef.push().setValue(routines);
        startActivity(new Intent(AddUserInformationActivity.this, MainActivity.class));
//        SeeRoutinesFragment userInfoFragment = SeeRoutinesFragment.newInstance();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container,userInfoFragment)
//                .commit();
    }

    @Override
    public void changeDay(String day, Workout workout, String currentDay) {
        Day day_aux = new Day(workout);
        mWorkoutHashMap.put(currentDay,day_aux);
        Day currentDayObject = mWorkoutHashMap.get(day);
        AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance(day,currentDayObject);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();
    }

    @Override
    public void clickedOnFab() {
        boolean isValid=true;
        String day="Monday";
        Workout workout=new Workout();
        int num=0;
        for(int i=0;i<mWorkoutHashMap.size();i++)
        {
            day = mListOfDays.get(i);
            workout = mWorkoutHashMap.get(day).getWorkout();
            if(workout.getNameOfWorkout()==null || workout.getExercises().size()<=0) {
                isValid = false;
                num=i;
                i=mWorkoutHashMap.size();
            }
        }
        if(!isValid)
        {
            AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance(day, mWorkoutHashMap.get(day));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,userInfoFragment)
                    .commit();
        }
        else
        {
           mWeekList.add(new Week(mWorkoutHashMap));
            AddRoutineFragment userInfoFragment = AddRoutineFragment.newInstance(mWeekList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,userInfoFragment)
                    .commit();
        }
    }
}
