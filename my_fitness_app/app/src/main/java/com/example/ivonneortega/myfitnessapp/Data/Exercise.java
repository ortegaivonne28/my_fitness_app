package com.example.ivonneortega.myfitnessapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Exercise {

    long mId;
    String mName;

    public Exercise(long id, String name) {
        mId = id;
        mName = name;
    }

    public Exercise() {
        mName = null;
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

}
