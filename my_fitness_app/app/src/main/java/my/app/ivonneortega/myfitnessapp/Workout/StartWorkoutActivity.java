package my.app.ivonneortega.myfitnessapp.Workout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import my.app.ivonneortega.myfitnessapp.Data.Exercise;
import my.app.ivonneortega.myfitnessapp.Data.SingleExercise;
import my.app.ivonneortega.myfitnessapp.Data.SuperSet;
import my.app.ivonneortega.myfitnessapp.Data.TripleSet;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StartWorkoutActivity extends AppCompatActivity {

    private String mToday;
    private RecyclerView mRecyclerView;
    private WorkoutRecyclerViewAdapter mAdapter;
    private List<Exercise> mExerciseList;
    private List<Exercise> mExerciseCompleted;

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

        mExerciseCompleted = new ArrayList<>();

        mExerciseList = FitnessDBHelper.getInstance(this).getWorkoutForToday(mToday).getExercises();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new WorkoutRecyclerViewAdapter(mExerciseList);
        mRecyclerView.setAdapter(mAdapter);


        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        final Workout workout = db.getWorkoutById(mExerciseList.get(0).getWorkoutID());



        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExerciseCompleted = mAdapter.getExerciseList();

                if(exerciseCompleted())
                {
                    Toast.makeText(StartWorkoutActivity.this, "Workout completed", Toast.LENGTH_SHORT).show();
                    workout.setExercises(mExerciseCompleted);
                    db.updateUserDayWorkoutCompleted(mToday,workout);
                }
                else
                {
                    Toast.makeText(StartWorkoutActivity.this, "You haven't completed your workout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean exerciseCompleted()
    {
        for (Exercise exercise: mExerciseCompleted) {
            if(exercise instanceof SingleExercise)
            {
                if(((SingleExercise) exercise).getSets() != exercise.getSetsList().size()) {
                    System.out.println("SETS: "+((SingleExercise) exercise).getSets());
                    System.out.println("SIZE: "+exercise.getSetsList().size());
                    return false;
                }
            }
            if(exercise instanceof SuperSet)
            {
                if(((SuperSet) exercise).getSets() != exercise.getSetsList().size()) {
                    System.out.println("SETS: "+((SuperSet) exercise).getSets());
                    System.out.println("SIZE: "+ exercise.getSetsList().size());
                    return false;
                }
            }
            if(exercise instanceof TripleSet)
            {
                if(((TripleSet) exercise).getSets() != exercise.getSetsList().size()) {
                    System.out.println("SETS: "+((TripleSet) exercise).getSets());
                    System.out.println("SIZE: "+exercise.getSetsList().size());
                    return false;
                }
            }
        }
        return true;
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

    public interface getExerciseListInterface
    {
        List<Exercise> getExerciseList();
    }

}
