package com.example.ivonneortega.myfitnessapp.AddUserInformation;

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

import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.ArrayList;
import java.util.List;


public class SeeRoutinesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static List<Routines> mRoutines;


//    private OnFragmentInteractionListener mListener;

    public SeeRoutinesFragment() {
        // Required empty public constructor
    }


    public static SeeRoutinesFragment newInstance(List<Routines> list) {
        SeeRoutinesFragment fragment = new SeeRoutinesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        if(list!=null)
            mRoutines = list;
        else
            mRoutines = new ArrayList<>();
//        args.putString(ARG_PARAM2, param2);
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
        return inflater.inflate(R.layout.fragment_see_routines, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(new RoutineRecyclerViewAdapter(mRoutines));
    }

    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.clickedOnRoutine(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
//
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void clickedOnRoutine(Uri uri);
//    }
}
