package com.example.ivonneortega.myfitnessapp.AddWorkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.AddUserInformation.AddUserInformationActivity;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.User;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.DatabaseTableNames;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.LoginActivity;
import com.example.ivonneortega.myfitnessapp.MainActivity;
import com.example.ivonneortega.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.ivonneortega.myfitnessapp.R.id.body_text;
import static com.example.ivonneortega.myfitnessapp.R.id.fab;

public class AddWorkoutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SeeAllWorkoutsFragment.SeeAllWorkoutsInterface{

    public static final int FAB_SAVE_WORKOUT = 1;
    public static final int FAB_CREATE_WORKOUT =2;
    public static final int FAB_EDIT_WORKOUT = 3;
    private  int whichFab;
    private Workout mWorkout;
    private FitnessDBHelper db;
    private GetListFromFragment mListener;
    FloatingActionButton fab;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Workouts");

        whichFab = FAB_CREATE_WORKOUT;
        db = FitnessDBHelper.getInstance(this);

        mAuth = FirebaseAuth.getInstance();



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateWorkoutFragment routineFragment;
                switch (whichFab)
                {
                    case FAB_CREATE_WORKOUT:
                        routineFragment = CreateWorkoutFragment.newInstance(CreateWorkoutFragment.TYPE_USER_CREATING, null
                        );
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, routineFragment)
                                .addToBackStack(null)
                                .commit();
                        mListener = routineFragment;

                        fab.setImageResource(R.mipmap.ic_done_white_24dp);
                        whichFab = FAB_SAVE_WORKOUT;
                        break;

                    case FAB_SAVE_WORKOUT:
                        if(whichFab == FAB_SAVE_WORKOUT)
                        {
                           saveWorkout();
                        }
                        else
                            Toast.makeText(AddWorkoutActivity.this, "Please create a workout first", Toast.LENGTH_SHORT).show();
                        break;

                    case FAB_EDIT_WORKOUT:
                        mWorkout = mListener.getWorkoutFromFragment();
                        if(mWorkout!=null)
                        {
                            db.updateWorkout(mWorkout);
                            Toast.makeText(AddWorkoutActivity.this, "Workout updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(view.getContext().getApplicationContext(),AddWorkoutActivity.class));
                        }
                        else
                        {
                            Toast.makeText(AddWorkoutActivity.this, "Please complete the exercise", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SeeAllWorkoutsFragment routineFragment = SeeAllWorkoutsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, routineFragment)
                .commit();

    }

    public void saveWorkout()
    {


//        myRef.push().setValue(routines);

        mWorkout = mListener.getWorkoutFromFragment();
        if(mWorkout==null)
            Toast.makeText(AddWorkoutActivity.this, "Please complete the exercise", Toast.LENGTH_SHORT).show();
        else{
            db.insertWorkout(mWorkout,-1);
            saveToFirebase();

            Toast.makeText(AddWorkoutActivity.this, "Workout added successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddWorkoutActivity.this,AddWorkoutActivity.class));
        }
    }

    public void saveToFirebase()
    {
        List<Workout> workoutList = db.getUserWorkouts();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.ROUTINES);
        System.out.println("SAVING INTO FIREBASE");
        myRef.orderByChild("userId").equalTo("HjyyS2p3WlgsBubDWavCp6K5DsQ2").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("Exits");
                } else {
                    System.out.println("Doesnt exits");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
        getMenuInflater().inflate(R.menu.add_workout, menu);
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
    public void clickedOnWorkout(String id) {
        CreateWorkoutFragment routineFragment;
        routineFragment = CreateWorkoutFragment.newInstance(CreateWorkoutFragment.TYPE_USER_EDITING_WORKOUT,id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, routineFragment)
                .addToBackStack(null)
                .commit();
        mListener = routineFragment;

        fab.setImageResource(R.mipmap.ic_done_white_24dp);
        whichFab = FAB_EDIT_WORKOUT;
    }

//    @Override
//    public void clickedOnWorkout(Uri uri) {
//
//    }

    public interface GetListFromFragment{
        Workout getWorkoutFromFragment();
    }
}
