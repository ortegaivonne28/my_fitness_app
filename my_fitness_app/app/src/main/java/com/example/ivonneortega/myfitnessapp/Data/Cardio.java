package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Cardio {
    private boolean isSteady;
    private int mSeconds,mHowMany, mRest;

    public Cardio(boolean isSteady, int seconds, int howMany, int rest) {
        this.isSteady = isSteady;
        mSeconds = seconds;
        mHowMany = howMany;
        mRest = rest;
    }

    public Cardio(boolean isSteady, int seconds) {
        this.isSteady = isSteady;
        mSeconds = seconds;
    }

    public boolean isSteady() {
        return isSteady;
    }

    public void setSteady(boolean steady) {
        isSteady = steady;
    }

    public int getSeconds() {
        return mSeconds;
    }

    public void setSeconds(int seconds) {
        mSeconds = seconds;
    }

    public int getHowMany() {
        return mHowMany;
    }

    public void setHowMany(int howMany) {
        mHowMany = howMany;
    }

    public int getRest() {
        return mRest;
    }

    public void setRest(int rest) {
        mRest = rest;
    }
}
