package com.example.ivonneortega.myfitnessapp.Challenges;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.myfitnessapp.Data.Challenges;
import com.example.ivonneortega.myfitnessapp.DatabaseTableNames;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class SeeAllChallenges extends Fragment implements ChallengesRecyclerViewAdapter.ChallengesRecyclerInterface {


    private OnFragmentInteractionListener mListener;
    private RecyclerView mPendingRecyclerView,mAcceptedRecyclerView;
    private ChallengesRecyclerViewAdapter mPendingAdapter,mAcceptedAdapter;
    private List<Challenges> acceptedList;

    public SeeAllChallenges() {
        // Required empty public constructor
    }


    public static SeeAllChallenges newInstance() {
        SeeAllChallenges fragment = new SeeAllChallenges();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_see_all_challenges, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPendingRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        FitnessDBHelper db = FitnessDBHelper.getInstance(mPendingRecyclerView.getContext());
        List<Challenges> pendingList = db.getAllPendingChallenges();

        mPendingRecyclerView.setLayoutManager(new LinearLayoutManager(mPendingRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mPendingAdapter = new ChallengesRecyclerViewAdapter(pendingList,this);
        mPendingRecyclerView.setAdapter(mPendingAdapter);


        mAcceptedRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view2);
        acceptedList = db.getAllAceptedChallenges();

        mAcceptedRecyclerView.setLayoutManager(new LinearLayoutManager(mPendingRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mAcceptedAdapter = new ChallengesRecyclerViewAdapter(acceptedList,this);
        mAcceptedRecyclerView.setAdapter(mAcceptedAdapter);




    }

    public void updateStatusChallengeInDatabase(final Challenges challenges)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(DatabaseTableNames.CHALLENGES);
        final FitnessDBHelper db = FitnessDBHelper.getInstance(mAcceptedRecyclerView.getContext());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();



        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    System.out.println("STATUS: "+challenges.getStatus());
                    myRef.child(challenges.getUniqueKey()).child("status").setValue(challenges.getStatus());

                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void approvedAChallenge(Challenges challenge) {
        acceptedList.add(challenge);
        mAcceptedAdapter.notifyItemInserted(acceptedList.size()-1);
        challenge.setStatus(DatabaseTableNames.ACCEPTED);
        updateStatusChallengeInDatabase(challenge);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

