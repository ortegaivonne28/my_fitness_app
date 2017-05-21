package com.example.ivonneortega.myfitnessapp.Routines;

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

import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeekFragmentListener} interface
 * to handle interaction events.
 * Use the {@link WeeksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeeksFragment extends Fragment
implements Routines2RecyclerViewAdapter.weekInterface{
    public static final String ID = "id";
    private RecyclerView mRecyclerView;
    private Routines2RecyclerViewAdapter mAdapter;

    private String mId;


    private WeekFragmentListener mListener;

    public WeeksFragment() {
        // Required empty public constructor
    }


    public static WeeksFragment newInstance(String id) {
        WeeksFragment fragment = new WeeksFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weeks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.week_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));

        FitnessDBHelper db = FitnessDBHelper.getInstance(mRecyclerView.getContext());
        List<Week> list = db.getWeekById(mId);
        mAdapter = new Routines2RecyclerViewAdapter(list,this,Routines2RecyclerViewAdapter.TYPE_WEEKS);
        mRecyclerView.setAdapter(mAdapter);


    }

    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.clickedOnWeek(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WeekFragmentListener) {
            mListener = (WeekFragmentListener) context;
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
    public void clickedOnItemWeekRecyclerView(String id) {
        mListener.clickedOnWeek(id);
    }


    public interface WeekFragmentListener {
        // TODO: Update argument type and name
        void clickedOnWeek(String id);
    }
}
