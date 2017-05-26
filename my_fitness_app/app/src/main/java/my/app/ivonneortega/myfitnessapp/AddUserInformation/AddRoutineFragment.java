package my.app.ivonneortega.myfitnessapp.AddUserInformation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import my.app.ivonneortega.myfitnessapp.Data.Routines;
import my.app.ivonneortega.myfitnessapp.Data.Week;
import my.app.ivonneortega.myfitnessapp.R;

import java.util.ArrayList;
import java.util.List;

public class AddRoutineFragment extends Fragment
implements Week_RecyclerView.RecyclerViewInterface{


    private RoutineInterface mListener;
    private RecyclerView mRecyclerView;
    private List<Week> mWeekList;
    public static final String LIST = "list";
    private FloatingActionButton mFloatingActionButton;
    private Routines mRoutines;
    private TextView mTitle, mDuration, mSkip;

    public AddRoutineFragment() {
        // Required empty public constructor
    }



    public static AddRoutineFragment newInstance(List<Week> list) {
        AddRoutineFragment fragment = new AddRoutineFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM2, param2);
        args.putParcelableArrayList(LIST,(ArrayList)list);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWeekList = (ArrayList) getArguments().getParcelableArrayList(LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_routine, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.routine_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        Week_RecyclerView adapter = new Week_RecyclerView(mWeekList,this);
        mRecyclerView.setAdapter(adapter);

        mRoutines = new Routines(mWeekList);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        mDuration = (TextView) view.findViewById(R.id.routine_duration);
        mTitle = (TextView) view.findViewById(R.id.routine_title);
        mSkip = (TextView) view.findViewById(R.id.skip);

        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.skip();
            }
        });


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTitle.getText().toString().trim().isEmpty())
                    mTitle.setError("Please add routine title");
                else
                {
                    mRoutines.setName(mTitle.getText().toString());
                    mListener.saveRoutine(mRoutines);
                }
            }
        });


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.AddWeekToRoutine();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RoutineInterface) {
            mListener = (RoutineInterface) context;
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
    public void AddWeekToRoutine() {
        mListener.AddWeekToRoutine();
    }


    public interface RoutineInterface {
        // TODO: Update argument type and name
        void AddWeekToRoutine();
        void saveRoutine(Routines routines);
        void skip();
    }
}

