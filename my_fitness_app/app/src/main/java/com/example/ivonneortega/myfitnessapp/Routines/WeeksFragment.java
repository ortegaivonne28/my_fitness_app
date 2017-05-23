package com.example.ivonneortega.myfitnessapp.Routines;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;
import com.example.ivonneortega.myfitnessapp.Swipe.SimpleItemTouchHelperCallback;

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
    public static final String TITLE = "title";
    private RecyclerView mRecyclerView;
    private Routines2RecyclerViewAdapter mAdapter;
    private FloatingActionButton fab;
    private List<Week> mList;
    private EditText mRoutineName;

    private String mId,mTitle;
    private FitnessDBHelper db;


    private WeekFragmentListener mListener;

    public WeeksFragment() {
        // Required empty public constructor
    }


    public static WeeksFragment newInstance(String id, String title) {
        WeeksFragment fragment = new WeeksFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        args.putString(TITLE,title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mId = getArguments().getString(ID);
            mTitle = getArguments().getString(TITLE);
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

        mRoutineName = (EditText) view.findViewById(R.id.routine_name);
        mRoutineName.setText(mTitle);


        mRoutineName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                db.updateRoutineName(mId,s.toString());
            }
        });

        db = FitnessDBHelper.getInstance(mRecyclerView.getContext());
        mList = db.getWeekById(mId);




        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        //TODO MAYBE ADD OPTION TO ADD MORE WEEKS LATER
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(fab.getContext(), "CLICKED ON FAB", Toast.LENGTH_SHORT).show();
            }
        });


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

    @Override
    public void removeWeek(Week week) {
        if(db != null)
        {
            long idRemoved = db.removeWeekById(week.getId());
            Toast.makeText(mRecyclerView.getContext(),""+idRemoved , Toast.LENGTH_SHORT).show();
            List<Week> weeks = db.getWeekById(week.getId());
            if(weeks==null || weeks.size()<=0)
                mListener.removeRoutine(week.getRoutineId());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new Routines2RecyclerViewAdapter(mList,this,Routines2RecyclerViewAdapter.TYPE_WEEKS);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    public interface WeekFragmentListener {
        // TODO: Update argument type and name
        void clickedOnWeek(String id);
        void removeRoutine(String id);
//        void clickOnAddAnotherWeek(String id);
    }
}
