package com.example.ivonneortega.myfitnessapp.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Week {

    long mId;
    HashMap<String, Day> mDayHashMap;

    public Week() {
        long mId = -1;
        mDayHashMap = new HashMap<>();
    }

    public Week(long id, HashMap dayList) {
        mId = id;
        mDayHashMap = dayList;
    }

    public Week(HashMap<String, Day> dayHashMap) {
        mDayHashMap = dayHashMap;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public HashMap<String, Day> getDayHashMap() {
        return mDayHashMap;
    }

    public void setDayHashMap(HashMap dayHashMap) {
        mDayHashMap = dayHashMap;
    }
}
