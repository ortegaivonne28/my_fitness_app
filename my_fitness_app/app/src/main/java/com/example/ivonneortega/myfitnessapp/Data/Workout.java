package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Workout {

    long mId;
    String mNameOfWorkout;
    List<Exercise> mExercises;

    public Workout(long id, String nameOfWorkout, List<Exercise> exercises) {
        mId = id;
        mNameOfWorkout = nameOfWorkout;
        mExercises = exercises;
    }

    public Workout() {
        mNameOfWorkout=null;
        mExercises = new ArrayList<>();
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getNameOfWorkout() {
        return mNameOfWorkout;
    }

    public void setNameOfWorkout(String nameOfWorkout) {
        mNameOfWorkout = nameOfWorkout;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> exercises) {
        mExercises = exercises;
    }
}
