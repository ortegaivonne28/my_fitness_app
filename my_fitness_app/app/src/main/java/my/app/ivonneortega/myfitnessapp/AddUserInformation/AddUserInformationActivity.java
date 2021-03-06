package my.app.ivonneortega.myfitnessapp.AddUserInformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import my.app.ivonneortega.myfitnessapp.Data.Day;
import my.app.ivonneortega.myfitnessapp.Data.Routines;
import my.app.ivonneortega.myfitnessapp.Data.User;
import my.app.ivonneortega.myfitnessapp.Data.Week;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.DatabaseTableNames;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.MainActivity;
import my.app.ivonneortega.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddUserInformationActivity extends AppCompatActivity
        implements AddUserInformationFragment.UserInformationInterface,
        AddRoutineFragment.RoutineInterface,
        AddWeekWorkoutFragment.addWeekWorkoutInterface{

    public static final String TAG = "ACTIVITY";

    private FirebaseAuth mAuth;
    private HashMap<String, Day> mWorkoutHashMap;
    private String[] mListOfDays;
    private List<Week> mWeekList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_information);

        AddUserInformationFragment userInfoFragment = AddUserInformationFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();

        mListOfDays = DatabaseTableNames.LIST_OF_DAYS;

        mAuth = FirebaseAuth.getInstance();


    }



    @Override
    public void addUser(String name, String lastName, int age, float weight, float weightGoal) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.USER);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        User current = new User(currentUser.getUid(),currentUser.getEmail(),name,lastName,null,weight,weightGoal,8);
        myRef.push().setValue(current);

        FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        User user = new User();
        user.setId(currentUser.getUid());
        user.setName(name);
        user.setEmail(current.getEmail());
        user.setWeight(weight);
        user.setDesiredWeight(weightGoal);
        long id = db.insertUserInformation(user);
        System.out.println(id);

        mWorkoutHashMap = new HashMap<>();
        mWeekList = new ArrayList<>();
        setTheme(R.style.AddRoutineActivityTheme);
        AddRoutineFragment userInfoFragment = AddRoutineFragment.newInstance(mWeekList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();


    }

    @Override
    public void AddWeekToRoutine() {
        AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance("Monday", null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();
    }

    @Override
    public void saveRoutine(Routines routines) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.ROUTINES);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        routines.setUserId(currentUser.getUid());

        FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        routines = db.insertRoutine(routines);

        routines.setId(String.valueOf(routines.getId()));

        myRef.push().setValue(routines);




        startActivity(new Intent(AddUserInformationActivity.this, MainActivity.class));
        finish();
//        SeeRoutinesFragment userInfoFragment = SeeRoutinesFragment.newInstance();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container,userInfoFragment)
//                .commit();
    }

    @Override
    public void skip() {
        startActivity(new Intent(AddUserInformationActivity.this, MainActivity.class));
    }

    @Override
    public void changeDay(String day, Workout workout, String currentDay) {
       insertDay(workout,currentDay);
        Day currentDayObject = mWorkoutHashMap.get(day);
        AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance(day,currentDayObject);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,userInfoFragment)
                .commit();
    }

    public void insertDay(Workout workout,String currentDay)
    {
        Day day_aux = new Day(workout);
        mWorkoutHashMap.put(currentDay,day_aux);
    }
    @Override
    public void clickedOnFab(String lastDay, Workout lastWorkout) {
        boolean isValid=true;

        insertDay(lastWorkout,lastDay);
        String day="Monday";
        Workout workout=new Workout();
        int num=0;
        for(int i=0;i<mWorkoutHashMap.size();i++)
        {
            day = mListOfDays[i];
            System.out.println("CLICKED ON FAB: "+day);
            workout = mWorkoutHashMap.get(day).getWorkout();
            if(workout.getNameOfWorkout()==null || workout.getExercises().size()<=0) {
                isValid = false;
                num=i;
                i=mWorkoutHashMap.size();
            }
        }
        if(!isValid)
        {
            AddWeekWorkoutFragment userInfoFragment = AddWeekWorkoutFragment.newInstance(day, mWorkoutHashMap.get(day));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,userInfoFragment)
                    .commit();
        }
        else
        {
           mWeekList.add(new Week(mWorkoutHashMap));
            AddRoutineFragment userInfoFragment = AddRoutineFragment.newInstance(mWeekList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,userInfoFragment)
                    .commit();
        }
    }
}
