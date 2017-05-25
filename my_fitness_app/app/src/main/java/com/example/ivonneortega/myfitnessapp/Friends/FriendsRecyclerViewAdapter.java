package com.example.ivonneortega.myfitnessapp.Friends;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.AddUserInformation.ExerciseRecyclerViewAdapter;
import com.example.ivonneortega.myfitnessapp.Data.Friend;
import com.example.ivonneortega.myfitnessapp.Data.Workout;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * Created by ivonneortega on 5/23/17.
 */

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Friend> mFriendList;
    private List<Workout> mWorkouts;

    FriendsInRecyclerViewInterface mFriendListener;
    WorkoutChallengeInRecyclerViewInterface mWorkoutListener;

    public static final int VIEW_TYPE_FRIEND = 1;
    public static final int VIEW_TYPE_WORKOUT = 2;


    public FriendsRecyclerViewAdapter(List<Friend> friendList, FriendsInRecyclerViewInterface listener, List<Workout> workout, WorkoutChallengeInRecyclerViewInterface listener1) {
        mFriendList = friendList;
        mWorkouts = workout;
        mFriendListener  = listener;
        mWorkoutListener = listener1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType)
        {
            case VIEW_TYPE_FRIEND:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_friends, parent, false);
                return new FriendsViewHolder(view);

            case VIEW_TYPE_WORKOUT:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_friends, parent, false);
                return new WorkoutsViewHolder(view1);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FriendsViewHolder) {
            ((FriendsViewHolder) holder).mFriendName.setText(mFriendList.get(position).getName());
            ((FriendsViewHolder) holder).mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(((FriendsViewHolder) holder).mRoot.getContext());
                        builder.setMessage("Do you wish to challenge "+mFriendList.get(holder.getAdapterPosition()).getName()+"?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mFriendListener.clickedOnFriendToChallenge(mFriendList.get(holder.getAdapterPosition()));
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        mWorkoutListener.clickedOnWorkoutToChallengeFriend(mWorkouts.get(holder.getAdapterPosition()));
                                    }
                                });
                        // Create the AlertDialog object and return it
                        builder.create();
                        builder.show();
                }
            });
        }
        if(holder instanceof WorkoutsViewHolder)
        {
            ((WorkoutsViewHolder) holder).mWorkoutName.setText(mWorkouts.get(position).getNameOfWorkout());
            ((WorkoutsViewHolder) holder).mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWorkoutListener.clickedOnWorkoutToChallengeFriend(mWorkouts.get(holder.getAdapterPosition()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(mFriendList==null)
            return mWorkouts.size();
        else
            return mFriendList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mFriendList==null)
            return VIEW_TYPE_WORKOUT;
        else
            return VIEW_TYPE_FRIEND;
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder{

        public TextView mFriendName;
        public View mRoot;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mFriendName = (TextView) itemView.findViewById(R.id.friend_name);
            mRoot = itemView.findViewById(R.id.root);
        }
    }

    public class WorkoutsViewHolder extends RecyclerView.ViewHolder{

        public TextView mWorkoutName;
        public View mRoot;

        public WorkoutsViewHolder(View itemView) {
            super(itemView);
            mWorkoutName = (TextView) itemView.findViewById(R.id.friend_name);
            mRoot = itemView.findViewById(R.id.root);
        }
    }

    public interface FriendsInRecyclerViewInterface{
        void clickedOnFriendToChallenge(Friend friend);
    }

    public interface WorkoutChallengeInRecyclerViewInterface{
        void clickedOnWorkoutToChallengeFriend(Workout workout);
    }
}
