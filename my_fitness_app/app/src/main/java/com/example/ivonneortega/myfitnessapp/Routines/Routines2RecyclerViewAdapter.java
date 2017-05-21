package com.example.ivonneortega.myfitnessapp.Routines;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * Created by ivonneortega on 5/19/17.
 */

public class Routines2RecyclerViewAdapter extends RecyclerView.Adapter<Routines2RecyclerViewAdapter.RoutinesViewHolder> {

    private List<Routines> mRoutinesList;
    private List<Week> mWeekList;
    private recyclerInterface mListener;
    private weekInterface mWeekInterface;
    private int mType;

    public static final int TYPE_ROUTINE = 1;
    public static final int TYPE_WEEKS = 2;

    public Routines2RecyclerViewAdapter(List<Routines> list, recyclerInterface recyclerInterface, int type) {
        mRoutinesList = list;
        mListener = recyclerInterface;
        mType = type;
    }

    public Routines2RecyclerViewAdapter(List<Week> list, weekInterface weekInterface, int type) {
        mWeekList = list;
        mWeekInterface = weekInterface;
        mType = type;
    }

    @Override
    public RoutinesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_view_routines, parent, false);
        return new RoutinesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RoutinesViewHolder holder, final int position) {

        if(mType == TYPE_ROUTINE)
        {
            holder.mTextView.setText(mRoutinesList.get(position).getName());
            holder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickedOnItemInRoutineRecyclerView(mRoutinesList.get(position).getId());
                }
            });
        }
        else
        {
            holder.mTextView.setText("Week "+position+1);
            holder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeekInterface.clickedOnItemWeekRecyclerView(mWeekList.get(position).getId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(mType == TYPE_ROUTINE)
        return mRoutinesList.size();
        else {
            return mWeekList.size();
        }
    }

    public class RoutinesViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        View mRoot;

        public RoutinesViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.routine_name);
            mRoot = itemView.findViewById(R.id.root);
        }
    }

    public interface recyclerInterface
    {
        void clickedOnItemInRoutineRecyclerView(String id);
    }

    public interface weekInterface
    {
        void clickedOnItemWeekRecyclerView(String id);
    }


}
