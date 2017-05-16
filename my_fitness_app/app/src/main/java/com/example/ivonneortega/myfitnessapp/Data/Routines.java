package com.example.ivonneortega.myfitnessapp.Data;

import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

class Routines {

    long mId;
    String mName;
    int mHowLong;
    List<Week> mWeeks;

    public Routines(long id, String name, int howLong, List<Week> weeks) {
        mId = id;
        mName = name;
        mHowLong = howLong;
        mWeeks = weeks;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getHowLong() {
        return mHowLong;
    }

    public void setHowLong(int howLong) {
        mHowLong = howLong;
    }

    public List<Week> getWeeks() {
        return mWeeks;
    }

    public void setWeeks(List<Week> weeks) {
        mWeeks = weeks;
    }
}
