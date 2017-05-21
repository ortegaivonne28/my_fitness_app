package com.example.ivonneortega.myfitnessapp.Workout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.SingleExercise;
import com.example.ivonneortega.myfitnessapp.Data.SuperSet;
import com.example.ivonneortega.myfitnessapp.Data.TripleSet;
import com.example.ivonneortega.myfitnessapp.R;

/**
 * Created by ivonneortega on 5/21/17.
 */

public class StartWorkoutExerciseRecyclerViewAdapter extends RecyclerView.Adapter {

    Exercise mExercise;
    
    public static final int SINGLE = 0;
    public static final int SUPER = 2;
    public static final int TRIPLE = 3;

    public StartWorkoutExerciseRecyclerViewAdapter(Exercise exercise) {
        mExercise = exercise;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case SINGLE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.start_workout_exercise_single, parent, false);
                return new StartWorkoutSingleExerciseViewHolder(view);
            case SUPER:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.start_workout_super, parent, false);
                return new StartWorkoutSuperExerciseViewHolder(view1);
            case TRIPLE:
                View view2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.start_workout_triple, parent, false);
                return new StartWorkoutTripleExerciseViewHolder(view2);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int pos = position+1;
        if(holder instanceof StartWorkoutSingleExerciseViewHolder)
        {
           ((StartWorkoutSingleExerciseViewHolder) holder).mSetNumber.setText("Set #"+pos);
        }
        else if(holder instanceof StartWorkoutSuperExerciseViewHolder)
        {
            ((StartWorkoutSuperExerciseViewHolder) holder).mSetNumber.setText("Set #"+pos);
        }
        else if(holder instanceof StartWorkoutTripleExerciseViewHolder)
        {
            ((StartWorkoutTripleExerciseViewHolder) holder).mSetNumber.setText("Set #"+pos);
        }
    }


    @Override
    public int getItemCount() {
        if(mExercise instanceof SingleExercise)
            return ((SingleExercise) mExercise).getSets();
        if(mExercise instanceof SuperSet)
            return ((SuperSet) mExercise).getSets();
        if(mExercise instanceof TripleSet)
            return ((TripleSet) mExercise).getSets();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mExercise instanceof SingleExercise)
            return SINGLE;
        if(mExercise instanceof SuperSet)
            return SUPER;
        if(mExercise instanceof TripleSet)
            return TRIPLE;
        return 0;
    }

    public class StartWorkoutSingleExerciseViewHolder extends RecyclerView.ViewHolder{

        EditText mSetOne, mWeightOne;
        TextView mSetNumber;

        public StartWorkoutSingleExerciseViewHolder(View itemView) {
            super(itemView);
            mSetOne = (EditText) itemView.findViewById(R.id.number_of_reps_one);
            mWeightOne = (EditText) itemView.findViewById(R.id.weight_one);
            mSetNumber = (TextView) itemView.findViewById(R.id.set);
        }
    }

    public class StartWorkoutSuperExerciseViewHolder extends RecyclerView.ViewHolder{
        EditText mSetOne, mWeightOne;
        EditText mSetTwo, mWeightTwo;
        TextView mSetNumber;

        public StartWorkoutSuperExerciseViewHolder(View itemView) {
            super(itemView);
            mSetOne = (EditText) itemView.findViewById(R.id.number_of_reps_one);
            mWeightOne = (EditText) itemView.findViewById(R.id.weight_one);
            mSetTwo = (EditText) itemView.findViewById(R.id.number_of_reps_two);
            mWeightTwo = (EditText) itemView.findViewById(R.id.weight_two);
            mSetNumber = (TextView) itemView.findViewById(R.id.set);
        }
    }

    public class StartWorkoutTripleExerciseViewHolder extends RecyclerView.ViewHolder{
        EditText mSetOne, mWeightOne;
        EditText mSetTwo, mWeightTwo;
        EditText mSetThree, mWeightThree;
        TextView mSetNumber;

        public StartWorkoutTripleExerciseViewHolder(View itemView) {
            super(itemView);
            mSetOne = (EditText) itemView.findViewById(R.id.number_of_reps_one);
            mWeightOne = (EditText) itemView.findViewById(R.id.weight_one);
            mSetTwo = (EditText) itemView.findViewById(R.id.number_of_reps_two);
            mWeightTwo = (EditText) itemView.findViewById(R.id.weight_two);
            mSetThree = (EditText) itemView.findViewById(R.id.number_of_reps_three);
            mWeightThree = (EditText) itemView.findViewById(R.id.weight_three);
            mSetNumber = (TextView) itemView.findViewById(R.id.set);
        }
    }


}
