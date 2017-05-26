package my.app.ivonneortega.myfitnessapp.Friends;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import my.app.ivonneortega.myfitnessapp.AddWorkout.AddWorkoutActivity;
import my.app.ivonneortega.myfitnessapp.Challenges.ChallengesActivity;
import my.app.ivonneortega.myfitnessapp.Data.Challenges;
import my.app.ivonneortega.myfitnessapp.Data.Friend;
import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.DatabaseTableNames;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.R;
import my.app.ivonneortega.myfitnessapp.Routines.RoutinesActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        SeeAllFriendsFragment.SeeAllFriendsInterface, AddFriendFragment.AddFriendInterface,
        WorkoutToChallenge.WorkoutToChallengeInterface{

    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Friends");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SeeAllFriendsFragment fragment;
        fragment = SeeAllFriendsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_routines) {
            startActivity(new Intent(getApplication(),RoutinesActivity.class));
        } else if (id == R.id.nav_workouts) {
            startActivity(new Intent(getApplication(), AddWorkoutActivity.class));
        } else if (id == R.id.nav_cardio) {

        } else if (id == R.id.nav_friends) {
            startActivity(new Intent(getApplication(), FriendsActivity.class));
        } else if (id == R.id.nav_challenges) {

            startActivity(new Intent(getApplication(), ChallengesActivity.class));
        } else if (id == R.id.nav_settings) {
            this.deleteDatabase(FitnessDBHelper.DATABASE_NAME);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();

            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
//                            startActivity(new Intent(getApplication(), LoginActivity.class));
//                            finish();
                        }
                    });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void clickedOnFriend(String id) {
        WorkoutToChallenge fragment;
        fragment = WorkoutToChallenge.newInstance(id);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void addAFriend() {
        AddFriendFragment fragment;
        fragment = AddFriendFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void addFriend(final Friend friend) {
        FitnessDBHelper db = FitnessDBHelper.getInstance(this);
        db.insertFriend(friend);
        Toast.makeText(this, "Friend added", Toast.LENGTH_SHORT).show();
        final SeeAllFriendsFragment fragment;
        fragment = SeeAllFriendsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        final List<Friend> listOfFriends = db.getAllFriends();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.USER);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        myRef.orderByChild("id").equalTo(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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
                System.out.println(key);


                myRef.child(key).child("friends").setValue(listOfFriends);
//                searchUsers.child(newKey).child("mRatings").child("friendlySUM").setValue(friendSUM);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
//        routines.setUserId(currentUser.getUid());
//        routines.setId(String.valueOf(routines.getId()));
//        myRef.push().setValue(routines);

    });

        myRef.orderByChild("id").equalTo(friend.getFriendId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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
                System.out.println(key);

                Friend friend1 = new Friend();

                friend1.setName(currentUser.getDisplayName());
                friend1.setEmail(currentUser.getEmail());
                friend1.setFriendId(currentUser.getUid());
                friend1.setUserId(friend.getFriendId());

                List<Friend> friendList = new ArrayList<Friend>();
                for (DataSnapshot data: dataSnapshot.child(key).child("friends").getChildren()) {
                    Friend friend = data.getValue(Friend.class);
                    System.out.println("FRIEND ID: "+friend.getFriendId());
                    friendList.add(friend);
                }

                friendList.add(friend1);

                myRef.child(key).child("friends").setValue(friendList);
//                myRef.child(key).child("friends").child(String.valueOf(childCount)).push().setValue(friend1);
//                searchUsers.child(newKey).child("mRatings").child("friendlySUM").setValue(friendSUM);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
//        routines.setUserId(currentUser.getUid());
//        routines.setId(String.valueOf(routines.getId()));
//        myRef.push().setValue(routines);

        });
}


    @Override
    public void insertChallenge(Workout workout, String friendId) {

        final Challenges challenge = new Challenges();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        challenge.setFriendId(friendId);
//        challenge.setUserId(mAuth.getCurrentUser().getUid());
        challenge.setUserId(mAuth.getCurrentUser().getUid());
        challenge.setCreatorId(mAuth.getCurrentUser().getUid());
        challenge.setStatus(DatabaseTableNames.PENDING);

        final FitnessDBHelper db = FitnessDBHelper.getInstance(this);

        challenge.setWorkoutUser(workout);
        challenge.setWorkoutFriend(null);
        final EditText edittext = new EditText(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Enter Your Message");
        alert.setTitle("Enter Your Title");

        alert.setView(edittext);

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                challenge.setTitle(edittext.getText().toString());
//                long id = db.insertChallengeCreatedByUser(challenge);
//                insertChallengeInFriendFirebase(challenge);
                insertChallengeInFirebase(challenge);

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();





    }

    public void insertChallengeInFriendFirebase(final Challenges challenge)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.USER);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        myRef.orderByChild("id").equalTo(challenge.getFriendId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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
                System.out.println(key);


                String userId = challenge.getFriendId();
                String friendId = challenge.getUserId();
                challenge.setCreatorId(friendId);
                challenge.setStatus(DatabaseTableNames.PENDING);

                challenge.setUserId(userId);
                challenge.setFriendId(friendId);
                myRef.child(key).child("challenges").push().setValue(challenge);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void insertChallengeInFirebase(Challenges challenge)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.CHALLENGES);
        mAuth = FirebaseAuth.getInstance();

        String key = myRef.push().getKey();
        myRef.child(key).setValue(challenge);

        challenge.setUniqueKey(key);
        FitnessDBHelper.getInstance(this).insertChallengeCreatedByUser(challenge);

//        final List<Challenges> challengesList = FitnessDBHelper.getInstance(this).getAllChallenges();
//        FirebaseUser currentUser = mAuth.getCurrentUser();

//        myRef.orderByChild("id").equalTo(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                boolean exit=true;
//                int counter = 1;
//                String key = dataSnapshot.getValue().toString();
//                while (exit)
//                {
//                    if(key.charAt(counter)=='=')
//                        exit = false;
//                    else
//                        counter++;
//                }
//
//                key = key.substring(1,counter);
//                System.out.println(key);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
