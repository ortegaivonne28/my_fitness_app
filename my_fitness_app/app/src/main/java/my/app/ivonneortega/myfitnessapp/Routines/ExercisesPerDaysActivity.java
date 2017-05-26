package my.app.ivonneortega.myfitnessapp.Routines;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import my.app.ivonneortega.myfitnessapp.AddUserInformation.ExerciseRecyclerViewAdapter;
import my.app.ivonneortega.myfitnessapp.AddWorkout.AddWorkoutActivity;
import my.app.ivonneortega.myfitnessapp.Challenges.ChallengesActivity;
import my.app.ivonneortega.myfitnessapp.Data.Day;
import my.app.ivonneortega.myfitnessapp.Data.Exercise;
import my.app.ivonneortega.myfitnessapp.Data.User;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.DatabaseTableNames;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.Friends.FriendsActivity;
import my.app.ivonneortega.myfitnessapp.R;

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

import java.util.HashMap;
import java.util.List;

public class ExercisesPerDaysActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateHashMapInterface {

    ExercisesPerDaysActivity.ExercisesPageAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    HashMap<String, Day> mDayHashMap;
    String mId, mRoutineId;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(my.app.ivonneortega.myfitnessapp.R.layout.activity_exercises_per_days);
        Toolbar toolbar = (Toolbar) findViewById(my.app.ivonneortega.myfitnessapp.R.id.toolbar);
        setSupportActionBar(toolbar);

        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(my.app.ivonneortega.myfitnessapp.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDayHashMap.size()>0)
                {
                    for (String day: DatabaseTableNames.LIST_OF_DAYS) {
                        System.out.println(day);
                        Day dayy;
                            dayy = mDayHashMap.get(day);


                        Workout workout = null;
                        if(dayy !=null) {
                            workout = dayy.getWorkout();
                            db.updateWorkout(workout);
                        }
                    }
                }

            }
        });

        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mRoutineId = intent.getStringExtra("routineId");

        mDayHashMap = db.getDayById(mId);

        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, my.app.ivonneortega.myfitnessapp.R.string.navigation_drawer_open, my.app.ivonneortega.myfitnessapp.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(my.app.ivonneortega.myfitnessapp.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDemoCollectionPagerAdapter = new ExercisesPerDaysActivity.ExercisesPageAdapter(getSupportFragmentManager(), mDayHashMap);


        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(my.app.ivonneortega.myfitnessapp.R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


    }

    public void updateRoutinesInFirebase()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.ROUTINES);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        myRef.orderByChild("id").equalTo(mRoutineId).orderByChild("userId").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    System.out.println("START ACTIVITY");
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

                    User myUser = dataSnapshot.child(key).getValue(User.class);




                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
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
        getMenuInflater().inflate(my.app.ivonneortega.myfitnessapp.R.menu.exercises_per_days, menu);
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
    public void updateHashMap(String today, Workout workout) {
        Day day = mDayHashMap.get(today);
        day.setWorkout(workout);
        mDayHashMap.put(today,day);
    }


    public static class ExercisesPageAdapter extends FragmentStatePagerAdapter {

        HashMap<String, Day> mDayHashMap;
        public static final String DAY_ID = "day_id";
        public static final String TODAY = "today";
        public String mToday;

        public ExercisesPageAdapter(FragmentManager fm, HashMap<String, Day> hashMap) {
            super(fm);
            mDayHashMap = hashMap;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ExercisesPerDaysActivity.DemoObjectFragment();
            Bundle args = new Bundle();
            mToday = DatabaseTableNames.LIST_OF_DAYS[i];
            args.putString(DAY_ID,mDayHashMap.get(mToday).getId());
            args.putString(TODAY,mToday);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mDayHashMap.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return DatabaseTableNames.LIST_OF_DAYS[position];
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DemoObjectFragment extends Fragment
            implements ExerciseRecyclerViewAdapter.updateListOfExercisesInterface {

        List<Exercise> mExercises;
        RecyclerView mRecyclerview;
        String mId;
        ExerciseRecyclerViewAdapter mAdapter;
        UpdateHashMapInterface mListener;
        Workout mWorkout;
        String mToday;

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(my.app.ivonneortega.myfitnessapp.R.layout.fragment_routines, container, false);
            Bundle args = getArguments();
            mId = args.getString(ExercisesPageAdapter.DAY_ID);
            mToday = args.getString(ExercisesPageAdapter.TODAY);

            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mRecyclerview = (RecyclerView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.recycler_view);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(mRecyclerview.getContext(),LinearLayoutManager.VERTICAL,false));


            mWorkout = new Workout();
            mWorkout = FitnessDBHelper.getInstance(mRecyclerview.getContext()).getWorkoutByDayId(mId);
            mExercises = FitnessDBHelper.getInstance(mRecyclerview.getContext()).getExercisesById(mWorkout.getId());
            mAdapter = new ExerciseRecyclerViewAdapter(mExercises,ExerciseRecyclerViewAdapter.TYPE_USER_EDITING, this);
            mRecyclerview.setAdapter(mAdapter);


        }
        @Override
        public void updateListOfExercises(List<Exercise> exercises) {
//            FitnessDBHelper.getInstance(mRecyclerview.getContext()).updateListOfExercises(exercises);
            mWorkout.setExercises(exercises);
            mListener.updateHashMap(mToday,mWorkout);
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mListener = (UpdateHashMapInterface)context;
        }

    }



}
