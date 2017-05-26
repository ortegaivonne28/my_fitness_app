package my.app.ivonneortega.myfitnessapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import my.app.ivonneortega.myfitnessapp.AddUserInformation.AddUserInformationActivity;
import my.app.ivonneortega.myfitnessapp.Data.Challenges;
import my.app.ivonneortega.myfitnessapp.Data.Day;
import my.app.ivonneortega.myfitnessapp.Data.Exercise;
import my.app.ivonneortega.myfitnessapp.Data.Routines;
import my.app.ivonneortega.myfitnessapp.Data.SingleExercise;
import my.app.ivonneortega.myfitnessapp.Data.SuperSet;
import my.app.ivonneortega.myfitnessapp.Data.TripleSet;
import my.app.ivonneortega.myfitnessapp.Data.User;
import my.app.ivonneortega.myfitnessapp.Data.Week;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private ImageView imageLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        imageLogin = (ImageView) findViewById(R.id.image_login);



    }

    @Override
    public void onStart() {
        super.onStart();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            isInDatabase(currentUser);
        }
        else
        {
            imageLogin.setVisibility(View.GONE);
        }

    }

    public void isInDatabase(FirebaseUser user)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("users");
            myRef.orderByChild("id").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        boolean exit=true;
                        int counter = 1;


                        String key = dataSnapshot.getValue().toString();
                        while (exit)
                        {
                            if(key.charAt(counter)=='=')
                                exit = false;
                            else
                                counter++;
                        }

                        key = key.substring(1,counter);

                        User myUser = dataSnapshot.child(key).getValue(User.class);

                        List<Exercise> exerciseList = new ArrayList<>();
                        int workout_counter = 0;
                        int exercises_counter = 0;
                    if(myUser.getWorkouts()!=null)
                    {
                        for (Workout workout : myUser.getWorkouts()) {
                            exercises_counter = 0;

                            for (Exercise exercise : workout.getExercises()) {

                                Exercise exercise1 = dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).getValue(Exercise.class);
                                if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE)) {
                                    Exercise exercise2 = new SingleExercise(0, 0);
                                    exercise2.setId(exercise1.getId());

                                    exercise2.setType(exercise1.getType());
                                    exercise2.setName(exercise1.getName());
                                    if (exercise2 instanceof SingleExercise) {
                                        ((SingleExercise) exercise2).setReps(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("reps").getValue(Integer.class));
                                        ((SingleExercise) exercise2).setSets(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("sets").getValue(Integer.class));
                                    }
                                    exerciseList.add(exercise2);
                                }
                                if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SUPER)) {
                                    SuperSet single = new SuperSet();
                                    single.setId(exercise1.getId());
                                    single.setType(exercise1.getType());
                                    single.setNameOne(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("nameOne").getValue(String.class));
                                    single.setNameTwo(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("nameTwo").getValue(String.class));
                                    single.setNameTwo(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("nameTwo").getValue(String.class));
                                    single.setRepsForFirst(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("repsForFirst").getValue(Integer.class));
                                    single.setRepsForSecond(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("repsForSecond").getValue(Integer.class));
                                    single.setSets(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("sets").getValue(Integer.class));
                                    exerciseList.add(single);

                                }
                                if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE)) {
                                    TripleSet single = new TripleSet();
                                    single.setId(exercise1.getId());
                                    single.setType(exercise1.getType());
                                    single.setNameOne(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("nameOne").getValue(String.class));
                                    single.setNameTwo(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("nameTwo").getValue(String.class));
                                    single.setNameThree(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("nameThree").getValue(String.class));
                                    single.setRepsFirstExercise(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("repsFirstExercise").getValue(Integer.class));
                                    single.setRepsSecondExercise(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("repsSecondExercise").getValue(Integer.class));
                                    single.setRepsThirdExercise((dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("repsThirdExercise").getValue(Integer.class)));
                                    single.setSets(dataSnapshot.child(key).child("workouts").child(String.valueOf(workout_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("exercises").child(String.valueOf(exercises_counter)).child("sets").getValue(Integer.class));
                                    exerciseList.add(single);

                                }
                                exercises_counter++;
                            }

                            myUser.getWorkouts().get(workout_counter).setExercises(exerciseList);
                            workout_counter++;
                        }


                        }
                        insertUserToDB(myUser);
                        Intent intent = new Intent(LoginActivity.this, ChallengesService.class);
                        startService(intent);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else
                    {
                        Intent intent = new Intent(LoginActivity.this, ChallengesService.class);
                        startService(intent);
                        startActivity(new Intent(LoginActivity.this, AddUserInformationActivity.class));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

    }

    public void getUserRoutinesFromFirebase()
    {}

    public void insertUserToDB(User user)
    {
        FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        if(!db.isUserTableNotEmpty())
        {
            db.insertUserInformation(user);
        }
        checkForRoutines(user.getId());
        checkForChallenges(user.getId());
//        Intent intent = new Intent(LoginActivity.this, ChallengesService.class);
//        startService(intent);

    }

    public void checkForRoutines(String id)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.ROUTINES);
//        final DatabaseReference myRef = myRef2.child("challenges");
        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        final Routines[] newRoutine = {null};
        final String[] key = new String[1];


        myRef.orderByChild("userId").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
//
//                        boolean exit=true;
//                        int counter = 1;
//                        key[0] = data.getValue().toString();
//                        while (exit)
//                        {
//                            if(key[0].charAt(counter)=='=')
//                                exit = false;
//                            else
//                                counter++;
//                        }
//
//
//
//                        key[0] = key[0].substring(1,counter);
//
//                        System.out.println(key[0]);

                        String key = data.getKey();

                        long numberOfWeeks = dataSnapshot.child(key).child("weeks").getChildrenCount();

                        String routineId = dataSnapshot.child(key).child("id").getValue(String.class);
                        String routineName = dataSnapshot.child(key).child("name").getValue(String.class);
                        String userId = dataSnapshot.child(key).child("userId").getValue(String.class);

                        Routines routine = new Routines();
                        routine.setId(routineId);
                        routine.setName(routineName);
                        routine.setUserId(userId);

                        if(!db.doesRoutineExits(routineId)){
                            List<Week> weekList = new ArrayList<Week>();
                            for(int i=0;i<numberOfWeeks;i++)
                            {
                                Week week = new Week();
                                HashMap<String, Day> mHashMap = new HashMap<String, Day>();
                                Day thisDay = new Day();
                                for (String day: DatabaseTableNames.LIST_OF_DAYS) {
                                    thisDay = dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).getValue(Day.class);

                                    Workout workout = dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").getValue(Workout.class);
                                    List<Exercise> exerciseList = new ArrayList<>();
                                    int counter2 = 0;
                                    for (Exercise exercise: workout.getExercises()) {
                                        Exercise exercise1 = dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).getValue(Exercise.class);
                                        if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE))
                                        {
                                            Exercise exercise2 = new SingleExercise(0,0);
                                            exercise2.setId(exercise1.getId());

                                            exercise2.setType(exercise1.getType());
                                            exercise2.setName(exercise1.getName());
                                            if(exercise2 instanceof SingleExercise)
                                            {
                                                ((SingleExercise) exercise2).setReps(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("reps").getValue(Integer.class));
                                                ((SingleExercise) exercise2).setSets(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                            }
                                            exerciseList.add(exercise2);
                                        }
                                        if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SUPER))
                                        {
                                            SuperSet single = new SuperSet();
                                            single.setId(exercise1.getId());
                                            single.setType(exercise1.getType());
                                            single.setNameOne(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("nameOne").getValue(String.class));
                                            single.setNameTwo(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                            single.setNameTwo(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                            single.setRepsForFirst(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("repsForFirst").getValue(Integer.class));
                                            single.setRepsForSecond(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("repsForSecond").getValue(Integer.class));
                                            single.setSets(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                            exerciseList.add(single);

                                        }
                                        if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE))
                                        {
                                            TripleSet single = new TripleSet();
                                            single.setId(exercise1.getId());
                                            single.setType(exercise1.getType());
                                            single.setNameOne(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("nameOne").getValue(String.class));
                                            single.setNameTwo(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                            single.setNameThree(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("nameThree").getValue(String.class));
                                            single.setRepsFirstExercise(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("repsFirstExercise").getValue(Integer.class));
                                            single.setRepsSecondExercise(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("repsSecondExercise").getValue(Integer.class));
                                            single.setRepsThirdExercise((dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("repsThirdExercise").getValue(Integer.class)));
                                            single.setSets(dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("dayHashMap").child(day).child("workout").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                            exerciseList.add(single);

                                        }

                                        counter2++;
                                    }

                                    workout.setExercises(exerciseList);
                                    thisDay.setWorkout(workout);
                                    mHashMap.put(day,thisDay);
                                }
                                String id = (String)dataSnapshot.child(key).child("weeks").child(String.valueOf(i)).child("id").getValue();
                                week.setDayHashMap(mHashMap);
                                week.setId(id);
                                week.setRoutineId(routineId);
                                weekList.add(week);
                            }
                            routine.setWeeks(weekList);

                            Routines routine1,routine2;
                            routine1 = db.insertRoutine(routine);



                            routine2 = db.getRoutineById(routine1.getId());


                            if(routine2!=null)
                            {
                                myRef.child(key).setValue(routine2);
                            }
                        }

                    }




                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void checkForChallenges(String id)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.CHALLENGES);
        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);

        mAuth = FirebaseAuth.getInstance();



        myRef.orderByChild("userId").equalTo(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot data: dataSnapshot.getChildren()) {
//
//                        boolean exit=true;
//                        int counter = 1;
//                        key[0] = data.getValue().toString();
//                        while (exit)
//                        {
//                            if(key[0].charAt(counter)=='=')
//                                exit = false;
//                            else
//                                counter++;
//                        }
//
//
//
//                        key[0] = key[0].substring(1,counter);
//
//                        System.out.println(key[0]);

                        String key = data.getKey();



                        String friendId = dataSnapshot.child(key).child("friendId").getValue(String.class);
                        String title = dataSnapshot.child(key).child("title").getValue(String.class);
                        String userId = dataSnapshot.child(key).child("userId").getValue(String.class);
                        String status = dataSnapshot.child(key).child("status").getValue(String.class);

                        Challenges challenge = new Challenges();
                        challenge.setUserId(userId);
                        challenge.setTitle(title);
                        challenge.setUniqueKey(key);
                        System.out.println("UNIQUE KEY: "+key);
                        challenge.setFriendId(friendId);
                        challenge.setStatus(status);

                        if(!db.doesChallengeExits(challenge.getUniqueKey())){

                            Workout workoutUser = dataSnapshot.child(key).child("workoutUser").getValue(Workout.class);


                                int counter=0;
                                List<Exercise> exerciseList = new ArrayList<Exercise>();
                                for(Exercise exercise : workoutUser.getExercises())
                                {
                                    Exercise exercise1 = dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).getValue(Exercise.class);
                                    if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE))
                                    {
                                        Exercise exercise2 = new SingleExercise(0,0);
                                        exercise2.setId(exercise1.getId());

                                        exercise2.setType(exercise1.getType());
                                        exercise2.setName(exercise1.getName());
                                        if(exercise2 instanceof SingleExercise)
                                        {
                                            ((SingleExercise) exercise2).setReps(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("reps").getValue(Integer.class));
                                            ((SingleExercise) exercise2).setSets(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("sets").getValue(Integer.class));
                                        }
                                        exerciseList.add(exercise2);
                                    }
                                    if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SUPER))
                                    {
                                        SuperSet single = new SuperSet();
                                        single.setId(exercise1.getId());
                                        single.setType(exercise1.getType());
                                        single.setNameOne(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameOne").getValue(String.class));
                                        single.setNameTwo(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameTwo").getValue(String.class));
                                        single.setNameTwo(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameTwo").getValue(String.class));
                                        single.setRepsForFirst(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsForFirst").getValue(Integer.class));
                                        single.setRepsForSecond(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsForSecond").getValue(Integer.class));
                                        single.setSets(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("sets").getValue(Integer.class));
                                        exerciseList.add(single);

                                    }
                                    if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE))
                                    {
                                        TripleSet single = new TripleSet();
                                        single.setId(exercise1.getId());
                                        single.setType(exercise1.getType());
                                        single.setNameOne(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameOne").getValue(String.class));
                                        single.setNameTwo(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameTwo").getValue(String.class));
                                        single.setNameThree(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameThree").getValue(String.class));
                                        single.setRepsFirstExercise(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsFirstExercise").getValue(Integer.class));
                                        single.setRepsSecondExercise(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsSecondExercise").getValue(Integer.class));
                                        single.setRepsThirdExercise((dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsThirdExercise").getValue(Integer.class)));
                                        single.setSets(dataSnapshot.child(key).child("workoutUser").child("exercises").child(String.valueOf(counter)).child("sets").getValue(Integer.class));
                                        exerciseList.add(single);

                                    }

                                    counter++;
                                }
                                workoutUser.setExercises(exerciseList);


                            Workout workoutFriend = dataSnapshot.child(key).child("workFriend").getValue(Workout.class);
                            int counter2=0;
                            List<Exercise> exerciseList1 = new ArrayList<Exercise>();
                            if(workoutFriend!=null) {
                                for (Exercise exercise : workoutFriend.getExercises()) {
                                    Exercise exercise1 = dataSnapshot.child(key).child("workFriend").child("exercises").child(String.valueOf(counter2)).getValue(Exercise.class);
                                    if (exercise1 != null) {

                                        if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE)) {
                                            Exercise exercise2 = new SingleExercise(0, 0);
                                            exercise2.setId(exercise1.getId());

                                            exercise2.setType(exercise1.getType());
                                            exercise2.setName(exercise1.getName());
                                            if (exercise2 instanceof SingleExercise) {
                                                ((SingleExercise) exercise2).setReps(dataSnapshot.child(key).child("workFriend").child("workoutUser").child("exercises").child(String.valueOf(counter2)).child("reps").getValue(Integer.class));
                                                ((SingleExercise) exercise2).setSets(dataSnapshot.child(key).child("workFriend").child("workoutUser").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                            }
                                            exerciseList1.add(exercise2);
                                        }
                                        if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SUPER)) {
                                            SuperSet single = new SuperSet();
                                            single.setId(exercise1.getId());
                                            single.setType(exercise1.getType());
                                            single.setNameOne(dataSnapshot.child(key).child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameOne").getValue(String.class));
                                            single.setNameTwo(dataSnapshot.child(key).child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                            single.setNameTwo(dataSnapshot.child(key).child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                            single.setRepsForFirst(dataSnapshot.child(key).child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsForFirst").getValue(Integer.class));
                                            single.setRepsForSecond(dataSnapshot.child(key).child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsForSecond").getValue(Integer.class));
                                            single.setSets(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                            exerciseList1.add(single);

                                        }
                                        if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE)) {
                                            TripleSet single = new TripleSet();
                                            single.setId(exercise1.getId());
                                            single.setType(exercise1.getType());
                                            single.setNameOne(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameOne").getValue(String.class));
                                            single.setNameTwo(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                            single.setNameThree(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameThree").getValue(String.class));
                                            single.setRepsFirstExercise(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsFirstExercise").getValue(Integer.class));
                                            single.setRepsSecondExercise(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsSecondExercise").getValue(Integer.class));
                                            single.setRepsThirdExercise((dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsThirdExercise").getValue(Integer.class)));
                                            single.setSets(dataSnapshot.child(key).child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                            exerciseList1.add(single);
                                        }
                                        counter2++;
                                    }
                                }
                                workoutFriend.setExercises(exerciseList1);
                            }
                            challenge.setWorkoutUser(workoutUser);
                            challenge.setWorkoutFriend(workoutFriend);
                            db.insertChallengeFromFirebase(challenge);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {

//                mAuth.signOut();
                Toast.makeText(LoginActivity.this, "Authentication failed, please try again",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            isInDatabase(user);
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
//                            mAuth.signOut();
//                            System.out.println(task.getResult().toString());
                            Toast.makeText(LoginActivity.this, "Authentication failed, please try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
