package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class SingleExercise extends Exercise {

    int mSets, mReps;
    List<Repetition> mRepetitions;

    public SingleExercise(int sets, int reps) {
        super();
        mSets = sets;
        mReps = reps;
        mRepetitions = new ArrayList<>();
    }

    public int getSets() {
        return mSets;
    }

    public void setSets(int sets) {
        mSets = sets;
    }

    public int getReps() {
        return mReps;
    }

    public void setReps(int reps) {
        mReps = reps;
    }

    public List<Repetition> getRepetitions() {
        return mRepetitions;
    }

    public void setRepetitions(List<Repetition> repetitions) {
        mRepetitions = repetitions;
    }
}
