package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class SuperSet {

    int mSets, mRepsForFirst, mRepsForSecond;
    List<Repetition> mRepetitionsFirst;
    List<Repetition> mRepetitionsSecond;

    public SuperSet(int sets, int repsForFirst, int repsForSecond) {
        mSets = sets;
        mRepsForFirst = repsForFirst;
        mRepsForSecond = repsForSecond;
        mRepetitionsFirst = new ArrayList<>();
        mRepetitionsSecond = new ArrayList<>();
    }

    public int getSets() {
        return mSets;
    }

    public void setSets(int sets) {
        mSets = sets;
    }

    public int getRepsForFirst() {
        return mRepsForFirst;
    }

    public void setRepsForFirst(int repsForFirst) {
        mRepsForFirst = repsForFirst;
    }

    public int getRepsForSecond() {
        return mRepsForSecond;
    }

    public void setRepsForSecond(int repsForSecond) {
        mRepsForSecond = repsForSecond;
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
}
