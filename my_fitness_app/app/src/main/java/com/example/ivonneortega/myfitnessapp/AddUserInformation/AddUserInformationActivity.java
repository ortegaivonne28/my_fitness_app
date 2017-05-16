package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ivonneortega.myfitnessapp.Data.User;
import com.example.ivonneortega.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUserInformationActivity extends AppCompatActivity
        implements AddUserInformationFragment.UserInformationInterface {

    private FirebaseAuth mAuth;

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

//        myRef.push().setValue(currentUser.getEmail());
//        DatabaseReference myUser = database.getReference(currentUser.getUid());

        User current = new User(currentUser.getUid(),currentUser.getEmail(),name,lastName,null,weight,weightGoal,8);
        myRef.push().setValue(current);
//        myUser.push().setValue(lastName);
//        myUser.push().setValue(age);
//        myUser.push().setValue(currentUser.getEmail());
//        myUser.push().setValue(currentUser.getEmail());
//        myUser.push().setValue(currentUser.getEmail());


    }

}
