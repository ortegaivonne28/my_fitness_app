package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Challenges {

    long mId, mFriendId, mExerciseId;

    public Challenges(long id, long friendId, long exerciseId) {
        mId = id;
        mFriendId = friendId;
        mExerciseId = exerciseId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getFriendId() {
        return mFriendId;
    }

    public void setFriendId(long friendId) {
        mFriendId = friendId;
    }

    public long getExerciseId() {
        return mExerciseId;
    }

    public void setExerciseId(long exerciseId) {
        mExerciseId = exerciseId;
    }
}
