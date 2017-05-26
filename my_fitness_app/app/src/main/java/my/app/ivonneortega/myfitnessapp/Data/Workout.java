package my.app.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Workout {

    private String mId, mDayId, mChallengeId;
    private String mNameOfWorkout;
    private List<Exercise> mExercises;

    public String getDayId() {
        return mDayId;
    }

    public String getChallengeId() {
        return mChallengeId;
    }

    public void setChallengeId(String challengeId) {
        mChallengeId = challengeId;
    }

    public void setDayId(String dayId) {
        mDayId = dayId;
    }

    public Workout(String id, String nameOfWorkout, List<Exercise> exercises) {
        mId = id;
        mNameOfWorkout = nameOfWorkout;
        mExercises = exercises;
    }

    public Workout() {
        mNameOfWorkout=null;
        mExercises = new ArrayList<>();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNameOfWorkout() {
        return mNameOfWorkout;
    }

    public void setNameOfWorkout(String nameOfWorkout) {
        mNameOfWorkout = nameOfWorkout;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> exercises) {
        mExercises = exercises;
    }
}
