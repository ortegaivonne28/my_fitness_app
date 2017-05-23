package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Challenges {

    private long mId, mFriendId;
    private Workout mWorkout;

    public Challenges(long id, long friendId, Workout workout) {
        mId = id;
        mFriendId = friendId;
        mWorkout = workout;
    }

    public Challenges() {
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

    public Workout getWorkout() {
        return mWorkout;
    }

    public void setWorkout(Workout workout) {
        mWorkout = workout;
    }
}
