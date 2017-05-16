package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.R;


public class AddUserInformationFragment extends Fragment {
    private UserInformationInterface mListener;
    private TextView mName, mLastName, mAge, mWeight, mWeightGoal;

    public AddUserInformationFragment() {
        // Required empty public constructor
    }

    public static AddUserInformationFragment newInstance() {
        AddUserInformationFragment fragment = new AddUserInformationFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_user_information, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mName = (TextView) view.findViewById(R.id.user_name);
        mLastName = (TextView) view.findViewById(R.id.user_last_name);
        mAge = (TextView) view.findViewById(R.id.user_age);
        mWeight = (TextView) view.findViewById(R.id.user_weight);
        mWeightGoal = (TextView) view.findViewById(R.id.user_weight_goal);
        view.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedOnDoneButton();
            }


        });
    }

    private void clickedOnDoneButton() {
        if(mName.getText().toString().trim().isEmpty() || mLastName.getText().toString().trim().isEmpty()
                || mAge.getText().toString().trim().isEmpty() || mWeight.getText().toString().trim().isEmpty()
                || mWeightGoal.getText().toString().trim().isEmpty())
        {
            if (mName.getText().toString().trim().isEmpty())
                mName.setError("Must add a name");
            if (mLastName.getText().toString().trim().isEmpty())
                mLastName.setError("Must add a last name");
            if (mAge.getText().toString().trim().isEmpty())
                mAge.setError("Must add an age");
            if (mWeight.getText().toString().trim().isEmpty())
                mWeight.setError("Must add a weight");
            if (mWeightGoal.getText().toString().trim().isEmpty())
                mWeightGoal.setError("Must add a weight goal");
        }
        else
        {
            mListener.addUser(mName.getText().toString(),mLastName.getText().toString(),
                    Integer.parseInt(mAge.getText().toString()),Float.parseFloat(mWeight.getText().toString()),
                            Float.parseFloat(mWeightGoal.getText().toString()));
        }







    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.addUser();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserInformationInterface) {
            mListener = (UserInformationInterface) context;
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

    public interface UserInformationInterface {
        // TODO: Update argument type and name
        void addUser(String name, String lastName, int age, float weight, float weightGoal);
    }
}
