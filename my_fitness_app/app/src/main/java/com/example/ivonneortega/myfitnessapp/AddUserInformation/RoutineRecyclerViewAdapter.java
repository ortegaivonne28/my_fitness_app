package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.Data.Routines;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

/**
 * Created by ivonneortega on 5/18/17.
 */

public class RoutineRecyclerViewAdapter extends RecyclerView.Adapter<RoutineRecyclerViewAdapter.CustomViewHolder>{
        private List<Routines> mList;

        public RoutineRecyclerViewAdapter(List<Routines> list) {
            mList = list;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.add_week_routine, parent, false);
                    return new CustomViewHolder(view);

        }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.mRoutine.setText(mList.get(position).getName());
    }


        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder{

            TextView mRoutine;
            View mRoot;


            public CustomViewHolder(View itemView) {
                super(itemView);
                mRoutine = (TextView) itemView.findViewById(R.id.week_routine_number);
                mRoot = itemView.findViewById(R.id.week_root);
            }
        }

    }

