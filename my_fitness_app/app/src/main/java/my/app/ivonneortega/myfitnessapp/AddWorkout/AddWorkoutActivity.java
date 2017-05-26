package my.app.ivonneortega.myfitnessapp.AddWorkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import my.app.ivonneortega.myfitnessapp.Challenges.ChallengesActivity;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.DatabaseTableNames;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.Friends.FriendsActivity;
import my.app.ivonneortega.myfitnessapp.R;
import my.app.ivonneortega.myfitnessapp.Routines.RoutinesActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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
    private GoogleApiClient mGoogleApiClient;


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
        final List<Workout> workoutList = db.getUserWorkouts();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.USER);
        myRef.orderByChild("id").equalTo(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                boolean exit=true;
                int counter = 1;
                String key = dataSnapshot.getValue().toString();
                while (exit)
                {
                    if(key.charAt(counter)=='=')
                        exit = false;
                    else
                        counter++;
                }

                key = key.substring(1,counter);
                System.out.println(key);


                myRef.child(key).child("workouts").setValue(workoutList);
//                searchUsers.child(newKey).child("mRatings").child("friendlySUM").setValue(friendSUM);

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
                .commit();
        mListener = routineFragment;

        fab.setImageResource(R.mipmap.ic_done_white_24dp);
        whichFab = FAB_EDIT_WORKOUT;
    }

    @Override
    public void updateWorkouts() {
        saveToFirebase();
    }

//    @Override
//    public void clickedOnWorkout(Uri uri) {
//
//    }

    public interface GetListFromFragment{
        Workout getWorkoutFromFragment();
    }
}
