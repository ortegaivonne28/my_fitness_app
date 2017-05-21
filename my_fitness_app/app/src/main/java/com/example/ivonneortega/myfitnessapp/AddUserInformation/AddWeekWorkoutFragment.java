package com.example.ivonneortega.myfitnessapp.AddUserInformation;

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

import com.example.ivonneortega.myfitnessapp.Data.Day;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.ArrayList;
import java.util.List;


public class AddWeekWorkoutFragment extends Fragment implements View.OnClickListener {

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

    public AddWeekWorkoutFragment() {
        // Required empty public constructor
    }


    public static AddWeekWorkoutFragment newInstance(String day, Day dayObject) {
        AddWeekWorkoutFragment fragment = new AddWeekWorkoutFragment();
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
        return inflater.inflate(R.layout.fragment_add_week_workout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMonday = (TextView) view.findViewById(R.id.letter_monday);
        mTuesday = (TextView) view.findViewById(R.id.letter_tuesday);
        mWednesday = (TextView) view.findViewById(R.id.letter_wednesday);
        mThrusday = (TextView) view.findViewById(R.id.letter_thrusday);
        mFriday = (TextView) view.findViewById(R.id.letter_friday);
        mSaturday = (TextView) view.findViewById(R.id.letter_saturday);
        mSunday = (TextView) view.findViewById(R.id.letter_sunday);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mWorkoutName = (TextView) view.findViewById(R.id.workout_name_title);
        if(mWorkout!=null && mWorkout.getNameOfWorkout()!=null)
            mWorkoutName.setText(mWorkout.getNameOfWorkout());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new ExerciseRecyclerViewAdapter(mList, ExerciseRecyclerViewAdapter.TYPE_USER_CREATING);
        mRecyclerView.setAdapter(mAdapter);



        mRestDay = (Switch) view.findViewById(R.id.rest_day);

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
                mMonday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
                break;
            case "Tuesday":
                mTuesday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
                break;
            case "Wednesday":
                mWednesday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
                break;
            case "Thrusday":
                mThrusday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
                break;
            case "Friday":
                mFriday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
                break;
            case "Saturday":
                mSaturday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
                break;
            case "Sunday":
                mSunday.setTextColor(ContextCompat.getColor(mMonday.getContext(), R.color.colorTextSelectedWeek));
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
            case R.id.letter_monday:
                mListener.changeDay("Monday",mWorkout, mDay);
                break;
            case R.id.letter_tuesday:
                mListener.changeDay("Tuesday",mWorkout, mDay);
                break;
            case R.id.letter_wednesday:
                mListener.changeDay("Wednesday",mWorkout, mDay);
                break;
            case R.id.letter_thrusday:
                mListener.changeDay("Thrusday",mWorkout, mDay);
                break;
            case R.id.letter_friday:
                mListener.changeDay("Friday",mWorkout, mDay);
                break;
            case R.id.letter_saturday:
                mListener.changeDay("Saturday",mWorkout, mDay);
                break;
            case R.id.letter_sunday:
                mListener.changeDay("Sunday",mWorkout, mDay);
                break;
            case R.id.rest_day:
                if(mRestDay.isChecked())
                    mRecyclerView.setVisibility(View.GONE);
                else
                    mRecyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.fab:
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
