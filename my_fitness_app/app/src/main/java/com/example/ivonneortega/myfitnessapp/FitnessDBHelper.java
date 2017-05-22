package com.example.ivonneortega.myfitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.ivonneortega.myfitnessapp.Data.Day;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.Data.Sets;
import com.example.ivonneortega.myfitnessapp.Data.SingleExercise;
import com.example.ivonneortega.myfitnessapp.Data.SuperSet;
import com.example.ivonneortega.myfitnessapp.Data.TripleSet;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.Data.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by ivonneortega on 5/19/17.
 */

public class FitnessDBHelper extends SQLiteOpenHelper {

    private static final String TAG = FitnessDBHelper.class.getCanonicalName();

    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FITNESS_DB.db";
    public static final String USER_TABLE_NAME = "USER_TABLE";
    public static final String ROUTINES_TABLE_NAME = "ROUTINE_TABLE";
    public static final String WORKOUT_TABLE_NAME = "WORKOUT_TABLE";
    public static final String TYPE_OF_EXERCISE_NAME = "TYPE_OF_EXERCISE_TABLE";
    public static final String EXERCISE_NAME = "EXERCISE_TABLE";
    public static final String DAY_USER_NAME = "DAY_TABLE";
    public static final String WORK_COMPLETED_NAME = "WORKOUT_COMPLETED_TABLE";
    public static final String EXERCISE_COMPLETED_NAME = "EXERCISE_COMPLETED";
    public static final String WEEK_NAME = "week_name";
    public static final String DAY_WORKOUT_NAME = "day_workout_name";
    public static final String SET_TABLE_NAME = "set_table_name";

    public static final String COL_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "user_email";
    public static final String COL_USER_CURRENT_WEIGHT = "current_weight";
    public static final String COL_USER_DESIRED_WEGIHT = "desired_weight";
    public static final String COL_USER_AGE = "user_age";

    public static final String COL_ROUTINE_ID = "routine_id";
    public static final String COL_ROUTINE_NAME = "routine_name";

    public static final String COL_WEEK_ID = "week_id";
    //ROUTINE ID

    public static final String COL_WORKOUT_ID = "workout_id";
    public static final String COL_WORKOUT_NAME = "workout_name";
    //DAY ID

    public static final String COL_TYPE_OF_EXERCISE_NAME = "type_of_exercise";
    public static final String COL_TYPE_OF_EXERCISE_ID = "exercise_type_id";

    public static final String COL_EXERCISE_ID = "exercise_id";
    //WORKOUT ID
    //COL_TYPE_OF_EXERCISE_ID
    public static final String COL_EXERCISE_ONE = "exercise_one";
    public static final String COL_EXERCISE_TWO = "exercise_two";
    public static final String COL_EXERCISE_THREE = "exercise_three";
    public static final String COL_SETS = "sets";
    public static final String COL_REPS_ONE = "reps_one";
    public static final String COL_REPS_TWO = "reps_two";
    public static final String COL_REPS_THREE = "reps_three";

    public static final String COL_DAY_ID = "day_id";
    public static final String COL_DAY_DATE = "day_date";
    //WORKOUT ID
    // CARDIO ID
    public static final String COL_WEIGHT = "weight";
    public static final String COL_GLASSES_OF_WATER = "glasses_of_water";
    public static final String COL_WORKOUT_COMPLETED = "workout_completed";
    public static final String COL_CARDIO_COMPLETED = "cardio_completed";


    public static final String COL_DAY_WORKOUT_ID = "day_id";
    public static final String COL_DAY_WORKOUT_WEEK_ID = "week_id";
    public static final String COL_DAY_WORKOUT_DAY_NAME = "day_name";
    public static final String COL_DAY_WEEK_ID = "workout_id";

    public static final String COL_WORKOUT_COMPLETED_ID = "workout_completed_id";
    public static final String COL_WORKOUT_COMPLETED_ID_OF_WORKOUT = "workout_main_id";

    public static final String COL_EXERCISE_COMPLETED_ID = "exercise_completed_id";
    public static final String COL_EXERCISE_REPS_ONE = "exercise_completed_reps_one";
    public static final String COL_EXERCISE_REPS_TWO = "exercise_completed_reps_two";
    public static final String COL_EXERCISE_REPS_THREE = "exercise_completed_reps_three";
    public static final String COL_EXERCISE_SETS = "exercise_completed_sets";
    //WORKOUT_COMPLETED_ID


    public static final String COL_SET_ID = "set_id";
    //COL_EXERCISE_COMPLETED_ID
    public static final String COL_SET_TYPE = "set_type";
    public static final String COL_SET_REPS_ONE = "reps_one";
    public static final String COL_SET_REPS_TWO = "reps_two";
    public static final String COL_SET_REPS_THREE = "reps_three";
    public static final String COl_SET_WEIGHT_ONE = "weight_one";
    public static final String COL_SET_WEIGHT_TWO = "weight_two";
    public static final String COL_SET_WEIGHT_THREE = "weight_three";

    public static final String COL_CARDIO_ID = "cardio_id";



    public static final String USER_COLUMNS = COL_ID+", "+COL_USER_NAME+", "+COL_USER_EMAIL+", "+COL_USER_AGE+", "+COL_USER_CURRENT_WEIGHT+", "+COL_USER_DESIRED_WEGIHT;

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_USER_NAME + " TEXT, " +
                    COL_USER_EMAIL + " TEXT, " +
                    COL_USER_AGE + " INTEGER, " +
                    COL_USER_CURRENT_WEIGHT + " TEXT, " +
                    COL_USER_DESIRED_WEGIHT + " TEXT" + ")";

    public static final String[] ROUTINES_COLUMNS = {COL_ID,COL_USER_NAME,COL_USER_EMAIL,COL_USER_AGE,COL_USER_CURRENT_WEIGHT,COL_USER_DESIRED_WEGIHT};

    private static final String CREATE_WORKOUT_TABLE =
            "CREATE TABLE " + WORKOUT_TABLE_NAME +
                    "(" +
                    COL_WORKOUT_ID + " INTEGER PRIMARY KEY, " +
                    COL_DAY_ID + " INTEGER," +
                    COL_WORKOUT_NAME + " TEXT"+ ")";

    private static final String CREATE_WEEK_TABLE =
            "CREATE TABLE " + WEEK_NAME +
                    "(" +
                    COL_WEEK_ID + " INTEGER PRIMARY KEY, " +
                    COL_ROUTINE_ID + " TEXT"+ ")";

    private static final String CREATE_ROUTINE_TABLE =
            "CREATE TABLE " + ROUTINES_TABLE_NAME +
                    "(" +
                    COL_ROUTINE_ID + " INTEGER PRIMARY KEY, " +
                    COL_ROUTINE_NAME + " TEXT"+ ")";

    private static final String CREATE_TYPE_OF_EXERCISE_TABLE =
            "CREATE TABLE " + TYPE_OF_EXERCISE_NAME +
                    "(" +
                    COL_TYPE_OF_EXERCISE_ID + " INTEGER PRIMARY KEY, " +
                    COL_TYPE_OF_EXERCISE_NAME + " TEXT"+ ")";

    private static final String CREATE_EXERCISE_TABLE =
            "CREATE TABLE " + EXERCISE_NAME +
                    "(" +
                    COL_EXERCISE_ID + " INTEGER PRIMARY KEY, " +
                    COL_EXERCISE_ONE + " TEXT, "+
                    COL_EXERCISE_TWO + " TEXT, "+
                    COL_TYPE_OF_EXERCISE_NAME + " TEXT,"+
                    COL_EXERCISE_THREE + " TEXT, "+
                    COL_REPS_ONE + " INTEGER, "+
                    COL_WORKOUT_ID + " INTEGER," +
                    COL_REPS_TWO + " INTEGER, "+
                    COL_REPS_THREE + " INTEGER, "+
                    COL_SETS + " INTEGER "+")";


    private static final String CREATE_DAY_TABLE =
            "CREATE TABLE " + DAY_USER_NAME +
                    "(" +
                    COL_DAY_ID + " INTEGER PRIMARY KEY, " +
                    COL_DAY_DATE + " TEXT, " +
                    COL_WORKOUT_ID + " INTEGER, "+
                    COL_CARDIO_ID + " INTEGER, "+
                    COL_WEIGHT + " TEXT, "+
                    COL_GLASSES_OF_WATER + " INTEGER, "+
                    COL_WORKOUT_COMPLETED + " INTEGER, "+
                    COL_CARDIO_COMPLETED + " INTEGER, "+
                    COL_WEEK_ID + " TEXT, "+
                    COL_SETS + " TEXT "+")";


    private static final String CREATE_WORKOUT_COMPLETED_TABLE =
            "CREATE TABLE " + WORK_COMPLETED_NAME +
                    "(" +
                    COL_WORKOUT_COMPLETED_ID + " INTEGER PRIMARY KEY, " +
                    COL_WORKOUT_COMPLETED_ID_OF_WORKOUT + " INTEGER"+ ")";

    private static final String CREATE_DAY_WORKOUT_TABLE =
            "CREATE TABLE " + DAY_WORKOUT_NAME +
                    "(" +
                    COL_DAY_WORKOUT_ID + " INTEGER PRIMARY KEY, " +
                    COL_DAY_WORKOUT_DAY_NAME + " TEXT,"+
                    COL_DAY_WORKOUT_WEEK_ID + " INTEGER," +
                    COL_DAY_WEEK_ID +" INTEGER" +")";

    private static final String CREATE_SET_TABLE =
            "CREATE TABLE " + SET_TABLE_NAME +
                    "(" +
                    COL_SET_ID + " INTEGER PRIMARY KEY, " +
                    COL_EXERCISE_COMPLETED_ID + " TEXT,"+
                    COL_SET_REPS_ONE + " INTEGER," +
                    COl_SET_WEIGHT_ONE + " TEXT,"+
                    COL_SET_WEIGHT_TWO+ " TEXT,"+
                    COL_SET_TYPE+" TEXT," +
                    COL_SET_WEIGHT_THREE+ " TEXT,"+
                    COL_SET_REPS_THREE+ " INTEGER," +
                    COL_SET_REPS_TWO +" INTEGER" +")";

    private static final String CREATE_EXERCISE_COMPLETED_TABLE =
            "CREATE TABLE " + EXERCISE_COMPLETED_NAME +
                    "(" +
                    COL_EXERCISE_COMPLETED_ID + " INTEGER PRIMARY KEY, " +
                    COL_EXERCISE_ONE + " TEXT, "+
                    COL_EXERCISE_TWO + " TEXT, "+
                    COL_EXERCISE_THREE + " TEXT, "+
                    COL_WORKOUT_COMPLETED_ID + " INTEGER,"+
                    COL_EXERCISE_REPS_ONE + " INTEGER, "+
                    COL_EXERCISE_REPS_TWO + " INTEGER, "+
                    COL_EXERCISE_REPS_THREE + " INTEGER, "+
                    COL_TYPE_OF_EXERCISE_NAME+ " TEXT,"+
                    COL_EXERCISE_SETS + " INTEGER "+")";




    private static FitnessDBHelper sInstance;

    public static FitnessDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FitnessDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private FitnessDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ROUTINE_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_TYPE_OF_EXERCISE_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_DAY_TABLE);
        db.execSQL(CREATE_WORKOUT_COMPLETED_TABLE);
        db.execSQL(CREATE_EXERCISE_COMPLETED_TABLE);
        db.execSQL(CREATE_WEEK_TABLE);
        db.execSQL(CREATE_DAY_WORKOUT_TABLE);
        db.execSQL(CREATE_SET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ROUTINES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TYPE_OF_EXERCISE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DAY_USER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORK_COMPLETED_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_COMPLETED_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WEEK_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DAY_WORKOUT_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SET_TABLE_NAME);
        this.onCreate(db);
    }


    public boolean isUserTableNotEmpty()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(USER_TABLE_NAME, // a. table
                null, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {
           return true;
        }
        cursor.close();
        return false;
    }

    public boolean isWorkoutCompletedForToday(String today)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DAY_USER_NAME, // a. table
                null, // b. column names
                COL_DAY_DATE + " = ? ", // c. selections
                new String[]{today}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        boolean bool=false;
        if (cursor.moveToFirst()) {

            long workoutCompletedId = getIdOfWorkoutCompleted(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_COMPLETED)));
            if(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))==workoutCompletedId)
                bool=true;
        }
        cursor.close();
        return bool;
    }

    public Workout getWorkoutCompleted(String id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(WORK_COMPLETED_NAME, // a. table
                null, // b. column names
                COL_WORKOUT_COMPLETED_ID + " = ? ", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        Workout workout = new Workout();
        if (cursor.moveToFirst()) {
            String workout_id = String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_COMPLETED_ID)));
            workout.setExercises(getExerciseByWorkoutCompletedId(workout_id));
            cursor.close();
            return workout;

        }
        cursor.close();
        return null;
    }

    public long getIdOfWorkoutCompleted(long id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(WORK_COMPLETED_NAME, // a. table
                null, // b. column names
                COL_WORKOUT_COMPLETED_ID + " = ? ", // c. selections
                new String[]{String.valueOf(id)}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor.moveToFirst()) {
            long workout_id = cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_COMPLETED_ID_OF_WORKOUT));
            cursor.close();
            return workout_id;

        }
        cursor.close();
        return -1;
    }

    private List<Exercise> getExerciseByWorkoutCompletedId(String workout_id) {
            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.query(EXERCISE_COMPLETED_NAME, // a. table
                    null, // b. column names
                    COL_WORKOUT_COMPLETED_ID + " = ? ", // c. selections
                    new String[]{workout_id}, // d. selections args
                    null, // e. group by
                    null, // f. having
                    null, // g. order by
                    null); // h. limit

            List<Exercise> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast())
                {
                    String type = cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_EXERCISE_NAME));
                    if(type.equalsIgnoreCase(DatabaseTableNames.SINGLE))
                    {
                        SingleExercise single = new SingleExercise(0,0);

                        single.setName(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_COMPLETED_ID))));
                        single.setSets(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_SETS)));
                        single.setReps(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_REPS_ONE)));

                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        single.setSetsList(getSetsListsByExerciseID(single.getId()));

                        list.add(single);

                    }
                    if(type.equalsIgnoreCase(DatabaseTableNames.SUPER))
                    {
                        SuperSet single = new SuperSet();
                        single.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        single.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_COMPLETED_ID))));
                        single.setSets(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_SETS)));
                        single.setRepsForFirst(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_REPS_ONE)));
                        single.setRepsForFirst(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_REPS_TWO)));
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        single.setSetsList(getSetsListsByExerciseID(single.getId()));
                        list.add(single);
                    }
                    if(type.equalsIgnoreCase(DatabaseTableNames.TRIPLE))
                    {
                        TripleSet single = new TripleSet();
                        single.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        single.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        single.setNameThree(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_COMPLETED_ID))));
                        single.setSets(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_SETS)));
                        single.setRepsFirstExercise(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_REPS_ONE)));
                        single.setRepsSecondExercise(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_REPS_TWO)));
                        single.setRepsThirdExercise(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_REPS_THREE)));
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        single.setSetsList(getSetsListsByExerciseID(single.getId()));
                        list.add(single);
                    }





                    cursor.moveToNext();
                }
                cursor.close();
                return list;

            }
            cursor.close();
            return null;
    }

    private List<Sets> getSetsListsByExerciseID(String id) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(SET_TABLE_NAME, // a. table
                null, // b. column names
                COL_EXERCISE_COMPLETED_ID + " = ? ", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Sets> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
           while(!cursor.isAfterLast())
           {
               Sets set = new Sets();
               set.setExerciseId(id);
               set.setType(cursor.getString(cursor.getColumnIndex(COL_SET_TYPE)));
               if(set.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE))
               {
                   set.setRepOne(cursor.getInt(cursor.getColumnIndex(COL_SET_REPS_ONE)));
                   set.setWeightOne(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COl_SET_WEIGHT_ONE))));
               }
               if(set.getType().equalsIgnoreCase(DatabaseTableNames.SUPER))
               {
                   set.setRepOne(cursor.getInt(cursor.getColumnIndex(COL_SET_REPS_ONE)));
                   set.setWeightOne(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COl_SET_WEIGHT_ONE))));
                   set.setRepTwo(cursor.getInt(cursor.getColumnIndex(COL_SET_REPS_TWO)));
                   set.setWeightTwo(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_SET_WEIGHT_TWO))));
               }
               if(set.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE))
               {
                   set.setRepOne(cursor.getInt(cursor.getColumnIndex(COL_SET_REPS_ONE)));
                   set.setWeightOne(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COl_SET_WEIGHT_ONE))));
                   set.setRepTwo(cursor.getInt(cursor.getColumnIndex(COL_SET_REPS_TWO)));
                   set.setWeightTwo(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_SET_WEIGHT_TWO))));
                   set.setRepThree(cursor.getInt(cursor.getColumnIndex(COL_SET_REPS_THREE)));
                   set.setWeightThree(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COL_SET_WEIGHT_THREE))));
               }
               list.add(set);
               cursor.moveToNext();
           }
            return list;

        }
        cursor.close();
        return null;

    }

    public long insertUserInformation(String id, String name, String email, float weight, float desiredWeight, int age)
    {
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, name);
        values.put(COL_USER_EMAIL, email);
        values.put(COL_USER_CURRENT_WEIGHT, weight);
        values.put(COL_USER_DESIRED_WEGIHT, desiredWeight);
        values.put(COL_USER_AGE, age);


        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(USER_TABLE_NAME, USER_COLUMNS, values);
        db.close();
        return returnId;
    }

    public long insertUserDay(String dayDate, String workoutId, String cardioId)
    {
        ContentValues values = new ContentValues();
        values.put(COL_DAY_DATE, dayDate);
        values.put(COL_WORKOUT_ID, workoutId);
        values.put(COL_CARDIO_ID, cardioId);
        values.put(COL_WEIGHT, 0.0);


        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(DAY_USER_NAME, COL_DAY_DATE+" "+COL_WORKOUT_ID+" "+COL_CARDIO_ID+" "+COL_WEIGHT, values);
        db.close();
        return returnId;
    }

    public Workout getWorkoutForToday(String today)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DAY_USER_NAME, // a. table
                null, // b. column names
                COL_DAY_DATE + " = ? ", // c. selections
                new String[]{today}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        Workout workout = new Workout();
        if (cursor.moveToFirst()) {
            String workout_id = String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID)));
            workout = getWorkoutById(workout_id);
            cursor.close();
            return workout;

        }
            cursor.close();
            return null;
    }

    public String getDayIdByDate(String today)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DAY_USER_NAME, // a. table
                null, // b. column names
                COL_DAY_DATE + " = ? ", // c. selections
                new String[]{today}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor.moveToFirst()) {
            String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_DAY_ID)));
            cursor.close();
            return id;

        }
        cursor.close();
        return null;
    }

    public int getWaterIntakeForToday(String today)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DAY_USER_NAME, // a. table
                null, // b. column names
                COL_DAY_DATE + " = ? ", // c. selections
                new String[]{today}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor.moveToFirst()) {
            int water = cursor.getInt(cursor.getColumnIndex(COL_GLASSES_OF_WATER));
            cursor.close();
            return water;

        }
        cursor.close();
        return 0;
    }

    public List<Routines> getRoutines()
    {
        SQLiteDatabase db = getReadableDatabase();
        List<Routines> list = new ArrayList<>();

        Cursor cursor = db.query(ROUTINES_TABLE_NAME, // a. table
                null, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor.moveToFirst()) {

            Routines routines;

            while(!cursor.isAfterLast())
            {
                routines = new Routines();
                routines.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_ROUTINE_ID))));
                System.out.println("This is the id "+routines.getId());
                routines.setName(cursor.getString(cursor.getColumnIndex(COL_ROUTINE_NAME)));
                cursor.moveToNext();
                list.add(routines);
            }

            cursor.close();
            return list;


        }
            cursor.close();
            return null;
    }

    public Routines getRoutineById(String id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(ROUTINES_TABLE_NAME, // a. table
                null, // b. column names
                COL_ROUTINE_ID+" = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        Routines routines = new Routines();

        if (cursor.moveToFirst()) {
                routines = new Routines();
                routines.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_ROUTINE_ID))));
                routines.setName(cursor.getString(cursor.getColumnIndex(COL_ROUTINE_NAME)));


                List<Week> listWeek = new ArrayList<>();
                listWeek = getWeekById(routines.getId());
                routines.setWeeks(listWeek);
                return routines;
        }


        cursor.close();
        return null;
    }


    public HashMap<String,Day> getDayById(String id)
    {
        HashMap<String, Day> mHashMap = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(DAY_WORKOUT_NAME, // a. table
                null, // b. column names
                COL_DAY_WEEK_ID +" = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {
            int index = 0;
            while(!cursor.isAfterLast() && index<7)
            {

                Day day = new Day();
                day.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_DAY_WORKOUT_ID))));
                day.setWeekId(id);
                day.setWorkout(getWorkoutById(day.getId()));
                mHashMap.put(DatabaseTableNames.LIST_OF_DAYS[index],day);
                index++;
                cursor.moveToNext();


            }
            return mHashMap;
        }


        cursor.close();
        return null;


    }

    public List<Week> getWeekById(String id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(WEEK_NAME, // a. table
                null, // b. column names
                COL_ROUTINE_ID +" = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {

            List<Week> listWeek = new ArrayList<>();
            while(!cursor.isAfterLast())
            {

                Week week = new Week();
                week.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_ROUTINE_ID))));
                HashMap<String,Day> mHashMap = new HashMap<>();
                mHashMap = getDayById(week.getId());
                week.setDayHashMap(mHashMap);
                week.setRoutineId(id);
                listWeek.add(week);
                cursor.moveToNext();
            }
            return listWeek;
        }


        cursor.close();
        return null;
    }

    public long insertWorkoutCompleted(Workout workout)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();


        values.put(COL_WORKOUT_COMPLETED_ID_OF_WORKOUT,workout.getId());
        long workoutId =  db.insert(WORK_COMPLETED_NAME,null,values);

        for (Exercise exercise: workout.getExercises()) {
            insertExerciseCompleted(exercise,workoutId);
        }
        db.close();
        return workoutId;
    }

    public long insertSetCompleted(String exerciseId, String type, int repsOne, int repsTwo, int repsThree, String weightOne, String weightTwo, String weightThree)
    {
        ContentValues values = new ContentValues();
        values.put(COL_EXERCISE_COMPLETED_ID, exerciseId);
        values.put(COL_SET_TYPE, type);
        values.put(COL_SET_REPS_ONE, repsOne);
        values.put(COL_SET_REPS_TWO, repsTwo);
        values.put(COL_SET_REPS_THREE, repsThree);
        values.put(COl_SET_WEIGHT_ONE, weightOne);
        values.put(COL_SET_WEIGHT_TWO, weightTwo);
        values.put(COL_SET_WEIGHT_THREE, weightThree);


        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(SET_TABLE_NAME, null, values);
        return returnId;
    }

    public void updateUserDayWorkoutCompleted(String currentDay, Workout workout)
    {
        String id = getDayIdByDate(currentDay);
        long workoutCompletedId = insertWorkoutCompleted(workout);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_WORKOUT_COMPLETED,workoutCompletedId);

        System.out.println();

        db.update(DAY_USER_NAME,values,COL_DAY_ID+"= "+id,null);
        db.close();

    }

    public void updateWaterIntakeForToday(String currentDay, int water)
    {
        String id = getDayIdByDate(currentDay);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_GLASSES_OF_WATER,water);

        System.out.println();

        db.update(DAY_USER_NAME,values,COL_DAY_ID+"= "+id,null);
        db.close();

    }

    public long insertExerciseCompleted(Exercise exercise, long id)
    {
        String type = null;
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(COL_WORKOUT_COMPLETED_ID, id);
        if(exercise instanceof SingleExercise) {
            type = DatabaseTableNames.SINGLE;
            values.put(COL_EXERCISE_ONE,exercise.getName());
            values.put(COL_EXERCISE_REPS_ONE,((SingleExercise) exercise).getReps());
            values.put(COL_EXERCISE_SETS,((SingleExercise) exercise).getSets());

            for (Sets set: exercise.getSetsList()) {
                insertSetCompleted(exercise.getId(),type,set.getRepOne(),0,0,String.valueOf(set.getWeightOne()),null,null);
            }
        }
        if(exercise instanceof SuperSet) {
            type = DatabaseTableNames.SUPER;
            values.put(COL_EXERCISE_ONE,((SuperSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((SuperSet) exercise).getNameTwo());
            values.put(COL_EXERCISE_REPS_ONE,((SuperSet) exercise).getRepsForFirst());
            values.put(COL_EXERCISE_REPS_TWO,((SuperSet) exercise).getRepsForSecond());
            values.put(COL_EXERCISE_SETS,((SuperSet) exercise).getSets());
            for (Sets set: exercise.getSetsList()) {
                insertSetCompleted(exercise.getId(),type,set.getRepOne(),set.getRepTwo(),0,String.valueOf(set.getWeightOne()),String.valueOf(set.getWeightTwo()),null);
            }
        }
        if(exercise instanceof TripleSet) {
            type = DatabaseTableNames.TRIPLE;
            values.put(COL_EXERCISE_ONE,((TripleSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((TripleSet) exercise).getNameTwo());
            values.put(COL_EXERCISE_THREE,((TripleSet) exercise).getNameThree());
            values.put(COL_EXERCISE_REPS_ONE,((TripleSet) exercise).getRepsFirstExercise());
            values.put(COL_EXERCISE_REPS_TWO,((TripleSet) exercise).getRepsSecondExercise());
            values.put(COL_EXERCISE_REPS_THREE,((TripleSet) exercise).getRepsThirdExercise());
            for (Sets set: exercise.getSetsList()) {
                insertSetCompleted(exercise.getId(),type,set.getRepOne(),set.getRepTwo(),set.getRepThree(),String.valueOf(set.getWeightOne()),String.valueOf(set.getWeightTwo()),String.valueOf(set.getWeightThree()));
            }
        }
        values.put(COL_TYPE_OF_EXERCISE_NAME,type);
        long exerciseId = db.insert(EXERCISE_COMPLETED_NAME,null,values);
        db.close();
        return exerciseId;
    }


    public Workout getWorkoutById(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Workout workout = new Workout();

        Cursor cursor = db.query(WORKOUT_TABLE_NAME, // a. table
                null, // b. column names
                COL_WORKOUT_ID + " = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast())
            {
                workout.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                workout.setNameOfWorkout(cursor.getString(cursor.getColumnIndex(COL_WORKOUT_NAME)));
                workout.setExercises(getExercisesById(workout.getId()));
                workout.setDayId(id);

                cursor.moveToNext();
            }
            return workout;
        }
        cursor.close();
        return null;
    }

    public void updateListOfExercises(List<Exercise> exercises)
    {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Exercise exercise: exercises) {
            if(exercise instanceof SingleExercise) {
                values.put(COL_EXERCISE_ONE,exercise.getName());
                values.put(COL_REPS_ONE,((SingleExercise) exercise).getReps());
                values.put(COL_SETS,((SingleExercise) exercise).getSets());
            }
            if(exercise instanceof SuperSet) {
                values.put(COL_EXERCISE_ONE,((SuperSet) exercise).getNameOne());
                values.put(COL_EXERCISE_TWO,((SuperSet) exercise).getNameTwo());
                values.put(COL_REPS_ONE,((SuperSet) exercise).getRepsForFirst());
                values.put(COL_REPS_TWO,((SuperSet) exercise).getRepsForSecond());
                values.put(COL_SETS,((SuperSet) exercise).getSets());
            }
            if(exercise instanceof TripleSet) {
                values.put(COL_EXERCISE_ONE,((TripleSet) exercise).getNameOne());
                values.put(COL_EXERCISE_TWO,((TripleSet) exercise).getNameTwo());
                values.put(COL_EXERCISE_THREE,((TripleSet) exercise).getNameThree());
                values.put(COL_REPS_ONE,((TripleSet) exercise).getRepsFirstExercise());
                values.put(COL_REPS_TWO,((TripleSet) exercise).getRepsSecondExercise());
                values.put(COL_REPS_THREE,((TripleSet) exercise).getRepsThirdExercise());
            }

            db.update(EXERCISE_NAME,values,COL_EXERCISE_ID+"= "+exercise.getId(),null);
        }


        db.close();
    }

    public List<Exercise> getExercisesById(String id)
    {

        SQLiteDatabase db = getReadableDatabase();
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = db.query(EXERCISE_NAME, // a. table
                null, // b. column names
                COL_WORKOUT_ID + " = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast())
            {

                Exercise exercise = new Exercise();
                exercise.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                switch (cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_EXERCISE_NAME)))
                {
                    case ((String)DatabaseTableNames.SINGLE):
                        SingleExercise single = new SingleExercise(0,0);
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        single.setName(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        single.setReps(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        single.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        exercise = single;
                        break;

                    case ((String) DatabaseTableNames.SUPER):
                        SuperSet superSet = new SuperSet();
                        superSet.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        superSet.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        superSet.setRepsForFirst(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        superSet.setRepsForSecond(cursor.getInt(cursor.getColumnIndex(COL_REPS_TWO)));
                        superSet.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        exercise = superSet;
                        break;

                    case ((String)DatabaseTableNames.TRIPLE):
                        TripleSet triple = new TripleSet();
                        triple.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        triple.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        triple.setNameThree(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_THREE)));
                        triple.setRepsFirstExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        triple.setRepsSecondExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_TWO)));
                        triple.setRepsThirdExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_THREE)));
                        triple.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        exercise = triple;
                        break;
                }
                cursor.moveToNext();
                exercise.setWorkoutID(id);
                exercises.add(exercise);

            }
            return exercises;
        }


        cursor.close();
        return null;
    }

    public Routines insertRoutine(Routines routines)
    {
        List<Week> weekList = routines.getWeeks();
        ContentValues values = new ContentValues();
        values.put(COL_ROUTINE_NAME, routines.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        long routineId = db.insert(ROUTINES_TABLE_NAME, null, values);
        routines.setId(String.valueOf(routineId));
        long week_id;

        for (Week week: weekList) {

            week_id = insertWeek(week,routineId);
            week.setId(String.valueOf(week_id));
        }


        db.close();

        return routines;
    }

    public long insertWeek(Week week, long id)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(COL_ROUTINE_ID,String.valueOf(id));
        long weekId = db.insert(WEEK_NAME,null,values);
        long id_day;
        HashMap<String, Day> hasmap = week.getDayHashMap();
        List<String> listOfDays = new ArrayList<>();
        listOfDays.add("Monday");
        listOfDays.add("Tuesday");
        listOfDays.add("Wednesday");
        listOfDays.add("Thrusday");
        listOfDays.add("Friday");
        listOfDays.add("Saturday");
        listOfDays.add("Sunday");
        for (String day: listOfDays) {
            id_day = insertDay(hasmap.get(day),weekId,day);
            hasmap.get(day).setId(String.valueOf(id_day));
            System.out.println(day);
        }
        return weekId;
    }

    public long insertDay(Day day, long id, String currentDay)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(COL_DAY_WORKOUT_DAY_NAME, currentDay);
        values.put(COL_DAY_WEEK_ID,id);
        long dayId =  db.insert(DAY_WORKOUT_NAME,null,values);
        insertWorkout(day.getWorkout(),dayId);

        return dayId;
    }


    public long insertWorkout(Workout workout, long id)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();


        values.put(COL_WORKOUT_NAME, workout.getNameOfWorkout());
        values.put(COL_DAY_ID,id);
        long workoutId =  db.insert(WORKOUT_TABLE_NAME,null,values);

        for (Exercise exercise: workout.getExercises()) {
            insertExercise(exercise,workoutId);
        }

        return workoutId;
    }



    public long insertExercise(Exercise exercise, long id)
    {
        String type = null;
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(COL_WORKOUT_ID, id);
        if(exercise instanceof SingleExercise) {
            type = DatabaseTableNames.SINGLE;
            values.put(COL_EXERCISE_ONE,exercise.getName());
            values.put(COL_REPS_ONE,((SingleExercise) exercise).getReps());
            values.put(COL_SETS,((SingleExercise) exercise).getSets());
        }
        if(exercise instanceof SuperSet) {
            type = DatabaseTableNames.SUPER;
            values.put(COL_EXERCISE_ONE,((SuperSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((SuperSet) exercise).getNameTwo());
            values.put(COL_REPS_ONE,((SuperSet) exercise).getRepsForFirst());
            values.put(COL_REPS_TWO,((SuperSet) exercise).getRepsForSecond());
            values.put(COL_SETS,((SuperSet) exercise).getSets());
        }
        if(exercise instanceof TripleSet) {
            type = DatabaseTableNames.TRIPLE;
            values.put(COL_EXERCISE_ONE,((TripleSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((TripleSet) exercise).getNameTwo());
            values.put(COL_EXERCISE_THREE,((TripleSet) exercise).getNameThree());
            values.put(COL_REPS_ONE,((TripleSet) exercise).getRepsFirstExercise());
            values.put(COL_REPS_TWO,((TripleSet) exercise).getRepsSecondExercise());
            values.put(COL_REPS_THREE,((TripleSet) exercise).getRepsThirdExercise());
        }
        values.put(COL_TYPE_OF_EXERCISE_NAME,type);
        long exerciseId = db.insert(EXERCISE_NAME,null,values);

        return exerciseId;
    }

}