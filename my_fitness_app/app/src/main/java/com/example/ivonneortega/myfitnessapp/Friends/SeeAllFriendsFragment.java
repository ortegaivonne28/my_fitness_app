package com.example.ivonneortega.myfitnessapp.Friends;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivonneortega.myfitnessapp.R;


public class SeeAllFriendsFragment extends Fragment {


    private SeeAllFriendsInterface mListener;

    public SeeAllFriendsFragment() {
        // Required empty public constructor
    }



    public static SeeAllFriendsFragment newInstance() {
        SeeAllFriendsFragment fragment = new SeeAllFriendsFragment();
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
        return inflater.inflate(R.layout.fragment_see_all_friends, container, false);
    }


    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.clickedOnFriend(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SeeAllFriendsInterface) {
            mListener = (SeeAllFriendsInterface) context;
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


    public interface SeeAllFriendsInterface {
        // TODO: Update argument type and name
        void clickedOnFriend(String id);
        void addAFriend();
    }
}
