package com.example.ivonneortega.myfitnessapp.Routines;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * Created by ivonneortega on 5/20/17.
 */

public class ExercisesRecyclerViewAdapter extends RecyclerView.Adapter<ExercisesRecyclerViewAdapter.ExercisesViewHolder> {

    List<Exercise> mExerciseList;

    @Override
    public ExercisesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_view_routines, parent, false);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExercisesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder
    {

        public ExercisesViewHolder(View itemView) {
            super(itemView);
        }
    }

}
