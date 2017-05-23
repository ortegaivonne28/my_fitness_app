package com.example.ivonneortega.myfitnessapp.AddWorkout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;
import com.example.ivonneortega.myfitnessapp.Swipe.ItemTouchHelperAdapter;
import com.example.ivonneortega.myfitnessapp.Swipe.SimpleItemTouchHelperCallback;

import java.util.List;


public class SeeAllWorkoutsFragment extends Fragment implements SeeAllWorkoutsRecyclerViewAdapter.AllWorkoutInterface {

    private SeeAllWorkoutsInterface mListener;
    private RecyclerView mRecyclerView;
    private FitnessDBHelper db;
    private List<Workout> mWorkoutList;
    private SeeAllWorkoutsRecyclerViewAdapter mAdapter;

    public SeeAllWorkoutsFragment() {
        // Required empty public constructor
    }

    public static SeeAllWorkoutsFragment newInstance() {
        SeeAllWorkoutsFragment fragment = new SeeAllWorkoutsFragment();
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
        return inflater.inflate(R.layout.fragment_see_all_workouts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        db = FitnessDBHelper.getInstance(view.getContext());
        mWorkoutList = db.getWorkoutsThatDontBelogToRoutine();
        mAdapter = new SeeAllWorkoutsRecyclerViewAdapter(mWorkoutList,this);

    }

    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.clickedOnWorkout(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SeeAllWorkoutsInterface) {
            mListener = (SeeAllWorkoutsInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SeeAllWorkoutsInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mAdapter);


        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void clickedOnWorkout(String id) {
        mListener.clickedOnWorkout(id);
    }

    @Override
    public void deleteOneWorkout(String id) {
        db.deleteOneWorkout(id);
    }


    public interface SeeAllWorkoutsInterface {
        // TODO: Update argument type and name
        void clickedOnWorkout(String id);
    }


}
