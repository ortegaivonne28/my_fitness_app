package my.app.ivonneortega.myfitnessapp.Routines;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import my.app.ivonneortega.myfitnessapp.AddUserInformation.ExerciseRecyclerViewAdapter;
import my.app.ivonneortega.myfitnessapp.Data.Day;
import my.app.ivonneortega.myfitnessapp.Data.Exercise;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.DatabaseTableNames;

import java.util.ArrayList;
import java.util.List;


public class AddWeekWorkoutFragmentFromRoutineActivity extends Fragment implements View.OnClickListener {

    private addWeekWorkoutInterface mListener;
    private TextView mMonday, mTuesday, mWednesday, mThrusday, mFriday, mSaturday, mSunday, mWorkoutName;
    private String mDay;
    public static Workout mWorkout;
    private FloatingActionButton mFab;
    private Switch mRestDay;
    public List<Exercise> mList;
    private RecyclerView mRecyclerView;
    ExerciseRecyclerViewAdapter mAdapter;

    public static final String WORKOUT_NAME = "workout_name";
    public static final String REPS = "reps";
    public static final String SETS = "sets";
    public static final String EXERCISE_NAME = "exercise_name";

    public AddWeekWorkoutFragmentFromRoutineActivity() {
        // Required empty public constructor
    }


    public static AddWeekWorkoutFragmentFromRoutineActivity newInstance(String day, Day dayObject) {
        AddWeekWorkoutFragmentFromRoutineActivity fragment = new AddWeekWorkoutFragmentFromRoutineActivity();
        Bundle args = new Bundle();
        args.putString("mDay",day);
        if(dayObject!=null &&dayObject.getWorkout()!=null)
        {
            mWorkout = dayObject.getWorkout();
        }
        else
        {
            mWorkout = new Workout();
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDay = getArguments().getString("mDay");
        }
        if(mWorkout!=null)
            mList = mWorkout.getExercises();
        else
            mList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(my.app.ivonneortega.myfitnessapp.R.layout.fragment_add_week_workout2, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMonday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_monday);
        mTuesday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_tuesday);
        mWednesday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_wednesday);
        mThrusday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_thrusday);
        mFriday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_friday);
        mSaturday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_saturday);
        mSunday = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.letter_sunday);
        mFab = (FloatingActionButton) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.fab);
        mWorkoutName = (TextView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.workout_name_title);
        if(mWorkout!=null && mWorkout.getNameOfWorkout()!=null)
            mWorkoutName.setText(mWorkout.getNameOfWorkout());

        mRecyclerView = (RecyclerView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new ExerciseRecyclerViewAdapter(mList, ExerciseRecyclerViewAdapter.TYPE_USER_CREATING);
        mRecyclerView.setAdapter(mAdapter);



        mRestDay = (Switch) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.rest_day);

        mMonday.setOnClickListener(this);
        mTuesday.setOnClickListener(this);
        mWednesday.setOnClickListener(this);
        mThrusday.setOnClickListener(this);
        mFriday.setOnClickListener(this);
        mSaturday.setOnClickListener(this);
        mSunday.setOnClickListener(this);
        mRestDay.setOnClickListener(this);
        mFab.setOnClickListener(this);

        switch (mDay)
        {
            case "Monday":
                mMonday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
            case "Tuesday":
                mTuesday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
            case "Wednesday":
                mWednesday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
            case "Thrusday":
                mThrusday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
            case "Friday":
                mFriday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
            case "Saturday":
                mSaturday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
            case "Sunday":
                mSunday.setTextColor(ContextCompat.getColor(mMonday.getContext(), my.app.ivonneortega.myfitnessapp.R.color.colorTextSelectedWeek));
                break;
        }

        mWorkoutName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mWorkout.setNameOfWorkout(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        if(mWorkoutname!=null)
//            mTitle.setText(mWorkoutname);
//        if(mExerciseName!=null)
//            mExercise.setText(mExerciseName);
//        if(mSetsText!=null)
//            mSets.setText(mSetsText);
//        if(mRepsText!=null)
//            mReps.setText(mRepsText);

        mRestDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mRestDay.isChecked())
                {
                    //TODO SET VIEWS TO GONE
                }
                else
                {
                    // TODO SET VIEWS TO VISIBLE
                }
            }
        });


    }

    public void onButtonPressed(String day, Workout workout) {
        if (mListener != null) {
            mListener.changeDay(day, workout,mDay);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof addWeekWorkoutInterface) {
            mListener = (addWeekWorkoutInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        mWorkout.setExercises(mAdapter.getList());

        switch (v.getId())
        {
            case my.app.ivonneortega.myfitnessapp.R.id.letter_monday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[0],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.letter_tuesday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[1],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.letter_wednesday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[2],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.letter_thrusday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[3],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.letter_friday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[4],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.letter_saturday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[5],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.letter_sunday:
                mListener.changeDay(DatabaseTableNames.LIST_OF_DAYS[6],mWorkout, mDay);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.rest_day:
                if(mRestDay.isChecked())
                    mRecyclerView.setVisibility(View.GONE);
                else
                    mRecyclerView.setVisibility(View.VISIBLE);
                break;
            case my.app.ivonneortega.myfitnessapp.R.id.fab:
                mListener.clickedOnFab(mDay,mWorkout);
                break;
        }
    }


    public interface addWeekWorkoutInterface {
        // TODO: Update argument type and name
        void changeDay(String day, Workout workout, String currentDay);
        void clickedOnFab(String day, Workout workout);
    }

    public interface getList{
        List<Exercise> getList();
    }
}
