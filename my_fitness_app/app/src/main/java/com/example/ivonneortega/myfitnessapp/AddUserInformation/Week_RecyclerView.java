package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Created by ivonneortega on 5/16/17.
 */

public class Week_RecyclerView extends RecyclerView.Adapter<Week_RecyclerView.CustomViewHolder> {

    private List<Week> mList;
    public static final int VIEW_TYPE_ADD =1;
    public static final int VIEW_TYPE_WEEK = 2;
    private RecyclerViewInterface mListener;

    public Week_RecyclerView(List<Week> list,RecyclerViewInterface context ) {
        mList = list;
        mListener = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case VIEW_TYPE_ADD:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.add_week_routine, parent, false);
                return new CustomViewHolder(view);

            case VIEW_TYPE_WEEK:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.week_routine, parent, false);
                return new CustomViewHolder(view1);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if(position == mList.size() || mList.size()==0)
        {
            holder.mAddWeek.setText("Add Week");
            holder.mAddRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.AddWeekToRoutine();
                }
            });

        }
        else
        {
            holder.mWeekTitle.setText("Week "+position+1);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView mWeekTitle;
        TextView mAddWeek;
        View mAddRoot, mWeekRoot;


        public CustomViewHolder(View itemView) {
            super(itemView);
            mAddWeek = (TextView) itemView.findViewById(R.id.week_add_title);
            mWeekTitle = (TextView) itemView.findViewById(R.id.week_routine_number);
            mAddRoot = itemView.findViewById(R.id.root_add_week);
            mWeekRoot = itemView.findViewById(R.id.week_root);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == mList.size() || mList.size()==0)
            return VIEW_TYPE_ADD;
        return VIEW_TYPE_WEEK;
    }

    public interface RecyclerViewInterface {
        void AddWeekToRoutine();
    }
}
