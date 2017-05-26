package my.app.ivonneortega.myfitnessapp.Data;

import java.util.HashMap;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Week {

    private String mId, mRoutineId;
    private HashMap<String, Day> mDayHashMap;

    public String getRoutineId() {
        return mRoutineId;
    }

    public void setRoutineId(String routineId) {
        mRoutineId = routineId;
    }

    public Week() {
        long mId = -1;
        mDayHashMap = new HashMap<>();
    }

    public Week(String id, HashMap dayList) {
        mId = id;
        mDayHashMap = dayList;
    }

    public Week(HashMap<String, Day> dayHashMap) {
        mDayHashMap = dayHashMap;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public HashMap<String, Day> getDayHashMap() {
        return mDayHashMap;
    }

    public void setDayHashMap(HashMap dayHashMap) {
        mDayHashMap = dayHashMap;
    }
}
