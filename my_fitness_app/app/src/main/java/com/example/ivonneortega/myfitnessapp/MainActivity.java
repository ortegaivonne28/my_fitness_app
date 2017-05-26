package com.example.ivonneortega.myfitnessapp;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.AddWorkout.AddWorkoutActivity;
import com.example.ivonneortega.myfitnessapp.Challenges.ChallengesActivity;
import com.example.ivonneortega.myfitnessapp.Data.Challenges;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.Friends.FriendsActivity;
import com.example.ivonneortega.myfitnessapp.Routines.RoutinesActivity;
import com.example.ivonneortega.myfitnessapp.Workout.StartWorkoutActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener{

    private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    private ViewPager mViewPager;
    private GoogleApiClient mGoogleApiClient;
    private String[] mListOfDays;
    private boolean workoutForToday;

    public static final String DAY_DATE = "day";
    public static final String TODAY = "mDay";
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String title = intent.getStringExtra("title");
                String name = intent.getStringExtra("name");
                String uniqueId = intent.getStringExtra("id");
                dialogToAcceptOrDenyChallenge(title,name,uniqueId);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter("challenge"));


    }

    public void dialogToAcceptOrDenyChallenge(String title, String name, final String challengeId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(name+" has challenged you to "+title)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FitnessDBHelper.getInstance(mViewPager.getContext()).updateStatusChallenge(challengeId,DatabaseTableNames.ACCEPTED);
                        updateStatusChallengeInDatabase(challengeId,DatabaseTableNames.ACCEPTED);
                    }
                })
                .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FitnessDBHelper.getInstance(mViewPager.getContext()).updateStatusChallenge(challengeId,DatabaseTableNames.ACCEPTED);
                        updateStatusChallengeInDatabase(challengeId,DatabaseTableNames.REJECTED);
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    public void updateStatusChallengeInDatabase(final String uniqueId, final String status)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.CHALLENGES);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    myRef.child(uniqueId).child("status").setValue(status);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mListOfDays = getListOfDays();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(),mListOfDays);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setCurrentItem(6);
    }

    public String[] getListOfDays() throws ParseException {

        String[] array = new String[15];
        DateFormat dateFormat = new SimpleDateFormat("MM/dd");
        Date date = new Date();
        String today = dateFormat.format(date);

        Date hoy = new Date();
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        for(int i=0;i<15;i++)
        {
            c.add(Calendar.DATE, 1);
            String newDay = (String)(formattedDate.format(c.getTime()));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date startDate = df.parse(newDay);

            newDay = startDate.toString();
            array[i] = newDay;
        }
    return array;

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
        getMenuInflater().inflate(R.menu.main, menu);
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
            startActivity(new Intent(MainActivity.this,RoutinesActivity.class));
        } else if (id == R.id.nav_workouts) {
            startActivity(new Intent(MainActivity.this, AddWorkoutActivity.class));
        } else if (id == R.id.nav_cardio) {

        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(MainActivity.this, FriendsActivity.class));
        } else if (id == R.id.nav_challenges) {

            startActivity(new Intent(MainActivity.this, ChallengesActivity.class));
        } else if (id == R.id.nav_settings) {
            this.deleteDatabase(FitnessDBHelper.DATABASE_NAME);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            finish();
                        }
                    });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

//        List<Article> mList;

        String[] mDays;

        public DemoCollectionPagerAdapter(FragmentManager fm, String[] days) {
            super(fm);
//            mList = list;
            mDays = days;
        }


        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putString(DAY_DATE,mDays[i]);
            args.putString(TODAY,mDays[6]);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 6:
                    return "Today";
                case 5:
                    return "Yesterday";
                case 7:
                    return "Tomorrow";
                default:
                    return mDays[position].toString().substring(0,10);
            }

        }

        /**
         *
         * @return the number of items in the list
         */
        @Override
        public int getCount() {
            return mDays.length-1;
        }

    }

    /**
     * Fragment that hold the views for each article
     */
    public static class DemoObjectFragment extends Fragment
    implements View.OnClickListener {

        ImageView mGlass1, mGlass2, mGlass3, mGlass4, mGlass5, mGlass6, mGlass7, mGlass8, mDividerWater, mDividerWorkout;
        TextView mWaterCompleted, mWorkout, mCardio, mChallenges, mExtraWorkoutInfo, mSetNewWorkout;
        String mDay, mToday;
        FitnessDBHelper db;
        boolean workoutForToday, workoutCompleted, mChallengeForToday;
        View mView, mChallengeLayout;

        public static final String ARG_OBJECT = "object";


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
            final Bundle args = getArguments();
            mDay = args.getString(DAY_DATE);
            mToday = args.getString(TODAY);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);


            //Setting all views
            mGlass1 = (ImageView) view.findViewById(R.id.glass1);
            mGlass2 = (ImageView) view.findViewById(R.id.glass2);
            mGlass3 = (ImageView) view.findViewById(R.id.glass3);
            mGlass4 = (ImageView) view.findViewById(R.id.glass4);
            mGlass5 = (ImageView) view.findViewById(R.id.glass5);
            mGlass6 = (ImageView) view.findViewById(R.id.glass6);
            mGlass7 = (ImageView) view.findViewById(R.id.glass7);
            mGlass8 = (ImageView) view.findViewById(R.id.glass8);
            mDividerWater = (ImageView) view.findViewById(R.id.dividerWater2);
            mWaterCompleted = (TextView) view.findViewById(R.id.water_completed_text);
            mWorkout = (TextView) view.findViewById(R.id.workout_title);
            mCardio = (TextView) view.findViewById(R.id.cardio_title);
            mChallenges = (TextView) view.findViewById(R.id.challenge_title);
            mExtraWorkoutInfo = (TextView) view.findViewById(R.id.workout_set_new_workout);
            mView = view.findViewById(R.id.workout_layout);
            mView.setOnClickListener(this);
            mSetNewWorkout = (TextView) view.findViewById(R.id.workout_set_new_workout);
            mDividerWorkout = (ImageView) view.findViewById(R.id.dividerExtraWorkout);
            mChallengeLayout = view.findViewById(R.id.challenge_layout);
            mChallengeLayout.setOnClickListener(this);


            db = FitnessDBHelper.getInstance(mWorkout.getContext());
            getWaterIntakeForToday();


            mGlass1.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.glass1:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass1.setClickable(false);
                        mGlass1.setImageResource(R.drawable.glass_full);
                        mGlass2.setImageResource(R.drawable.glass_add);
                        mGlass2.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,1);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.glass2:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass2.setClickable(false);
                        mGlass2.setImageResource(R.drawable.glass_full);
                        mGlass3.setImageResource(R.drawable.glass_add);
                        mGlass3.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,2);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.glass3:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass3.setClickable(false);
                        mGlass3.setImageResource(R.drawable.glass_full);
                        mGlass4.setImageResource(R.drawable.glass_add);
                        mGlass4.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,3);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case R.id.glass4:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass4.setClickable(false);
                        mGlass4.setImageResource(R.drawable.glass_full);
                        mGlass5.setImageResource(R.drawable.glass_add);
                        mGlass5.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,4);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.glass5:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass5.setClickable(false);
                        mGlass5.setImageResource(R.drawable.glass_full);
                        mGlass6.setImageResource(R.drawable.glass_add);
                        mGlass6.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,5);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.glass6:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass6.setClickable(false);
                        mGlass6.setImageResource(R.drawable.glass_full);
                        mGlass7.setImageResource(R.drawable.glass_add);
                        mGlass7.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,6);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.glass7:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass7.setClickable(false);
                        mGlass7.setImageResource(R.drawable.glass_full);
                        mGlass8.setImageResource(R.drawable.glass_add);
                        mGlass8.setOnClickListener(this);
                        db.updateWaterIntakeForToday(mDay,7);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.glass8:
                    if(mToday.equalsIgnoreCase(mDay))
                    {
                        mGlass8.setClickable(false);
                        mGlass8.setImageResource(R.drawable.glass_full);
                        mWaterCompleted.setVisibility(View.VISIBLE);
                        mDividerWater.setVisibility(View.VISIBLE);
                        db.updateWaterIntakeForToday(mDay,8);
                    }
                    else
                    {
                        Toast.makeText(mGlass1.getContext(), "Can't update water intake", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.workout_layout:
                    if (mToday.equalsIgnoreCase(mDay)) {
                        if (workoutForToday == false)
                            showDialogToSetRoutineOrAddWorkout();
                        else
                            startWorkoutActivity();
                    } else {
                        Toast.makeText(mDividerWater.getContext(), "You can only do today's workout", Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.challenge_layout:
                    showDialogToSetChallenge();


                    break;
            }
        }

        private void showDialogToSetChallenge() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You don't have a challenge for this day!");
            builder.setMessage("Would you like to set a challenge?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getActivity(),ChallengesActivity.class);
                            intent.putExtra("day",mDay);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();


        }

        public void getWaterIntakeForToday()
        {
            int water = db.getWaterIntakeForToday(mDay);
            switch (water)
            {
                case 1:
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass2.setImageResource(R.drawable.glass_add);
                    mGlass2.setOnClickListener(this);
                    break;
                case 2:
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass3.setImageResource(R.drawable.glass_add);
                    mGlass3.setOnClickListener(this);
                    break;
                case 3:
                    mGlass3.setClickable(false);
                    mGlass3.setImageResource(R.drawable.glass_full);
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass4.setImageResource(R.drawable.glass_add);
                    mGlass4.setOnClickListener(this);
                    break;
                case 4:
                    mGlass4.setClickable(false);
                    mGlass4.setImageResource(R.drawable.glass_full);
                    mGlass3.setClickable(false);
                    mGlass3.setImageResource(R.drawable.glass_full);
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass5.setImageResource(R.drawable.glass_add);
                    mGlass5.setOnClickListener(this);
                    break;
                case 5:
                    mGlass5.setClickable(false);
                    mGlass5.setImageResource(R.drawable.glass_full);
                    mGlass4.setClickable(false);
                    mGlass4.setImageResource(R.drawable.glass_full);
                    mGlass3.setClickable(false);
                    mGlass3.setImageResource(R.drawable.glass_full);
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass6.setImageResource(R.drawable.glass_add);
                    mGlass6.setOnClickListener(this);
                    break;
                case 6:
                    mGlass6.setClickable(false);
                    mGlass6.setImageResource(R.drawable.glass_full);
                    mGlass5.setClickable(false);
                    mGlass5.setImageResource(R.drawable.glass_full);
                    mGlass4.setClickable(false);
                    mGlass4.setImageResource(R.drawable.glass_full);
                    mGlass3.setClickable(false);
                    mGlass3.setImageResource(R.drawable.glass_full);
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass7.setImageResource(R.drawable.glass_add);
                    mGlass7.setOnClickListener(this);
                    break;
                case 7:
                    mGlass7.setClickable(false);
                    mGlass7.setImageResource(R.drawable.glass_full);
                    mGlass6.setClickable(false);
                    mGlass6.setImageResource(R.drawable.glass_full);
                    mGlass5.setClickable(false);
                    mGlass5.setImageResource(R.drawable.glass_full);
                    mGlass4.setClickable(false);
                    mGlass4.setImageResource(R.drawable.glass_full);
                    mGlass3.setClickable(false);
                    mGlass3.setImageResource(R.drawable.glass_full);
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mGlass8.setImageResource(R.drawable.glass_add);
                    mGlass8.setOnClickListener(this);
                    break;
                case 8:
                    mGlass8.setClickable(false);
                    mGlass8.setImageResource(R.drawable.glass_full);
                    mGlass7.setClickable(false);
                    mGlass7.setImageResource(R.drawable.glass_full);
                    mGlass6.setClickable(false);
                    mGlass6.setImageResource(R.drawable.glass_full);
                    mGlass5.setClickable(false);
                    mGlass5.setImageResource(R.drawable.glass_full);
                    mGlass4.setClickable(false);
                    mGlass4.setImageResource(R.drawable.glass_full);
                    mGlass3.setClickable(false);
                    mGlass3.setImageResource(R.drawable.glass_full);
                    mGlass2.setClickable(false);
                    mGlass2.setImageResource(R.drawable.glass_full);
                    mGlass1.setClickable(false);
                    mGlass1.setImageResource(R.drawable.glass_full);
                    mWaterCompleted.setVisibility(View.VISIBLE);
                    mDividerWater.setVisibility(View.VISIBLE);
                    break;

            }
        }

        @Override
        public void onResume() {
            super.onResume();



            Workout todayWorkout = db.getWorkoutForToday(mDay);
//            db.removeAllUserDays();
            workoutCompleted = false;
            if (todayWorkout != null) {
                mWorkout.setText(todayWorkout.getNameOfWorkout());
                if (db.isWorkoutCompletedForToday(mDay)) {
                    mSetNewWorkout.setVisibility(View.VISIBLE);
                    mExtraWorkoutInfo.setText("Workout completed!");
                    workoutForToday = false;
                    workoutCompleted = true;
                } else {
                    workoutForToday = true;
                }

            } else {
                mWorkout.setText("You don't have a workout for today!");
                mDividerWorkout.setVisibility(View.VISIBLE);
                mSetNewWorkout.setVisibility(View.VISIBLE);
                workoutForToday = false;
            }


            Challenges challenge = db.getChallengeForToday(mDay);
            if (challenge != null) {
                mChallenges.setText(challenge.getTitle());
                mChallengeForToday = true;
//                if (db.isWorkoutCompletedForToday(mDay)) {
//                    mSetNewWorkout.setVisibility(View.VISIBLE);
//                    mExtraWorkoutInfo.setText("Workout completed!");
//                    workoutForToday = false;
//                    workoutCompleted = true;
//                } else {
//                    workoutForToday = true;
//                }

            } else {
                  mChallenges.setText("You don't have any challenge for today!");
                  mChallengeForToday = false;
//                mDividerWorkout.setVisibility(View.VISIBLE);
//                mSetNewWorkout.setVisibility(View.VISIBLE);
//                workoutForToday = false;
            }

        }

        public void showDialogToSetRoutineOrAddWorkout() {
            AlertDialog.Builder builder = new AlertDialog.Builder((mGlass2.getContext()));
            builder.setTitle("Set a workout")
                    .setItems(R.array.alertDialogSetWorkoutOrRoutine, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    if (workoutCompleted) {
                                        showSecondAlertDialog();
                                    } else
                                        startActivity(new Intent(getActivity(), RoutinesActivity.class));
                                    break;

                                case 1:

                                    break;
                            }
                        }
                    });
            builder.create();
            builder.show();
        }

        public void startWorkoutActivity() {
            startActivity(new Intent(getActivity(), StartWorkoutActivity.class));
        }

        public void showSecondAlertDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to set a new workout for today? " +
                    "This will override today's workout")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getActivity(), RoutinesActivity.class));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }
    }


}
