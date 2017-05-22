package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class TripleSet extends Exercise {

    private int mSets, mRepsFirstExercise, mRepsSecondExercise, mRepsThirdExercise;
    private String mNameOne, mNameTwo, mNameThree;
    private List<Repetition> mRepetitionsFirst;
    private List<Repetition> mRepetitionsSecond;
    private List<Repetition> mRepetitionsThird;

    public TripleSet(int sets, int repsFirstExercise, int repsSecondExercise, int repsThirdExercise) {
        mSets = sets;
        mRepsFirstExercise = repsFirstExercise;
        mRepsSecondExercise = repsSecondExercise;
        mRepsThirdExercise = repsThirdExercise;
        mRepetitionsFirst = new ArrayList<>();
        mRepetitionsSecond = new ArrayList<>();
        mRepetitionsThird = new ArrayList<>();
    }

    public TripleSet(int sets, int repsFirstExercise, int repsSecondExercise, int repsThirdExercise, String nameOne, String nameTwo, String nameThree, List<Repetition> repetitionsFirst, List<Repetition> repetitionsSecond, List<Repetition> repetitionsThird) {
        mSets = sets;
        mRepsFirstExercise = repsFirstExercise;
        mRepsSecondExercise = repsSecondExercise;
        mRepsThirdExercise = repsThirdExercise;
        mNameOne = nameOne;
        mNameTwo = nameTwo;
        mNameThree = nameThree;
        mRepetitionsFirst = repetitionsFirst;
        mRepetitionsSecond = repetitionsSecond;
        mRepetitionsThird = repetitionsThird;
    }

    public TripleSet() {
        super();
        mSets=0;
        mRepsFirstExercise=0;
        mRepsSecondExercise=0;
        mRepsThirdExercise = 0;
        mNameOne=null;
        mNameTwo=null;
        mNameThree = null;
        mRepetitionsFirst = new ArrayList<>();
        mRepetitionsSecond = new ArrayList<>();
        mRepetitionsThird = new ArrayList<>();

    }

    public String getNameOne() {
        return mNameOne;
    }

    public void setNameOne(String nameOne) {
        mNameOne = nameOne;
    }

    public String getNameTwo() {
        return mNameTwo;
    }

    public void setNameTwo(String nameTwo) {
        mNameTwo = nameTwo;
    }

    public String getNameThree() {
        return mNameThree;
    }

    public void setNameThree(String nameThree) {
        mNameThree = nameThree;
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
