package com.example.ivonneortega.myfitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.ivonneortega.myfitnessapp.Data.Challenges;
import com.example.ivonneortega.myfitnessapp.Data.Day;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Friend;
import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.Data.Sets;
import com.example.ivonneortega.myfitnessapp.Data.SingleExercise;
import com.example.ivonneortega.myfitnessapp.Data.SuperSet;
import com.example.ivonneortega.myfitnessapp.Data.TripleSet;
import com.example.ivonneortega.myfitnessapp.Data.User;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.Data.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
    public static final String FRIEND_TABLE_NAME = "friend_table_name";
    public static final String CHALLENGE_TABLE_NAME = "challenge_table_name";
    public static final String WORKOUT_CHALLENGE_TABLE_NAME = "workout_challenge_table_name";
    public static final String EXERCISES_CHALLENGE_TABLE_NAME = "exercises_challenge_table_name";
    public static final String WORK_CHALLENGE_COMPLETED_NAME = "WORKOUT_CHALLENGE_COMPLETED_TABLE";
    public static final String EXERCISE_CHALLENGE_COMPLETED_NAME = "EXERCISE_CHALLENGE_COMPLETED";
    public static final String SET_CHALLENGE_TABLE_NAME = "set_challenge_table_name";

    public static final String COL_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "user_email";
    public static final String COL_USER_CURRENT_WEIGHT = "current_weight";
    public static final String COL_USER_DESIRED_WEGIHT = "desired_weight";
    public static final String COL_USER_AGE = "user_age";

    public static final String COL_ROUTINE_ID = "routine_id";
    public static final String COL_ROUTINE_NAME = "routine_name";
    public static final String COL_USER_ID = "user_id";

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


    public static final String COL_FRIENDTABLE_ID = "friend_table_id";
    public static final String COL_FRIEND_ID = "friend_id";
    public static final String COL_FRIEND_NAME = "friend_name";
    public static final String COL_FRIEND_EMAIL = "friend_email";

    public static final String COL_CHALLENGE_ID = "challenge_id";
    public static final String COL_CHALLENGE_FRIEND_ID = "friend_id";
    public static final String COL_CHALLENGE_USER_ID = "user_id";
    public static final String COL_CHALLENGE_WORKOUT_FRIEND_ID = "workout_friend_id";
    public static final String COL_CHALLENGE_WORKOUT_USER_ID = "workout_user_id";
    public static final String COL_CHALLENGE_STATUS = "challenge_status";
    public static final String COL_CHALLENGE_ID_CREATOR = "id_creator";
    public static final String COL_CHALLENGE_TITLE = "challenge_title";
    public static final String COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE = "unique_key";


    public static final String COL_WORKOUT_CHALLENGE_ID = "workout_challenge_id";
    public static final String COL_WORKOUT_CHALLENGE_WHO = "workout_challenge_who";
    //COL_CHALLENGE_ID
    public static final String COL_WORKOUT_CHALLENGE_NAME_OF_WORKOUT = "name_of_workout";




    public static final String COL_CARDIO_ID = "cardio_id";



    public static final String USER_COLUMNS = COL_ID+", "+COL_USER_NAME+", "+COL_USER_EMAIL+", "+COL_USER_AGE+", "+COL_USER_CURRENT_WEIGHT+", "+COL_USER_DESIRED_WEGIHT;

    private static final String CREATE_WORKOUT_CHALLENGE_TABLE =
            "CREATE TABLE " + WORKOUT_CHALLENGE_TABLE_NAME +
                    "(" +
                    COL_WORKOUT_CHALLENGE_ID + " INTEGER PRIMARY KEY, " +
                    COL_CHALLENGE_ID + " INTEGER, " +
                    COL_WORKOUT_CHALLENGE_NAME_OF_WORKOUT + " TEXT,"+
                    COL_WORKOUT_CHALLENGE_WHO + " TEXT"+")";


    private static final String CREATE_CHALLENGE_TABLE =
            "CREATE TABLE " + CHALLENGE_TABLE_NAME +
                    "(" +
                    COL_CHALLENGE_ID + " INTEGER PRIMARY KEY, " +
                    COL_CHALLENGE_FRIEND_ID + " TEXT, " +
                    COL_CHALLENGE_USER_ID + " TEXT, " +
                    COL_CHALLENGE_STATUS + " TEXT, " +
                    COL_CHALLENGE_TITLE+ " TEXT, "+
                    COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE + " TEXT, "+
                    COL_CHALLENGE_ID_CREATOR + " TEXT, "+
                    COL_CHALLENGE_WORKOUT_FRIEND_ID + " TEXT, " +
                    COL_CHALLENGE_WORKOUT_USER_ID + " TEXT" +")";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_USER_NAME + " TEXT, " +
                    COL_USER_EMAIL + " TEXT, " +
                    COL_USER_AGE + " INTEGER, " +
                    COL_USER_CURRENT_WEIGHT + " TEXT, " +
                    COL_USER_DESIRED_WEGIHT + " TEXT" + ")";

    private static final String CREATE_FRIEND_TABLE =
            "CREATE TABLE " + FRIEND_TABLE_NAME +
                    "(" +
                    COL_FRIENDTABLE_ID + " INTEGER PRIMARY KEY, " +
                    COL_FRIEND_EMAIL + " TEXT, " +
                    COL_FRIEND_NAME + " TEXT, " +
                    COL_FRIEND_ID + " TEXT" + ")";

    public static final String[] ROUTINES_COLUMNS = {COL_ID,COL_USER_NAME,COL_USER_EMAIL,COL_USER_AGE,COL_USER_CURRENT_WEIGHT,COL_USER_DESIRED_WEGIHT};

    private static final String CREATE_WORKOUT_TABLE =
            "CREATE TABLE " + WORKOUT_TABLE_NAME +
                    "(" +
                    COL_WORKOUT_ID + " INTEGER PRIMARY KEY, " +
                    COL_DAY_ID + " INTEGER," +
                    COL_WORKOUT_CHALLENGE_ID + " INTEGER, "+
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
                    COL_USER_ID + " TEXT," +
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

    private static final String CREATE_EXERCISE_CHALLENGE_TABLE =
            "CREATE TABLE " + EXERCISES_CHALLENGE_TABLE_NAME +
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

    private static final String CREATE_WORKOUT_CHALLENGE_COMPLETED_TABLE =
            "CREATE TABLE " + WORK_CHALLENGE_COMPLETED_NAME +
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

    private static final String CREATE_SET_CHALLENGE_TABLE =
            "CREATE TABLE " + SET_CHALLENGE_TABLE_NAME +
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

    private static final String CREATE_EXERCISE_CHALLENGE_COMPLETED_TABLE =
            "CREATE TABLE " + EXERCISE_CHALLENGE_COMPLETED_NAME +
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
        db.execSQL(CREATE_FRIEND_TABLE);
        db.execSQL(CREATE_CHALLENGE_TABLE);
        db.execSQL(CREATE_WORKOUT_CHALLENGE_TABLE);
        db.execSQL(CREATE_WORKOUT_CHALLENGE_COMPLETED_TABLE);
        db.execSQL(CREATE_EXERCISE_CHALLENGE_COMPLETED_TABLE);
        db.execSQL(CREATE_SET_CHALLENGE_TABLE);
        db.execSQL(CREATE_EXERCISE_CHALLENGE_TABLE);

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
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHALLENGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_CHALLENGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_CHALLENGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORK_CHALLENGE_COMPLETED_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_CHALLENGE_COMPLETED_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SET_CHALLENGE_TABLE_NAME);
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

        today = today.replaceAll("\\s","");
        today = today.replaceAll(":","1");

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
                        single.setType(type);
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
                        single.setType(type);
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
                        single.setType(type);
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

    public long insertFriend(Friend friend)
    {
        ContentValues values = new ContentValues();
        values.put(COL_FRIEND_ID, friend.getFriendId());
        values.put(COL_FRIEND_EMAIL, friend.getEmail());
        values.put(COL_FRIEND_NAME, friend.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(FRIEND_TABLE_NAME, null, values);
        db.close();
        return returnId;
    }

    public long insertChallengeCreatedByUser(Challenges challenge)
    {
        ContentValues values = new ContentValues();
        values.put(COL_CHALLENGE_FRIEND_ID, challenge.getFriendId());
        values.put(COL_CHALLENGE_USER_ID, challenge.getUserId());
        if(challenge.getWorkoutFriend()!=null)
            values.put(COL_CHALLENGE_WORKOUT_FRIEND_ID, challenge.getWorkoutFriend().getId());
        values.put(COL_CHALLENGE_WORKOUT_USER_ID, challenge.getWorkoutUser().getId());
        values.put(COL_CHALLENGE_STATUS,DatabaseTableNames.PENDING);
        values.put(COL_CHALLENGE_ID_CREATOR,challenge.getUserId());
        values.put(COL_CHALLENGE_TITLE,challenge.getTitle());
        values.put(COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE,challenge.getUniqueKey());

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(CHALLENGE_TABLE_NAME, null, values);

        if(challenge.getWorkoutFriend()!=null)
            insertWorkoutInChallenge(challenge.getWorkoutFriend(),DatabaseTableNames.CHALLENGE_FRIEND,String.valueOf(returnId));
        insertWorkoutInChallenge(challenge.getWorkoutUser(),DatabaseTableNames.CHALLENGE_USER,String.valueOf(returnId));


        db.close();
        return returnId;
    }

    public long insertChallengeFromFirebase(Challenges challenge)
    {
        ContentValues values = new ContentValues();
        values.put(COL_CHALLENGE_FRIEND_ID, challenge.getFriendId());
        values.put(COL_CHALLENGE_USER_ID, challenge.getUserId());
        if(challenge.getWorkoutFriend()!=null)
            values.put(COL_CHALLENGE_WORKOUT_FRIEND_ID, challenge.getWorkoutFriend().getId());
        values.put(COL_CHALLENGE_WORKOUT_USER_ID, challenge.getWorkoutUser().getId());
        values.put(COL_CHALLENGE_ID_CREATOR,challenge.getCreatorId());
        values.put(COL_CHALLENGE_TITLE,challenge.getTitle());
        values.put(COL_CHALLENGE_STATUS,challenge.getStatus());
        values.put(COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE,challenge.getUniqueKey());


        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(CHALLENGE_TABLE_NAME, null, values);

        if(challenge.getWorkoutFriend()!=null)
            insertWorkoutInChallenge(challenge.getWorkoutFriend(),DatabaseTableNames.CHALLENGE_FRIEND,String.valueOf(returnId));
        insertWorkoutInChallenge(challenge.getWorkoutUser(),DatabaseTableNames.CHALLENGE_USER,String.valueOf(returnId));


        db.close();
        return returnId;
    }

    public long insertChallengeCreatedByFriend(Challenges challenge)
    {
        ContentValues values = new ContentValues();
        values.put(COL_CHALLENGE_FRIEND_ID, challenge.getFriendId());
        values.put(COL_CHALLENGE_USER_ID, challenge.getUserId());
        if(challenge.getWorkoutFriend()!=null)
            values.put(COL_CHALLENGE_WORKOUT_FRIEND_ID, challenge.getWorkoutFriend().getId());
        values.put(COL_CHALLENGE_WORKOUT_USER_ID, challenge.getWorkoutUser().getId());
        values.put(COL_CHALLENGE_STATUS,DatabaseTableNames.PENDING);
        values.put(COL_CHALLENGE_ID_CREATOR,challenge.getFriendId());

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(CHALLENGE_TABLE_NAME, null, values);

        if(challenge.getWorkoutFriend()!=null)
            insertWorkoutInChallenge(challenge.getWorkoutFriend(),DatabaseTableNames.CHALLENGE_FRIEND,String.valueOf(returnId));
        insertWorkoutInChallenge(challenge.getWorkoutUser(),DatabaseTableNames.CHALLENGE_USER,String.valueOf(returnId));


        db.close();
        return returnId;
    }

    public long insertWorkoutInChallenge(Workout workout, String who, String challengeId)
    {
        ContentValues values = new ContentValues();
        values.put(COL_WORKOUT_CHALLENGE_WHO, who);
        values.put(COL_CHALLENGE_ID, challengeId);
        values.put(COL_WORKOUT_CHALLENGE_NAME_OF_WORKOUT,workout.getNameOfWorkout());

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(WORKOUT_CHALLENGE_TABLE_NAME, null, values);

//        long workoutId =  db.insert(WORKOUT_TABLE_NAME,null,values);

        for (Exercise exercise: workout.getExercises()) {
            insertExerciseFromChallenge(exercise,returnId);
        }
        db.close();
        return returnId;
    }

    public List<Friend> getAllFriends()
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(FRIEND_TABLE_NAME, // a. table
                null, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Friend> friendList = new ArrayList<>();
        Friend friend;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast())
            {
                friend = new Friend();
                friend.setEmail(cursor.getString(cursor.getColumnIndex(COL_FRIEND_EMAIL)));
                friend.setFriendId(cursor.getString(cursor.getColumnIndex(COL_FRIEND_ID)));
                friend.setUserId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_FRIENDTABLE_ID))));
                friend.setName(cursor.getString(cursor.getColumnIndex(COL_FRIEND_NAME)));

                friendList.add(friend);

                cursor.moveToNext();
            }
            cursor.close();
            return friendList;

        }
        cursor.close();
        return friendList;
    }

    public List<Challenges> getAllChallenges()
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Challenges> challengesList = new ArrayList<>();
        Challenges challenge;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast())
            {
                challenge = new Challenges();
                challenge.setId(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID))));
                challenge.setFriendId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_FRIEND_ID)));
                challenge.setUserId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_USER_ID)));
                challenge.setWorkoutFriend(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_FRIEND));
                challenge.setWorkoutUser(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_USER));
                challenge.setTitle(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_TITLE)));
                challenge.setCreatorId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID_CREATOR)));
                challenge.setStatus(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_STATUS)));
                challenge.setUniqueKey(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE)));

                challengesList.add(challenge);

                cursor.moveToNext();
            }
            cursor.close();
            return challengesList;

        }
        cursor.close();
        return challengesList;
    }

    public List<Challenges> getAllPendingChallenges()
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_STATUS+" = ?", // c. selections
                new String[]{DatabaseTableNames.PENDING}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Challenges> challengesList = new ArrayList<>();
        Challenges challenge;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast())
            {
                challenge = new Challenges();
                challenge.setId(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID))));
                challenge.setFriendId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_FRIEND_ID)));
                challenge.setUserId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_USER_ID)));
                challenge.setWorkoutFriend(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_FRIEND));
                challenge.setWorkoutUser(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_USER));
                challenge.setTitle(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_TITLE)));
                challenge.setCreatorId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID_CREATOR)));
                challenge.setStatus(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_STATUS)));
                challenge.setUniqueKey(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE)));

                challengesList.add(challenge);

                cursor.moveToNext();
            }
            cursor.close();
            return challengesList;

        }
        cursor.close();
        return challengesList;
    }

    public List<Challenges> getAllAceptedChallenges()
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_STATUS+" = ?", // c. selections
                new String[]{DatabaseTableNames.ACCEPTED}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Challenges> challengesList = new ArrayList<>();
        Challenges challenge;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast())
            {
                challenge = new Challenges();
                challenge.setId(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID))));
                challenge.setFriendId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_FRIEND_ID)));
                challenge.setUserId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_USER_ID)));
                challenge.setWorkoutFriend(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_FRIEND));
                challenge.setWorkoutUser(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_USER));
                challenge.setTitle(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_TITLE)));
                challenge.setCreatorId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID_CREATOR)));
                challenge.setStatus(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_STATUS)));
                challenge.setUniqueKey(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE)));

                challengesList.add(challenge);

                cursor.moveToNext();
            }
            cursor.close();
            return challengesList;

        }
        cursor.close();
        return challengesList;
    }

    public List<Challenges> getChallengeByFriend(String friendId)
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_FRIEND_ID, // c. selections
                new String[]{friendId}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Challenges> challengesList = new ArrayList<>();
        Challenges challenge;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast())
            {
                challenge = new Challenges();
                challenge.setId(String.valueOf(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID))));
                challenge.setFriendId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_FRIEND_ID)));
                challenge.setUserId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_USER_ID)));
                challenge.setWorkoutFriend(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_FRIEND));
                challenge.setWorkoutUser(getWorkoutInChallenge(challenge.getId(),DatabaseTableNames.CHALLENGE_USER));
                challenge.setCreatorId(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_ID_CREATOR)));
                challenge.setStatus(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_STATUS)));
                challenge.setTitle(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_TITLE)));
                challenge.setUniqueKey(cursor.getString(cursor.getColumnIndex(COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE)));

                challengesList.add(challenge);

                cursor.moveToNext();
            }
            cursor.close();
            return challengesList;

        }
        cursor.close();
        return challengesList;
    }

    public long getChallengeId(String uniqueId)
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE+" = ?", // c. selections
                new String[]{uniqueId}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Challenges> challengesList = new ArrayList<>();
        Challenges challenge;
        if (cursor.moveToFirst()) {
            long id = cursor.getInt(cursor.getColumnIndex(COL_CHALLENGE_ID));
                cursor.close();
                return id;

        }


        cursor.close();
        return -1;
    }




    public Workout getWorkoutInChallenge(String challengeId, String who)
    {
        SQLiteDatabase db = getReadableDatabase();
        Workout workout = new Workout();

        Cursor cursor = db.query(WORKOUT_CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_ID + " = ?", // c. selections
                new String[]{challengeId}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast())
            {
                if(cursor.getString(cursor.getColumnIndex(COL_WORKOUT_CHALLENGE_WHO)).equalsIgnoreCase(who))
                {
                    workout.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_CHALLENGE_ID))));
                    workout.setNameOfWorkout(cursor.getString(cursor.getColumnIndex(COL_WORKOUT_CHALLENGE_NAME_OF_WORKOUT)));
                    workout.setExercises(getExercisesByIdInChallenge(workout.getId()));
                    workout.setDayId("-1");
                }
                cursor.moveToNext();
            }
            return workout;
        }
        cursor.close();
        return null;
    }

    public Friend getFriendByFriendId(String id)
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(FRIEND_TABLE_NAME, // a. table
                null, // b. column names
                COL_FRIEND_ID+" = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        Friend friend;
        if (cursor.moveToFirst()) {
                friend = new Friend();
                friend.setEmail(cursor.getString(cursor.getColumnIndex(COL_FRIEND_EMAIL)));
                friend.setFriendId(cursor.getString(cursor.getColumnIndex(COL_FRIEND_ID)));
                friend.setUserId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_FRIENDTABLE_ID))));
                friend.setName(cursor.getString(cursor.getColumnIndex(COL_FRIEND_NAME)));

            cursor.close();
            return friend;

        }
        cursor.close();
        return null;
    }

    public long insertUserInformation(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.getName());
        values.put(COL_USER_EMAIL, user.getEmail());
        values.put(COL_USER_CURRENT_WEIGHT, user.getWeight());
        values.put(COL_USER_DESIRED_WEGIHT, user.getDesiredWeight());


//        values.put(COL_USER_AGE, user.);

        SQLiteDatabase db = this.getWritableDatabase();
        long returnId = db.insert(USER_TABLE_NAME, USER_COLUMNS, values);
        if(user.getFriends()!=null && user.getFriends().size()>0)
        {
            for (Friend friend: user.getFriends()) {
                insertFriend(friend);
            }
        }
        if(user.getChallenges()!=null && user.getChallenges().size()>0)
        {
            for (Challenges challenges: user.getChallenges()) {
                insertChallengeFromFirebase(challenges);
            }
        }
        if(user.getWorkouts()!=null && user.getWorkouts().size()>0)
        {
            for (Workout workout: user.getWorkouts()) {
                insertWorkout(workout,-1);
            }
        }
        db.close();
        return returnId;
    }

    public long insertUserDay(String dayDate, String workoutId, String cardioId)
    {
        ContentValues values = new ContentValues();

        dayDate = dayDate.replaceAll("\\s","");
        dayDate = dayDate.replaceAll(":","1");

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

        today = today.replaceAll("\\s","");
        today = today.replaceAll(":","1");


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

    public boolean doesTodayExitInDatabase(String today)
    {
        SQLiteDatabase db = getReadableDatabase();

        today = today.replaceAll("\\s","");
        today = today.replaceAll(":","1");

        Cursor cursor = db.query(DAY_USER_NAME, // a. table
                null, // b. column names
                COL_DAY_DATE + " = ? ", // c. selections
                new String[]{today}, // d. selections args
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

    public boolean doesFriendExitsInDatabase(String friendId)
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(FRIEND_TABLE_NAME, // a. table
                null, // b. column names
                COL_FRIEND_ID + " = ? ", // c. selections
                new String[]{friendId}, // d. selections args
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

    public void updateWorkoutForToday(String today, String workoutId)
    {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_WORKOUT_ID,workoutId);

        String dayId = getDayIdByDate(today);



        db.update(DAY_USER_NAME,values,COL_DAY_ID+" = "+dayId,null);
        db.close();
    }

    public void updateStatusChallenge(String uniqueId, String status)
    {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_CHALLENGE_STATUS,status);

        long id = getChallengeId(uniqueId);

        db.update(CHALLENGE_TABLE_NAME,values,COL_CHALLENGE_ID+" = "+String.valueOf(id),null);
        db.close();
    }

    public void updateStatusInChallenge(String status, String id)
    {


        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_CHALLENGE_STATUS,status);


        db.update(CHALLENGE_TABLE_NAME,values,COL_CHALLENGE_ID+" = "+id,null);
        db.close();
    }

    public String getDayIdByDate(String today)
    {
        SQLiteDatabase db = getReadableDatabase();

        today = today.replaceAll("\\s","");
        today = today.replaceAll(":","1");

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

        today = today.replaceAll("\\s","");
        today = today.replaceAll(":","1");

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
                routines.setName(cursor.getString(cursor.getColumnIndex(COL_ROUTINE_NAME)));
                routines.setUserId(cursor.getString(cursor.getColumnIndex(COL_USER_ID)));
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
                routines.setUserId(cursor.getString(cursor.getColumnIndex(COL_USER_ID)));



                List<Week> listWeek = new ArrayList<>();
                listWeek = getWeekById(routines.getId());
                routines.setWeeks(listWeek);
                return routines;
        }


        cursor.close();
        return null;
    }

    public boolean doesRoutineExits(String id)
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


        if (cursor.moveToFirst()) {
            db.close();
            return true;
        }


        cursor.close();
        return false;
    }

    public boolean doesChallengeExits(String id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(CHALLENGE_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_UNIQUE_KEY_FROM_FIREBASE+" = ?", // c. selections
                new String[]{id}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
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
                week.setRoutineId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_ROUTINE_ID))));
                week.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WEEK_ID))));
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

    public List<Workout> getUserWorkouts()
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(WORKOUT_TABLE_NAME, // a. table
                null, // b. column names
                COL_DAY_ID +" = ?", // c. selections
                new String[]{"-1"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        List<Workout> listWorkout = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast())
            {
                Workout workout = new Workout();
                workout.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                workout.setDayId("-1");
                workout.setNameOfWorkout(cursor.getString(cursor.getColumnIndex(COL_WORKOUT_NAME)));
                workout.setExercises(getExercisesById(workout.getId()));

                listWorkout.add(workout);

                cursor.moveToNext();
            }
            return listWorkout;
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


        db.update(DAY_USER_NAME,values,COL_DAY_ID+"= "+id,null);
        db.close();

    }


    public void updateWorkout(Workout workout)
    {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_WORKOUT_NAME,workout.getNameOfWorkout());


        db.update(WORKOUT_TABLE_NAME,values,COL_WORKOUT_ID+"= "+workout.getId(),null);

        removeExercises(workout.getExercises());
        for (Exercise exercise: workout.getExercises()) {
            insertExercise(exercise,Long.parseLong(workout.getId()));
        }
        db.close();

    }

    public void removeAllUserDays()
    {
        SQLiteDatabase db = getWritableDatabase();
        long rowsDeleted = db.delete(DAY_USER_NAME, "1", null);
        db.close();
    }

    public boolean isRoutineUsedAsRoutine(String id)
    {
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.query(DAY_USER_NAME, // a. table
                null, // b. column names
                null, // c. selections
                null, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        Workout workout = new Workout();
        Day day = new Day();
        Week week = new Week();
        boolean isCurrentRoutine = false;
        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast())
            {
                week = getWeekById(id).get(0);
                day = getDayById(week.getId()).get("Monday");
                workout = getWorkoutByDayId(day.getId());

                if(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_DAY_WORKOUT_ID))).equalsIgnoreCase(workout.getId()))
                {
                    cursor.moveToLast();
                    isCurrentRoutine=true;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return isCurrentRoutine;
    }

    public long removeWorkoutFromDay(String workoutID)
    {
        ContentValues values = new ContentValues();
        values.put(COL_WORKOUT_ID,-1);
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.update(DAY_USER_NAME,values, COL_WORKOUT_ID + "= "+workoutID , null);
        db.close();
        return columnId;
    }

    public void removeRoutineFromUserDay()
    {
        ContentValues values = new ContentValues();
        values.put(COL_WORKOUT_ID,-1);
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.update(DAY_USER_NAME,values, null, null);
        db.close();
    }

    public void removeSetRoutine(String routineId)
    {
        List<Week> weekList = getWeekById(routineId);
        for (Week week : weekList) {
            HashMap<String, Day> mHashmap = getDayById(week.getId());
            for (String today: DatabaseTableNames.LIST_OF_DAYS) {
                Workout workout = getWorkoutByDayId(mHashmap.get(today).getId());
                removeWorkoutFromDay(workout.getId());
            }
        }
    }

    public void updateRoutineName(String id, String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ROUTINE_NAME,name);


        db.update(ROUTINES_TABLE_NAME,values,COL_ROUTINE_ID+"= "+id,null);

        db.close();
    }

    public void updateWaterIntakeForToday(String currentDay, int water)
    {
        if(!doesTodayExitInDatabase(currentDay))
            insertUserDay(currentDay,null,null);

        String id = getDayIdByDate(currentDay);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_GLASSES_OF_WATER,water);

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


    public Workout getWorkoutByDayId(String id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Workout workout = new Workout();

        Cursor cursor = db.query(WORKOUT_TABLE_NAME, // a. table
                null, // b. column names
                COL_DAY_ID + " = ?", // c. selections
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
    public List<Workout> getWorkoutsThatDontBelogToRoutine()
    {
        SQLiteDatabase db = getReadableDatabase();
        Workout workout = new Workout();

        Cursor cursor = db.query(WORKOUT_TABLE_NAME, // a. table
                null, // b. column names
                COL_DAY_ID + " = ?", // c. selections
                new String[]{"-1"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Workout> list = new ArrayList<>();
        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast())
            {
                workout = new Workout();
                workout.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                workout.setNameOfWorkout(cursor.getString(cursor.getColumnIndex(COL_WORKOUT_NAME)));
                workout.setExercises(getExercisesById(workout.getId()));
                workout.setDayId("-1");

                list.add(workout);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<Workout> getWorkoutsThatAreInAChallenge(String friendId)
    {
        SQLiteDatabase db = getReadableDatabase();
        Workout workout = new Workout();

        Cursor cursor = db.query(WORKOUT_TABLE_NAME, // a. table
                null, // b. column names
                COL_CHALLENGE_ID + " = ?", // c. selections
                new String[]{"-1"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        List<Workout> list = new ArrayList<>();
        if (cursor.moveToFirst()) {

            while(!cursor.isAfterLast())
            {
                workout = new Workout();
                workout.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                workout.setNameOfWorkout(cursor.getString(cursor.getColumnIndex(COL_WORKOUT_NAME)));
                workout.setExercises(getExercisesById(workout.getId()));
                workout.setDayId("-1");

                list.add(workout);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public long removeExercise(Exercise exercise)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(EXERCISE_NAME, COL_EXERCISE_ID + "= "+exercise.getId() , null);
        db.close();
        return columnId;
    }

    public long removeRoutine(String id)
    {
        removeWeekByRoutineId(id);
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(ROUTINES_TABLE_NAME, COL_ROUTINE_ID + "= "+id , null);
//        db.close();
        return columnId;
    }

    public long removeWeekById(String id)
    {

        List<Week> weekList = getWeekById(id);
        for (Week week: weekList) {
            removeDayByWeekId(week.getId());
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(WEEK_NAME, COL_WEEK_ID + "= "+id , null);
//        db.close();
        return columnId;
    }

    public long removeWeekByRoutineId(String id)
    {
        List<Week> weekList = getWeekById(id);
        for (Week week: weekList) {
            removeDayByWeekId(week.getId());
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(WEEK_NAME, COL_WEEK_ID + "= "+id , null);
//        db.close();
        return columnId;
    }

    public long removeDayByWeekId(String id)
    {
        HashMap<String, Day> day = getDayById(id);
        if(day!=null)
        {
            for (String today: DatabaseTableNames.LIST_OF_DAYS) {
                removeWorkoutByDayId(day.get(today).getId());
            }
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(DAY_WORKOUT_NAME, COL_DAY_WEEK_ID + "= "+id , null);
//        db.close();
        return columnId;
    }

    public long removeWorkoutByDayId(String id)
    {
        Workout workout = getWorkoutByDayId(id);
        if(workout!=null)
        {
            List<Exercise> exercises = workout.getExercises();
            removeExercises(exercises);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(WORKOUT_TABLE_NAME, COL_DAY_ID + "= "+id , null);
//        db.close();
        return columnId;
    }

    public void removeExercises(List<Exercise> exercises)
    {
        if(exercises!=null)
        {
            for (Exercise exercise: exercises) {
                long id = removeExercise(exercise);
            }
        }

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
                String theType = cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_EXERCISE_NAME));
                switch (theType)
                {
                    case ((String)DatabaseTableNames.SINGLE):
                        SingleExercise single = new SingleExercise(0,0);
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        single.setWorkoutID(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                        single.setName(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        single.setReps(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        single.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        single.setType(theType);
                        exercise = single;
                        exercise.setType(theType);
                        break;

                    case ((String) DatabaseTableNames.SUPER):
                        SuperSet superSet = new SuperSet();
                        superSet.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        superSet.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        superSet.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        superSet.setWorkoutID(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                        superSet.setRepsForFirst(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        superSet.setRepsForSecond(cursor.getInt(cursor.getColumnIndex(COL_REPS_TWO)));
                        superSet.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        superSet.setType(theType);
                        exercise = superSet;
                        exercise.setType(theType);
                        break;

                    case ((String)DatabaseTableNames.TRIPLE):
                        TripleSet triple = new TripleSet();
                        triple.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        triple.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        triple.setNameThree(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_THREE)));
                        triple.setRepsFirstExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        triple.setRepsSecondExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_TWO)));
                        triple.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        triple.setWorkoutID(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                        triple.setRepsThirdExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_THREE)));
                        triple.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        triple.setType(theType);
                        exercise = triple;
                        exercise.setType(theType);
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

    public List<Exercise> getExercisesByIdInChallenge(String id)
    {

        SQLiteDatabase db = getReadableDatabase();
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = db.query(EXERCISES_CHALLENGE_TABLE_NAME, // a. table
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
                String theType = cursor.getString(cursor.getColumnIndex(COL_TYPE_OF_EXERCISE_NAME));
                switch (theType)
                {
                    case ((String)DatabaseTableNames.SINGLE):
                        SingleExercise single = new SingleExercise(0,0);
                        single.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        single.setWorkoutID(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                        single.setName(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        single.setReps(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        single.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        single.setType(theType);
                        exercise = single;
                        exercise.setType(theType);
                        break;

                    case ((String) DatabaseTableNames.SUPER):
                        SuperSet superSet = new SuperSet();
                        superSet.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        superSet.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        superSet.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        superSet.setWorkoutID(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                        superSet.setRepsForFirst(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        superSet.setRepsForSecond(cursor.getInt(cursor.getColumnIndex(COL_REPS_TWO)));
                        superSet.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        superSet.setType(theType);
                        exercise = superSet;
                        exercise.setType(theType);
                        break;

                    case ((String)DatabaseTableNames.TRIPLE):
                        TripleSet triple = new TripleSet();
                        triple.setNameOne(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_ONE)));
                        triple.setNameTwo(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_TWO)));
                        triple.setNameThree(cursor.getString(cursor.getColumnIndex(COL_EXERCISE_THREE)));
                        triple.setRepsFirstExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_ONE)));
                        triple.setRepsSecondExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_TWO)));
                        triple.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_EXERCISE_ID))));
                        triple.setWorkoutID(String.valueOf(cursor.getInt(cursor.getColumnIndex(COL_WORKOUT_ID))));
                        triple.setRepsThirdExercise(cursor.getInt(cursor.getColumnIndex(COL_REPS_THREE)));
                        triple.setSets(cursor.getInt(cursor.getColumnIndex(COL_SETS)));
                        triple.setType(theType);
                        exercise = triple;
                        exercise.setType(theType);
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


        values.put(COL_USER_ID,routines.getUserId());
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
        values.put(COL_WORKOUT_CHALLENGE_ID,-1);
        long workoutId =  db.insert(WORKOUT_TABLE_NAME,null,values);
        workout.setId(String.valueOf(workoutId));

        for (Exercise exercise: workout.getExercises()) {
            insertExercise(exercise,workoutId);
        }

        return workoutId;
    }

    public long insertWorkoutFromChallenge(Workout workout, long id)
    {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();


        values.put(COL_WORKOUT_NAME, workout.getNameOfWorkout());
        values.put(COL_DAY_ID,-2);
        long workoutId =  db.insert(WORKOUT_TABLE_NAME,null,values);
        workout.setId(String.valueOf(workoutId));

        for (Exercise exercise: workout.getExercises()) {
            insertExercise(exercise,workoutId);
        }

        return workoutId;
    }


    public long deteleAllWorkouts()
    {

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(WORKOUT_TABLE_NAME, COL_DAY_ID + "= -1" , null);
        db.close();
        return columnId;
    }

    public long deleteOneWorkout(String id)
    {

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        long columnId =  db.delete(WORKOUT_TABLE_NAME, COL_WORKOUT_ID + " = " + id , null);
        db.close();
        return columnId;
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
            exercise.setType(type);
        }
        if(exercise instanceof SuperSet || exercise.getType().equalsIgnoreCase(DatabaseTableNames.SUPER)) {
            type = DatabaseTableNames.SUPER;
            values.put(COL_EXERCISE_ONE,((SuperSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((SuperSet) exercise).getNameTwo());
            values.put(COL_REPS_ONE,((SuperSet) exercise).getRepsForFirst());
            values.put(COL_REPS_TWO,((SuperSet) exercise).getRepsForSecond());
            values.put(COL_SETS,((SuperSet) exercise).getSets());
            exercise.setType(type);
        }
        if(exercise instanceof TripleSet || exercise.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE)) {
            type = DatabaseTableNames.TRIPLE;
            values.put(COL_EXERCISE_ONE,((TripleSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((TripleSet) exercise).getNameTwo());
            values.put(COL_EXERCISE_THREE,((TripleSet) exercise).getNameThree());
            values.put(COL_REPS_ONE,((TripleSet) exercise).getRepsFirstExercise());
            values.put(COL_REPS_TWO,((TripleSet) exercise).getRepsSecondExercise());
            values.put(COL_REPS_THREE,((TripleSet) exercise).getRepsThirdExercise());
            exercise.setType(type);
        }
        values.put(COL_TYPE_OF_EXERCISE_NAME,type);
        long exerciseId = db.insert(EXERCISE_NAME,null,values);
        exercise.setId(String.valueOf(exerciseId));
        return exerciseId;
    }


    public long insertExerciseFromChallenge(Exercise exercise, long id)
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
            exercise.setType(type);
        }
        if(exercise instanceof SuperSet || exercise.getType().equalsIgnoreCase(DatabaseTableNames.SUPER)) {
            type = DatabaseTableNames.SUPER;
            values.put(COL_EXERCISE_ONE,((SuperSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((SuperSet) exercise).getNameTwo());
            values.put(COL_REPS_ONE,((SuperSet) exercise).getRepsForFirst());
            values.put(COL_REPS_TWO,((SuperSet) exercise).getRepsForSecond());
            values.put(COL_SETS,((SuperSet) exercise).getSets());
            exercise.setType(type);
        }
        if(exercise instanceof TripleSet || exercise.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE)) {
            type = DatabaseTableNames.TRIPLE;
            values.put(COL_EXERCISE_ONE,((TripleSet) exercise).getNameOne());
            values.put(COL_EXERCISE_TWO,((TripleSet) exercise).getNameTwo());
            values.put(COL_EXERCISE_THREE,((TripleSet) exercise).getNameThree());
            values.put(COL_REPS_ONE,((TripleSet) exercise).getRepsFirstExercise());
            values.put(COL_REPS_TWO,((TripleSet) exercise).getRepsSecondExercise());
            values.put(COL_REPS_THREE,((TripleSet) exercise).getRepsThirdExercise());
            exercise.setType(type);
        }
        values.put(COL_TYPE_OF_EXERCISE_NAME,type);
        long exerciseId = db.insert(EXERCISES_CHALLENGE_TABLE_NAME,null,values);
        exercise.setId(String.valueOf(exerciseId));
        return exerciseId;
    }

}
