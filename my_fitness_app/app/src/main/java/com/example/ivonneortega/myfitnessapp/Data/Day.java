package com.example.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/16/17.
 */

class Day {

    long mId, mWorkoutId, mChallengeId,mCardioId;
    int mWaterIntake;
    float mWeight;

    public Day(long id, long workoutId, long challengeId, long cardioId, int waterIntake, float weight) {
        mId = id;
        mWorkoutId = workoutId;
        mChallengeId = challengeId;
        mCardioId = cardioId;
        mWaterIntake = waterIntake;
        mWeight = weight;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getWorkoutId() {
        return mWorkoutId;
    }

    public void setWorkoutId(long workoutId) {
        mWorkoutId = workoutId;
    }

    public long getChallengeId() {
        return mChallengeId;
    }

    public void setChallengeId(long challengeId) {
        mChallengeId = challengeId;
    }

    public long getCardioId() {
        return mCardioId;
    }

    public void setCardioId(long cardioId) {
        mCardioId = cardioId;
    }

    public int getWaterIntake() {
        return mWaterIntake;
    }

    public void setWaterIntake(int waterIntake) {
        mWaterIntake = waterIntake;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float weight) {
        mWeight = weight;
    }
}
