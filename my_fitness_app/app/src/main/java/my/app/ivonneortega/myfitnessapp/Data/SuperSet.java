package my.app.ivonneortega.myfitnessapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class SuperSet extends Exercise {

    private int mSets, mRepsForFirst, mRepsForSecond;
    private String mNameOne, mNameTwo;
    private List<Repetition> mRepetitionsFirst;
    private List<Repetition> mRepetitionsSecond;

    public SuperSet(int sets, int repsForFirst, int repsForSecond, String nameOne, String nameTwo, List<Repetition> repetitionsFirst, List<Repetition> repetitionsSecond) {
        mSets = sets;
        mRepsForFirst = repsForFirst;
        mRepsForSecond = repsForSecond;
        mNameOne = nameOne;
        mNameTwo = nameTwo;
        mRepetitionsFirst = repetitionsFirst;
        mRepetitionsSecond = repetitionsSecond;
    }

    public SuperSet() {
        super();
        mSets=0;
        mRepsForFirst=0;
        mRepsForSecond=0;
        mNameOne=null;
        mNameTwo=null;
        mRepetitionsFirst = new ArrayList<>();
        mRepetitionsSecond = new ArrayList<>();

    }

    public String getNameOne() {
        return mNameOne;
    }

    public void setNameOne(String nameOne) {
        mNameOne = nameOne;
    }

    public String getNameTwo() {
        return mNameTwo;
    }

    public void setNameTwo(String nameTwo) {
        mNameTwo = nameTwo;
    }

    public int getSets() {
        return mSets;
    }

    public void setSets(int sets) {
        mSets = sets;
    }

    public int getRepsForFirst() {
        return mRepsForFirst;
    }

    public void setRepsForFirst(int repsForFirst) {
        mRepsForFirst = repsForFirst;
    }

    public int getRepsForSecond() {
        return mRepsForSecond;
    }

    public void setRepsForSecond(int repsForSecond) {
        mRepsForSecond = repsForSecond;
    }

    public List<Repetition> getRepetitionsFirst() {
        return mRepetitionsFirst;
    }

    public void setRepetitionsFirst(List<Repetition> repetitionsFirst) {
        mRepetitionsFirst = repetitionsFirst;
    }

    public List<Repetition> getRepetitionsSecond() {
        return mRepetitionsSecond;
    }

    public void setRepetitionsSecond(List<Repetition> repetitionsSecond) {
        mRepetitionsSecond = repetitionsSecond;
    }
}
