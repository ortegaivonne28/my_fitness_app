package com.example.ivonneortega.myfitnessapp.Workout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.AddUserInformation.ExerciseRecyclerViewAdapter;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.SingleExercise;
import com.example.ivonneortega.myfitnessapp.Data.SuperSet;
import com.example.ivonneortega.myfitnessapp.Data.TripleSet;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * Created by ivonneortega on 5/21/17.
 */

public class WorkoutRecyclerViewAdapter extends RecyclerView.Adapter<WorkoutRecyclerViewAdapter.WorkoutViewHolder> {

    List<Exercise> mExerciseList;

    public WorkoutRecyclerViewAdapter(List<Exercise> exerciseList) {
        mExerciseList = exerciseList;
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_custom_view, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        if(mExerciseList.get(position) instanceof SingleExercise)
        {
            SingleExercise singleExercise = (SingleExercise) mExerciseList.get(position);
            holder.mExerciseOne.setVisibility(View.VISIBLE);
            holder.mExerciseOne.setText("Exercise: "+singleExercise.getName());
        }
        if(mExerciseList.get(position) instanceof SuperSet)
        {
            SuperSet superSet = (SuperSet) mExerciseList.get(position);
            holder.mExerciseOne.setVisibility(View.VISIBLE);
            holder.mExerciseOne.setText("Exercise A: "+superSet.getNameOne());
            holder.mExerciseTwo.setVisibility(View.VISIBLE);
            holder.mExerciseTwo.setText("Exercise B: "+superSet.getNameTwo());
        }
        if(mExerciseList.get(position) instanceof TripleSet)
        {
            TripleSet tripleSet = (TripleSet) mExerciseList.get(position);
            holder.mExerciseOne.setVisibility(View.VISIBLE);
            holder.mExerciseOne.setText("Exercise A: "+tripleSet.getNameOne());
            holder.mExerciseTwo.setVisibility(View.VISIBLE);
            holder.mExerciseTwo.setText("Exercise B: "+tripleSet.getNameTwo());
            holder.mExerciseThree.setVisibility(View.VISIBLE);
            holder.mExerciseThree.setText("Exercise C: "+tripleSet.getNameThree());
        }
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(holder.mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        holder.mRecyclerView.setAdapter(new StartWorkoutExerciseRecyclerViewAdapter(mExerciseList.get(position)));
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder{

        public TextView mExerciseOne;
        public TextView mExerciseTwo;
        public TextView mExerciseThree;
        public RecyclerView mRecyclerView;

        public WorkoutViewHolder(View itemView) {
            super(itemView);
            mExerciseOne = (TextView) itemView.findViewById(R.id.exercise_name_one);
            mExerciseTwo = (TextView) itemView.findViewById(R.id.exercise_name_two);
            mExerciseThree = (TextView) itemView.findViewById(R.id.exercise_name_three);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);

        }
    }
}
