package my.app.ivonneortega.myfitnessapp.Data;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Day {

    private String mId, mWeekId;
    private Workout mWorkout;
    private Challenges mChallenge;
    private Cardio mCardio;
    private int mWaterIntake;
    private float mWeight;

    public String getWeekId() {
        return mWeekId;
    }

    public void setWeekId(String weekId) {
        mWeekId = weekId;
    }

    public Day(String id, Workout workout, Challenges challenge, Cardio cardio, int waterIntake, float weight) {
        mId = id;
        mWorkout = workout;
        mChallenge = challenge;
        mCardio = cardio;
        mWaterIntake = waterIntake;
        mWeight = weight;
    }

    public Day() {
    }

    public Day(Workout workout) {
        mWorkout = workout;
        mChallenge = null;
        mCardio = null;
        mWaterIntake = 0;
        mWeight = 0;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Workout getWorkout() {
        return mWorkout;
    }

    public void setWorkout(Workout workout) {
        mWorkout = workout;
    }

    public Challenges getChallenge() {
        return mChallenge;
    }

    public void setChallenge(Challenges challenge) {
        mChallenge = challenge;
    }

    public Cardio getCardio() {
        return mCardio;
    }

    public void setCardio(Cardio cardio) {
        mCardio = cardio;
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
