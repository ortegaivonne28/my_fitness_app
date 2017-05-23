package com.example.ivonneortega.myfitnessapp.Routines;

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
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;
import com.example.ivonneortega.myfitnessapp.Swipe.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;


public class RoutinesFragment extends Fragment
implements Routines2RecyclerViewAdapter.recyclerInterface{

    private RoutinesFragmentInterface mListener;
    private RecyclerView mRecyclerview;
    private Routines2RecyclerViewAdapter mAdapter;
    private FitnessDBHelper db;
    private List<Routines> mRoutines;

    public RoutinesFragment() {
        // Required empty public constructor
    }


    public static RoutinesFragment newInstance() {
        RoutinesFragment fragment = new RoutinesFragment();
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
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String id, String name) {
        if (mListener != null) {
            mListener.clickedOnRoutine(id, name );
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerview = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mRecyclerview.getContext(), LinearLayoutManager.VERTICAL,false));

         db= FitnessDBHelper.getInstance(mRecyclerview.getContext());
        mRoutines = new ArrayList<>();
        mRoutines = db.getRoutines();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RoutinesFragmentInterface) {
            mListener = (RoutinesFragmentInterface) context;
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

    @Override
    public void clickedOnItemInRoutineRecyclerView(String id, String name) {
        mListener.clickedOnRoutine(id, name);
    }

    @Override
    public void removeRoutine(String id, int position) {
        if(db!=null)
        {
            if(db.isRoutineUsedAsRoutine(id))
            {
                Toast.makeText(mRecyclerview.getContext(), "Can't removed routine because it's current routine", Toast.LENGTH_SHORT).show();
//                db.removeSetRoutine(id);
            }
            else
            {
                mAdapter.removeOneRoutine(position);
                db.removeRoutine(id);
                Toast.makeText(mRecyclerview.getContext(), "Routine deleted", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mRoutines!= null && mRoutines.size()!=0)
        {
            System.out.println(mRoutines.size());
            mAdapter = new Routines2RecyclerViewAdapter(mRoutines,this,Routines2RecyclerViewAdapter.TYPE_ROUTINE);
            mRecyclerview.setAdapter(mAdapter);

            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(mAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(mRecyclerview);
        }

    }

    public interface RoutinesFragmentInterface {
        // TODO: Update argument type and name
        void clickedOnRoutine(String id, String name);
    }
}
