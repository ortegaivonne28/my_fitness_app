package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class TripleSet {

    int mSets, mRepsFirstExercise, mRepsSecondExercise, mRepsThirdExercise;
    List<Repetition> mRepetitionsFirst;
    List<Repetition> mRepetitionsSecond;
    List<Repetition> mRepetitionsThird;

    public TripleSet(int sets, int repsFirstExercise, int repsSecondExercise, int repsThirdExercise) {
        mSets = sets;
        mRepsFirstExercise = repsFirstExercise;
        mRepsSecondExercise = repsSecondExercise;
        mRepsThirdExercise = repsThirdExercise;
        mRepetitionsFirst = new ArrayList<>();
        mRepetitionsSecond = new ArrayList<>();
        mRepetitionsThird = new ArrayList<>();
    }

    public int getSets() {
        return mSets;
    }

    public void setSets(int sets) {
        mSets = sets;
    }

    public int getRepsFirstExercise() {
        return mRepsFirstExercise;
    }

    public void setRepsFirstExercise(int repsFirstExercise) {
        mRepsFirstExercise = repsFirstExercise;
    }

    public int getRepsSecondExercise() {
        return mRepsSecondExercise;
    }

    public void setRepsSecondExercise(int repsSecondExercise) {
        mRepsSecondExercise = repsSecondExercise;
    }

    public int getRepsThirdExercise() {
        return mRepsThirdExercise;
    }

    public void setRepsThirdExercise(int repsThirdExercise) {
        mRepsThirdExercise = repsThirdExercise;
    }

    public List<Repetition> getRepetitionsFirst() {
        return mRepetitionsFirst;
    }

    public void setRepetitionsFirst(List<Repetition> repetitionsFirst) {
        mRepetitionsFirst = repetitionsFirst;
    }

    public List<Repetition> getRepetitionsSecond() {
        return mRepetitionsSecond;
    }

    public void setRepetitionsSecond(List<Repetition> repetitionsSecond) {
        mRepetitionsSecond = repetitionsSecond;
    }

    public List<Repetition> getRepetitionsThird() {
        return mRepetitionsThird;
    }

    public void setRepetitionsThird(List<Repetition> repetitionsThird) {
        mRepetitionsThird = repetitionsThird;
    }
}
