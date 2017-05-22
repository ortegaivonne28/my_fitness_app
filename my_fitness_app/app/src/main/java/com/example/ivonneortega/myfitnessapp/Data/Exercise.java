package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Exercise {

    private String mId, mWorkoutID;
    private String mName;
    private List<Sets> mSetsList;

    public List<Sets> getSetsList() {
        return mSetsList;
    }

    public void setSetsList(List<Sets> setsList) {
        mSetsList = setsList;
    }

    public String getWorkoutID() {
        return mWorkoutID;
    }

    public void setWorkoutID(String workoutID) {
        mWorkoutID = workoutID;
    }

    public Exercise(String id, String name) {
        mId = id;
        mName = name;
    }

    public Exercise() {
        mName = null;
        mSetsList = new ArrayList<>();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
