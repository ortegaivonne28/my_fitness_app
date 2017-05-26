package my.app.ivonneortega.myfitnessapp.Challenges;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import my.app.ivonneortega.myfitnessapp.AddWorkout.AddWorkoutActivity;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;
import my.app.ivonneortega.myfitnessapp.Friends.FriendsActivity;
import my.app.ivonneortega.myfitnessapp.R;
import my.app.ivonneortega.myfitnessapp.Routines.RoutinesActivity;

public class ChallengesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String mDay;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(my.app.ivonneortega.myfitnessapp.R.layout.activity_challenges);
        Toolbar toolbar = (Toolbar) findViewById(my.app.ivonneortega.myfitnessapp.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Challenges");

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, my.app.ivonneortega.myfitnessapp.R.string.navigation_drawer_open, my.app.ivonneortega.myfitnessapp.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String day = intent.getStringExtra("day");


        NavigationView navigationView = (NavigationView) findViewById(my.app.ivonneortega.myfitnessapp.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SeeAllChallenges fragment;
        fragment = SeeAllChallenges.newInstance(day);
        getSupportFragmentManager().beginTransaction()
                .replace(my.app.ivonneortega.myfitnessapp.R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(my.app.ivonneortega.myfitnessapp.R.menu.challenges, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == my.app.ivonneortega.myfitnessapp.R.id.action_settings) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(my.app.ivonneortega.myfitnessapp.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
