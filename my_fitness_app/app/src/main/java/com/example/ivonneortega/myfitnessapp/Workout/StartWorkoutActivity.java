package com.example.ivonneortega.myfitnessapp.Workout;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartWorkoutActivity extends AppCompatActivity {

    private String mToday;
    private RecyclerView mRecyclerView;
    private WorkoutRecyclerViewAdapter mAdapter;
    private List<Exercise> mExerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        try {
            setToday();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(mToday);
        mExerciseList = FitnessDBHelper.getInstance(this).getWorkoutForToday(mToday).getExercises();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        mAdapter = new WorkoutRecyclerViewAdapter(mExerciseList);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setToday() throws ParseException {
        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        mToday = (String)(formattedDate.format(c.getTime()));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = df.parse(mToday);

        mToday = startDate.toString();


    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if ( v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//        }
//        return super.dispatchTouchEvent( event );
//    }

}
