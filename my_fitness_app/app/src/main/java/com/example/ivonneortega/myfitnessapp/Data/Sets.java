package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/21/17.
 */

public class Sets {

    private int mRepOne, mRepTwo, mRepThree;
    private String mExerciseId;
    private double mWeightOne, mWeightTwo, mWeightThree;
    private String mType;

    public String getExerciseId() {
        return mExerciseId;
    }

    public void setExerciseId(String exerciseId) {
        mExerciseId = exerciseId;
    }

    public Sets(int repOne, int repTwo, int repThree, long weightOne, long weightTwo, long weightThree, String type) {
        mRepOne = repOne;
        mRepTwo = repTwo;
        mRepThree = repThree;
        mWeightOne = weightOne;
        mWeightTwo = weightTwo;
        mWeightThree = weightThree;
        mType = type;
    }

    public Sets() {
        mRepOne = -1;
        mRepTwo = -1;
        mRepThree = -1;
        mWeightOne = -1.0;
        mWeightTwo = -1.0;
        mWeightThree = -1.0;
    }

    public int getRepOne() {
        return mRepOne;
    }

    public void setRepOne(int repOne) {
        mRepOne = repOne;
    }

    public int getRepTwo() {
        return mRepTwo;
    }

    public void setRepTwo(int repTwo) {
        mRepTwo = repTwo;
    }

    public int getRepThree() {
        return mRepThree;
    }

    public void setRepThree(int repThree) {
        mRepThree = repThree;
    }

    public double getWeightOne() {
        return mWeightOne;
    }

    public void setWeightOne(double weightOne) {
        mWeightOne = weightOne;
    }

    public double getWeightTwo() {
        return mWeightTwo;
    }

    public void setWeightTwo(double weightTwo) {
        mWeightTwo = weightTwo;
    }

    public double getWeightThree() {
        return mWeightThree;
    }

    public void setWeightThree(double weightThree) {
        mWeightThree = weightThree;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
