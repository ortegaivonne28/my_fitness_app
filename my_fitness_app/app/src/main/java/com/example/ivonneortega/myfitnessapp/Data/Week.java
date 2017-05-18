package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Week {

    long mId;
    List<Day> mDayList;

    public Week() {
        long mId = -1;
        mDayList = new ArrayList<>();
    }

    public Week(long id, List<Day> dayList) {
        mId = id;
        mDayList = dayList;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<Day> getDayList() {
        return mDayList;
    }

    public void setDayList(List<Day> dayList) {
        mDayList = dayList;
    }
}
