package com.example.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class User {

    private String mId;
    private String mEmail, mName, mLastname, mUsername;
    private float mWeight, mDesiredWeight;
    private int mHowMuchWater;
    private List<Long> mFriends;
    private List<Routines> mRoutines;
    private List<Challenges> mChallenges;

    public User() {
    }

    public User(String id, String email, String name, String lastname, String username, float weight, float desiredWeight, int howMuchWater) {
        mId = id;
        mEmail = email;
        mName = name;
        mLastname = lastname;
        mUsername = username;
        mWeight = weight;
        mDesiredWeight = desiredWeight;
        mHowMuchWater = howMuchWater;
        mFriends = new ArrayList<>();
        mRoutines = new ArrayList<>();
        mChallenges = new ArrayList<>();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastname() {
        return mLastname;
    }

    public void setLastname(String lastname) {
        mLastname = lastname;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        mWeight = weight;
    }

    public float getDesiredWeight() {
        return mDesiredWeight;
    }

    public void setDesiredWeight(float desiredWeight) {
        mDesiredWeight = desiredWeight;
    }

    public int getHowMuchWater() {
        return mHowMuchWater;
    }

    public void setHowMuchWater(int howMuchWater) {
        mHowMuchWater = howMuchWater;
    }

    public List<Long> getFriends() {
        return mFriends;
    }

    public void setFriends(List<Long> friends) {
        mFriends = friends;
    }

    public List<Routines> getRoutines() {
        return mRoutines;
    }

    public void setRoutines(List<Routines> routines) {
        mRoutines = routines;
    }

    public List<Challenges> getChallenges() {
        return mChallenges;
    }

    public void setChallenges(List<Challenges> challenges) {
        mChallenges = challenges;
    }
}
