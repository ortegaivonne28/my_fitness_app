package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.User;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
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
    private HashMap<String, Workout> mWorkoutHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_information);

        AddUserInformationFragment userInfoFragment = AddUserInformationFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();


        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void addUser(String name, String lastName, int age, float weight, float weightGoal) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        User current = new User(currentUser.getUid(),currentUser.getEmail(),name,lastName,null,weight,weightGoal,8);
        myRef.push().setValue(current);

        mWorkoutHashMap = new HashMap<>();
        setTheme(R.style.AddRoutineActivityTheme);
        List<Week> list = new ArrayList<>();
        AddRoutineFragment userInfoFragment = AddRoutineFragment.newInstance(list);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();


    }

    @Override
    public void AddWeekToRoutine() {
        List<Exercise> exercises = new ArrayList<>();
        AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance("Monday", null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();
    }

    @Override
    public void changeDay(String day, Workout workout, String currentDay) {
        mWorkoutHashMap.put(currentDay,workout);
        AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance(day, mWorkoutHashMap.get(day));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();
    }
}
