package my.app.ivonneortega.myfitnessapp.AddWorkout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.R;
import my.app.ivonneortega.myfitnessapp.Swipe.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by ivonneortega on 5/21/17.
 */

public class SeeAllWorkoutsRecyclerViewAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {

    private List<Workout> mWorkoutList;
    private AllWorkoutInterface mListener;

    public SeeAllWorkoutsRecyclerViewAdapter(List<Workout> workoutList, AllWorkoutInterface listener) {
        mWorkoutList = workoutList;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_view_routines, parent, false);
        return new SeeAllWorkoutsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof SeeAllWorkoutsViewHolder)
        {
            ((SeeAllWorkoutsViewHolder) holder).mTextView.setText(mWorkoutList.get(position).getNameOfWorkout());
            ((SeeAllWorkoutsViewHolder) holder).mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.clickedOnWorkout(mWorkoutList.get(holder.getAdapterPosition()).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mListener.deleteOneWorkout(mWorkoutList.get(position).getId());
        mWorkoutList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mWorkoutList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mWorkoutList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public class SeeAllWorkoutsViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        View mRoot;
        public SeeAllWorkoutsViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.routine_name);
            mRoot = itemView.findViewById(R.id.root);
        }

    }

    public interface AllWorkoutInterface{
        void clickedOnWorkout(String id);
        void deleteOneWorkout(String id);
    }
}
