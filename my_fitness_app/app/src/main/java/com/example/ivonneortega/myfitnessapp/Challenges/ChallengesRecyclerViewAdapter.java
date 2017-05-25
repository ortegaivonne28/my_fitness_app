package com.example.ivonneortega.myfitnessapp.Challenges;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.Data.Challenges;
import com.example.ivonneortega.myfitnessapp.Friends.FriendsRecyclerViewAdapter;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * Created by ivonneortega on 5/24/17.
 */

public class ChallengesRecyclerViewAdapter extends RecyclerView.Adapter<ChallengesRecyclerViewAdapter.ChallengesViewHolder> {

    List<Challenges> mList;

    public ChallengesRecyclerViewAdapter(List<Challenges> list) {
        mList = list;
    }

    @Override
    public ChallengesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_friends, parent, false);
        return new ChallengesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChallengesViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position).getTitle());

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ChallengesViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public View mRoot;

        public ChallengesViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.friend_name);
            mRoot = itemView.findViewById(R.id.root);
        }
    }
}
