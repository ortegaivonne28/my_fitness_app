package com.example.ivonneortega.myfitnessapp.Friends;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;


public class WorkoutToChallenge extends Fragment {


    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private FriendsRecyclerViewAdapter mAdapter;

    public WorkoutToChallenge() {
        // Required empty public constructor
    }


    public static WorkoutToChallenge newInstance() {
        WorkoutToChallenge fragment = new WorkoutToChallenge();
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
        return inflater.inflate(R.layout.fragment_workout_to_challenge, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        List<Workout> workouts = FitnessDBHelper.getInstance(mRecyclerView.getContext()).getWorkoutsThatDontBelogToRoutine();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));

        mAdapter = new FriendsRecyclerViewAdapter(null,null,workouts);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}