package com.example.ivonneortega.myfitnessapp.AddWorkout;

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
import android.widget.EditText;

import com.example.ivonneortega.myfitnessapp.AddUserInformation.ExerciseRecyclerViewAdapter;
import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.ArrayList;
import java.util.List;


public class CreateWorkoutFragment extends Fragment implements AddWorkoutActivity.GetListFromFragment{

    private CreateWorkoutInterface mListener;
    private RecyclerView mRecyclerView;
    private ExerciseRecyclerViewAdapter mAdapter;

    public static final int TYPE_USER_CREATING = 1;
    public static final int TYPE_USER_EDITING = 2;
    public static final int TYPE_USER_EDITING_WORKOUT = 3;
    public static final String TYPE = "type";
    public static final String ID = "id";
    private int mType;
    private String mID;
    private Workout mWorkout;


    private EditText mWorkoutName;

    public CreateWorkoutFragment() {
        // Required empty public constructor
    }

    public static CreateWorkoutFragment newInstance(int type, String id) {
        CreateWorkoutFragment fragment = new CreateWorkoutFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE,type);
        args.putString(ID,id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(TYPE);
            mID = getArguments().getString(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_workout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof CreateWorkoutInterface) {
//            mListener = (CreateWorkoutInterface) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mWorkoutName = (EditText) view.findViewById(R.id.workout_name_title);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));
        if(mID == null)
        {
            List<Exercise> list = new ArrayList<>();
            mAdapter = new ExerciseRecyclerViewAdapter(list,mType);
            mRecyclerView.setAdapter(mAdapter);
            mWorkout = new Workout();
        }
        else
        {
            mWorkout  = FitnessDBHelper.getInstance(mRecyclerView.getContext()).getWorkoutById(mID);
            mWorkoutName.setText(mWorkout.getNameOfWorkout());
            mAdapter = new ExerciseRecyclerViewAdapter(mWorkout.getExercises(),mType);
            mRecyclerView.setAdapter(mAdapter);
        }


    }

    @Override
    public Workout getWorkoutFromFragment() {
        if(mAdapter.isExerciseNotEmpty(mAdapter.getListOfExercises().get(mAdapter.getListOfExercises().size()-1)))
        {
            if(mWorkoutName.getText().toString().trim().isEmpty())
                mWorkoutName.setError("Please add a workout name");
            else
            {
                mWorkout.setNameOfWorkout(mWorkoutName.getText().toString());
                mWorkout.setId(mID);
                mWorkout.setExercises(mAdapter.getListOfExercises());
            }
            return mWorkout;
        }
        else
            return null;
    }


//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }



    public interface CreateWorkoutInterface {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface GetListFromRecyclerView {
        List<Exercise> getListOfExercises();
    }

}
