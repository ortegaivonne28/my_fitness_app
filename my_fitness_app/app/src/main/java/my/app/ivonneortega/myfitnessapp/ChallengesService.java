package my.app.ivonneortega.myfitnessapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import my.app.ivonneortega.myfitnessapp.Challenges.ChallengesActivity;
import my.app.ivonneortega.myfitnessapp.Data.Challenges;
import my.app.ivonneortega.myfitnessapp.Data.Exercise;
import my.app.ivonneortega.myfitnessapp.Data.Friend;
import my.app.ivonneortega.myfitnessapp.Data.SingleExercise;
import my.app.ivonneortega.myfitnessapp.Data.SuperSet;
import my.app.ivonneortega.myfitnessapp.Data.TripleSet;
import my.app.ivonneortega.myfitnessapp.Data.User;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ivonneortega on 5/24/17.
 */

public class ChallengesService extends Service {

    FirebaseAuth mAuth;
    public static final int NOTIFICATION_ID = 1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }


    public void checkForChallengesUpdates()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);


        final DatabaseReference myRef = database.getReference(DatabaseTableNames.CHALLENGES);
        myRef.orderByChild("userId").equalTo(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String status = dataSnapshot.child("status").getValue(String.class);
                String friendId = dataSnapshot.child("friendId").getValue(String.class);

                Friend friend = db.getFriendByFriendId(friendId);
                db.updateStatusChallenge(key,status);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra(DatabaseTableNames.NOTIFICATION_USER_ADD_AS_FRIEND);

                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                mBuilder.setContentTitle(friend.getName()+" has set the challenge to: "+status);
//                        mBuilder.setContentText("accept");
                mBuilder.setContentIntent(pIntent);
                mBuilder.setPriority(Notification.PRIORITY_MAX);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(2, mBuilder.build());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkChallengesFirebase()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);


        final DatabaseReference myRef = database.getReference(DatabaseTableNames.CHALLENGES);
        myRef.orderByChild("friendId").equalTo(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {


                    // for (DataSnapshot data: dataSnapshot.getChildren()) {
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

                    String key = dataSnapshot.getKey();
//                    System.out.println("KEY "+dataSnapshot.child("userId").getValue());


                    String friendId = dataSnapshot.child("userId").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String crreator = dataSnapshot.child("creatorId").getValue(String.class);
                    String userId = dataSnapshot.child("friendId").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);


                    Challenges challenge = new Challenges();
                    challenge.setUserId(userId);
                    challenge.setTitle(title);
                    challenge.setUniqueKey(key);
                    challenge.setFriendId(friendId);
                    challenge.setStatus(status);
                    challenge.setCreatorId(crreator);

                    if(!db.doesChallengeExits(challenge.getUniqueKey())){


                        Workout workoutUser = dataSnapshot.child("workoutUser").getValue(Workout.class);

                        int counter=0;
                        List<Exercise> exerciseList = new ArrayList<Exercise>();
                        for(Exercise exercise : workoutUser.getExercises())
                        {
                            Exercise exercise1 = dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).getValue(Exercise.class);
                            if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE))
                            {
                                Exercise exercise2 = new SingleExercise(0,0);
                                exercise2.setId(exercise1.getId());

                                exercise2.setType(exercise1.getType());
                                exercise2.setName(exercise1.getName());
                                if(exercise2 instanceof SingleExercise)
                                {
                                    ((SingleExercise) exercise2).setReps(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("reps").getValue(Integer.class));
                                    ((SingleExercise) exercise2).setSets(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("sets").getValue(Integer.class));
                                }
                                exerciseList.add(exercise2);
                            }
                            if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SUPER))
                            {
                                SuperSet single = new SuperSet();
                                single.setId(exercise1.getId());
                                single.setType(exercise1.getType());
                                single.setNameOne(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameOne").getValue(String.class));
                                single.setNameTwo(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameTwo").getValue(String.class));
                                single.setNameTwo(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameTwo").getValue(String.class));
                                single.setRepsForFirst(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsForFirst").getValue(Integer.class));
                                single.setRepsForSecond(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsForSecond").getValue(Integer.class));
                                single.setSets(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("sets").getValue(Integer.class));
                                exerciseList.add(single);

                            }
                            if(exercise1.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE))
                            {
                                TripleSet single = new TripleSet();
                                single.setId(exercise1.getId());
                                single.setType(exercise1.getType());
                                single.setNameOne(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameOne").getValue(String.class));
                                single.setNameTwo(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameTwo").getValue(String.class));
                                single.setNameThree(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("nameThree").getValue(String.class));
                                single.setRepsFirstExercise(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsFirstExercise").getValue(Integer.class));
                                single.setRepsSecondExercise(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsSecondExercise").getValue(Integer.class));
                                single.setRepsThirdExercise((dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("repsThirdExercise").getValue(Integer.class)));
                                single.setSets(dataSnapshot.child("workoutUser").child("exercises").child(String.valueOf(counter)).child("sets").getValue(Integer.class));
                                exerciseList.add(single);

                            }

                            counter++;
                        }
                        workoutUser.setExercises(exerciseList);


                        Workout workoutFriend = dataSnapshot.child("workFriend").getValue(Workout.class);
                        int counter2=0;
                        List<Exercise> exerciseList1 = new ArrayList<Exercise>();
                        if(workoutFriend!=null) {
                            for (Exercise exercise : workoutFriend.getExercises()) {
                                Exercise exercise1 = dataSnapshot.child("workFriend").child("exercises").child(String.valueOf(counter2)).getValue(Exercise.class);
                                if (exercise1 != null) {

                                    if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SINGLE)) {
                                        Exercise exercise2 = new SingleExercise(0, 0);
                                        exercise2.setId(exercise1.getId());

                                        exercise2.setType(exercise1.getType());
                                        exercise2.setName(exercise1.getName());
                                        if (exercise2 instanceof SingleExercise) {
                                            ((SingleExercise) exercise2).setReps(dataSnapshot.child("workFriend").child("workoutUser").child("exercises").child(String.valueOf(counter2)).child("reps").getValue(Integer.class));
                                            ((SingleExercise) exercise2).setSets(dataSnapshot.child("workFriend").child("workoutUser").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                        }
                                        exerciseList1.add(exercise2);
                                    }
                                    if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.SUPER)) {
                                        SuperSet single = new SuperSet();
                                        single.setId(exercise1.getId());
                                        single.setType(exercise1.getType());
                                        single.setNameOne(dataSnapshot.child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameOne").getValue(String.class));
                                        single.setNameTwo(dataSnapshot.child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                        single.setNameTwo(dataSnapshot.child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                        single.setRepsForFirst(dataSnapshot.child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsForFirst").getValue(Integer.class));
                                        single.setRepsForSecond(dataSnapshot.child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsForSecond").getValue(Integer.class));
                                        single.setSets(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
                                        exerciseList1.add(single);

                                    }
                                    if (exercise1.getType().equalsIgnoreCase(DatabaseTableNames.TRIPLE)) {
                                        TripleSet single = new TripleSet();
                                        single.setId(exercise1.getId());
                                        single.setType(exercise1.getType());
                                        single.setNameOne(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameOne").getValue(String.class));
                                        single.setNameTwo(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameTwo").getValue(String.class));
                                        single.setNameThree(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("nameThree").getValue(String.class));
                                        single.setRepsFirstExercise(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsFirstExercise").getValue(Integer.class));
                                        single.setRepsSecondExercise(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsSecondExercise").getValue(Integer.class));
                                        single.setRepsThirdExercise((dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("repsThirdExercise").getValue(Integer.class)));
                                        single.setSets(dataSnapshot.child("challenges").child("workFriend").child("exercises").child(String.valueOf(counter2)).child("sets").getValue(Integer.class));
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

                        Friend friend = db.getFriendByFriendId(friendId);
                        if(friend!=null)
                        {

                            Intent intent = new Intent(getApplicationContext(), ChallengesActivity.class);

                            PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                            mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                            mBuilder.setContentTitle(friend.getName()+" has challenged you");
                            mBuilder.setContentText(challenge.getTitle());
                            mBuilder.setContentIntent(pIntent);
                            mBuilder.setPriority(Notification.PRIORITY_MAX);

                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());



                            sendMyBroadcast(challenge, friend.getName());
                        }

                    }
                    // }
                }
            }



            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                String status = dataSnapshot.child("status").getValue(String.class);
                db.updateStatusChallenge(key,status);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {




        checkChallengesFirebase();
        checkForChallengesUpdates();







        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);



        final DatabaseReference myRef3 = database.getReference(DatabaseTableNames.USER);
        DatabaseReference myRef2 = myRef3.child("friends");
        myRef3.orderByChild("id").equalTo(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    String key = dataSnapshot.getKey();

                    User user = dataSnapshot.getValue(User.class);
                    List<Friend> friendList= user.getFriends();
                    if(friendList!=null && !db.doesFriendExitsInDatabase(friendList.get(friendList.size()-1).getFriendId()))
                    {

                        Friend friend = friendList.get(friendList.size()-1);
                        db.insertFriend(friend);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra(DatabaseTableNames.NOTIFICATION_USER_ADD_AS_FRIEND);

                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                        mBuilder.setContentTitle("A friend has added you");
//                        mBuilder.setContentText("accept");
                        mBuilder.setContentIntent(pIntent);
                        mBuilder.setPriority(Notification.PRIORITY_MAX);

                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        mNotificationManager.notify(2, mBuilder.build());
                    }

                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service Destroyed");
    }

    private void sendMyBroadcast(Challenges challenge, String friendName) {
        Intent broadcastIntent = new Intent("challenge");
        broadcastIntent.putExtra("title",challenge.getTitle());
        broadcastIntent.putExtra("name",friendName);
        broadcastIntent.putExtra("id",challenge.getUniqueKey());
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }



}
