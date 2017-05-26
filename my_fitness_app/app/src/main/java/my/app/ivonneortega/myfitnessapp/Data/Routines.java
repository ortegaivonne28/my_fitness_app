package my.app.ivonneortega.myfitnessapp.Data;

import java.util.List;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Routines {

    private String mId;
    private String mUserId;
    private String mName;
    private int mHowLong;
    private List<Week> mWeeks;

    public Routines(String id, String name, int howLong, List<Week> weeks) {
        mId = id;
        mName = name;
        mHowLong = howLong;
        mWeeks = weeks;
    }

    public Routines(String name, int howLong, List<Week> weeks) {
        mName = name;
        mHowLong = howLong;
        mWeeks = weeks;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public Routines() {
        mName = null;
        mHowLong = -1;
        mWeeks = null;
    }

    public Routines(List<Week> weeks) {
        mWeeks = weeks;
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

    public int getHowLong() {
        return mHowLong;
    }

    public void setHowLong(int howLong) {
        mHowLong = howLong;
    }

    public List<Week> getWeeks() {
        return mWeeks;
    }

    public void setWeeks(List<Week> weeks) {
        mWeeks = weeks;
    }
}
