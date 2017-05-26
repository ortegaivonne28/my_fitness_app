package com.example.ivonneortega.myfitnessapp.Challenges;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.Data.Challenges;
import com.example.ivonneortega.myfitnessapp.Data.Friend;
import com.example.ivonneortega.myfitnessapp.DatabaseTableNames;
import com.example.ivonneortega.myfitnessapp.FitnessDBHelper;
import com.example.ivonneortega.myfitnessapp.Friends.FriendsRecyclerViewAdapter;
import com.example.ivonneortega.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import okhttp3.Challenge;

/**
 * Created by ivonneortega on 5/24/17.
 */

public class ChallengesRecyclerViewAdapter extends RecyclerView.Adapter<ChallengesRecyclerViewAdapter.ChallengesViewHolder> {

    List<Challenges> mList;
    ChallengesRecyclerInterface mListener;

    public ChallengesRecyclerViewAdapter(List<Challenges> list, ChallengesRecyclerInterface listener) {
        mList = list;
        mListener = listener;
    }

    @Override
    public ChallengesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_challenges, parent, false);
        return new ChallengesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ChallengesViewHolder holder, final int position) {
        holder.mChallengeName.setText(mList.get(position).getTitle());
        Friend friend = FitnessDBHelper.getInstance(holder.mChallengeCreator.getContext()).getFriendByFriendId(mList.get(position).getFriendId());


        System.out.println("FRIEND ID: "+mList.get(position).getFriendId());
        holder.mChallengeCreator.setText("Friend name: "+friend.getName());
        if(friend.getFriendId().equalsIgnoreCase(mList.get(position).getFriendId()))
            holder.mChallengeCreator.setText("Created by: "+friend.getName());
        else
            holder.mChallengeCreator.setText("Created by: ME");


        holder.mChallengeStatus.setText("Challenge Status: "+mList.get(position).getStatus());

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mList.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase(DatabaseTableNames.PENDING))
                {
                    if(!mList.get(holder.getAdapterPosition()).getCreatorId().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    {
                        FitnessDBHelper.getInstance(holder.mChallengeCreator.getContext())
                                .updateStatusChallenge(mList.get(position).getUniqueKey(),DatabaseTableNames.ACCEPTED);
                        mListener.approvedAChallenge(mList.get(position));
                        mList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(holder.mChallengeCreator.getContext(), "CHALLENGE APPROVED", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(holder.mChallengeCreator.getContext(), "Have to wait for approval", Toast.LENGTH_SHORT).show();
                    }
                }
                if(mList.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase(DatabaseTableNames.ACCEPTED))
                {
                    holder.mRoot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.clickedOnItemApproved(mList.get(holder.getAdapterPosition()));
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ChallengesViewHolder extends RecyclerView.ViewHolder{

        public TextView mChallengeName, mChallengeStatus, mChallengeCreator;
        public View mRoot;

        public ChallengesViewHolder(View itemView) {
            super(itemView);
            mChallengeName = (TextView) itemView.findViewById(R.id.challenge_name);
            mChallengeCreator = (TextView) itemView.findViewById(R.id.challenge_created);
            mChallengeStatus = (TextView) itemView.findViewById(R.id.challenge_status);
            mRoot = itemView.findViewById(R.id.root);
        }
    }

    public interface ChallengesRecyclerInterface{
        void approvedAChallenge(Challenges challenge);
        void clickedOnItemApproved(Challenges challenge);
    }

    public void swapList(List<Challenges> list)
    {
        mList = list;
        notifyDataSetChanged();
    }
}
