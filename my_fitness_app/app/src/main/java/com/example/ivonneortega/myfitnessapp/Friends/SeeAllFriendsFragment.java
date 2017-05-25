package com.example.ivonneortega.myfitnessapp.Friends;

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
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.Data.Friend;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;


public class SeeAllFriendsFragment extends Fragment implements FriendsRecyclerViewAdapter.FriendsInRecyclerViewInterface {


    private SeeAllFriendsInterface mListener;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;
    private FriendsRecyclerViewAdapter mAdapter;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        List<Friend> friends = FitnessDBHelper.getInstance(mRecyclerView.getContext()).getAllFriends();
        mAdapter = new FriendsRecyclerViewAdapter(friends,this,null,null);
        mRecyclerView.setAdapter(mAdapter);


        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addAFriend();
            }
        });

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

    @Override
    public void clickedOnFriendToChallenge(Friend friend) {
        mListener.clickedOnFriend(friend.getFriendId());
    }


    public interface SeeAllFriendsInterface {
        // TODO: Update argument type and name
        void clickedOnFriend(String id);
        void addAFriend();
    }
}
