package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Challenges {

    private String mId, mFriendId, mUserId;
    private Workout mWorkoutFriend, mWorkoutUser;

    public Challenges(String id, String friendId, String userId, Workout workoutFriend, Workout workoutUser) {
        mId = id;
        mFriendId = friendId;
        mUserId = userId;
        mWorkoutFriend = workoutFriend;
        mWorkoutUser = workoutUser;
    }

    public Challenges() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFriendId() {
        return mFriendId;
    }

    public void setFriendId(String friendId) {
        mFriendId = friendId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public Workout getWorkoutFriend() {
        return mWorkoutFriend;
    }

    public void setWorkoutFriend(Workout workoutFriend) {
        mWorkoutFriend = workoutFriend;
    }

    public Workout getWorkoutUser() {
        return mWorkoutUser;
    }

    public void setWorkoutUser(Workout workoutUser) {
        mWorkoutUser = workoutUser;
    }
}
