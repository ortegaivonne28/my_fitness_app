package com.example.ivonneortega.myfitnessapp.Routines;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.AddUserInformation.ExerciseRecyclerViewAdapter;
import com.example.ivonneortega.myfitnessapp.Data.Day;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.DatabaseTableNames;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.HashMap;
import java.util.List;

public class ExercisesPerDaysActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ExercisesPerDaysActivity.ExercisesPageAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    HashMap<String, Day> mDayHashMap;
    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_per_days);
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

        Intent intent = getIntent();
        mId = intent.getStringExtra("id");

        FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        mDayHashMap = db.getDayById(mId);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDemoCollectionPagerAdapter = new ExercisesPerDaysActivity.ExercisesPageAdapter(getSupportFragmentManager(), mDayHashMap);


        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


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
        getMenuInflater().inflate(R.menu.exercises_per_days, menu);
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



    public static class ExercisesPageAdapter extends FragmentStatePagerAdapter {

        HashMap<String, Day> mDayHashMap;
        public static final String DAY_ID = "day_id";

        public ExercisesPageAdapter(FragmentManager fm, HashMap<String, Day> hashMap) {
            super(fm);
            mDayHashMap = hashMap;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ExercisesPerDaysActivity.DemoObjectFragment();
            Bundle args = new Bundle();
            args.putString(DAY_ID,mDayHashMap.get(DatabaseTableNames.LIST_OF_DAYS[i]).getId()); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
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
            implements ExerciseRecyclerViewAdapter.updateListOfExercisesInterface{

        List<Exercise> mExercises;
        RecyclerView mRecyclerview;
        String mId;
        ExerciseRecyclerViewAdapter mAdapter;

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_routines, container, false);
            Bundle args = getArguments();
            mId = args.getString(ExercisesPageAdapter.DAY_ID);


            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mRecyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(mRecyclerview.getContext(),LinearLayoutManager.VERTICAL,false));


            Workout workout = new Workout();
            workout = FitnessDBHelper.getInstance(mRecyclerview.getContext()).getWorkoutById(mId);
            mExercises = FitnessDBHelper.getInstance(mRecyclerview.getContext()).getExercisesById(workout.getId());
            mAdapter = new ExerciseRecyclerViewAdapter(mExercises,ExerciseRecyclerViewAdapter.TYPE_USER_EDITING, this);
            mRecyclerview.setAdapter(mAdapter);


        }
        @Override
        public void updateListOfExercises(List<Exercise> exercises) {
            FitnessDBHelper.getInstance(mRecyclerview.getContext()).updateListOfExercises(exercises);
        }



    }



}
