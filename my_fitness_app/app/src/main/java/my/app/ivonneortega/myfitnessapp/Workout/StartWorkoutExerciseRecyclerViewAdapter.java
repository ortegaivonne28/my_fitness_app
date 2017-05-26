package my.app.ivonneortega.myfitnessapp.Workout;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import my.app.ivonneortega.myfitnessapp.Data.Exercise;
import my.app.ivonneortega.myfitnessapp.Data.Sets;
import my.app.ivonneortega.myfitnessapp.Data.SingleExercise;
import my.app.ivonneortega.myfitnessapp.Data.SuperSet;
import my.app.ivonneortega.myfitnessapp.Data.TripleSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivonneortega on 5/21/17.
 */

public class StartWorkoutExerciseRecyclerViewAdapter extends RecyclerView.Adapter {

    Exercise mExercise;
    
    public static final int VIEW_TYPE_SINGLE = 0;
    public static final int VIEW_TYPE_SUPER = 2;
    public static final int VIEW_TYPE_TRIPLE = 3;
    private updateExercise mListener;
    private int mParentAdapterPosition;

    public StartWorkoutExerciseRecyclerViewAdapter(Exercise exercise, updateExercise listener, int position) {
        mExercise = exercise;
        mListener = listener;
        mParentAdapterPosition = position;
        List<Sets> list = new ArrayList<>();
        int size = 0;
        if(exercise instanceof SingleExercise)
           size = ((SingleExercise) exercise).getSets();
        if(exercise instanceof SuperSet)
            size = ((SuperSet) exercise).getSets();
        if(exercise instanceof TripleSet)
            size = ((TripleSet) exercise).getSets();

        if(mExercise.getSetsList().size()!=size)
        {
            for(int i=0;i<size;i++)
            {
                list.add(new Sets());
            }
            mExercise.setSetsList(list);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case VIEW_TYPE_SINGLE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(my.app.ivonneortega.myfitnessapp.R.layout.start_workout_exercise_single, parent, false);
                return new StartWorkoutSingleExerciseViewHolder(view);
            case VIEW_TYPE_SUPER:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(my.app.ivonneortega.myfitnessapp.R.layout.start_workout_super, parent, false);
                return new StartWorkoutSuperExerciseViewHolder(view1);
            case VIEW_TYPE_TRIPLE:
                View view2 = LayoutInflater.from(parent.getContext())
                        .inflate(my.app.ivonneortega.myfitnessapp.R.layout.start_workout_triple, parent, false);
                return new StartWorkoutTripleExerciseViewHolder(view2);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int pos = position+1;
        if(holder instanceof StartWorkoutSingleExerciseViewHolder)
        {
            SingleExercise singleExercise = (SingleExercise) mExercise;
           ((StartWorkoutSingleExerciseViewHolder) holder).mSetNumber.setText("Set #"+pos);
            ((StartWorkoutSingleExerciseViewHolder) holder).mSetOne.setHint("Target: "+singleExercise.getReps());

            ((StartWorkoutSingleExerciseViewHolder) holder).mSetOne.addTextChangedListener(new CustomTextWatcher(((StartWorkoutSingleExerciseViewHolder) holder).mSetOne,position,VIEW_TYPE_SINGLE,CustomTextWatcher.REPS_ONE));
            ((StartWorkoutSingleExerciseViewHolder) holder).mWeightOne.addTextChangedListener(new CustomTextWatcher(((StartWorkoutSingleExerciseViewHolder) holder).mWeightOne,position,VIEW_TYPE_SINGLE,CustomTextWatcher.WEIGHT_ONE));
        }
        else {
            if (holder instanceof StartWorkoutSuperExerciseViewHolder) {
                SuperSet superSet = (SuperSet) mExercise;
                ((StartWorkoutSuperExerciseViewHolder) holder).mSetNumber.setText("Set #" + pos);
                ((StartWorkoutSuperExerciseViewHolder) holder).mSetOne.setHint("Target: "+superSet.getRepsForFirst());
                ((StartWorkoutSuperExerciseViewHolder) holder).mSetTwo.setHint("Target: "+superSet.getRepsForSecond());

                ((StartWorkoutSuperExerciseViewHolder) holder).mSetOne.addTextChangedListener(new CustomTextWatcher(((StartWorkoutSuperExerciseViewHolder) holder).mSetOne,position,VIEW_TYPE_SUPER,CustomTextWatcher.REPS_ONE));
                ((StartWorkoutSuperExerciseViewHolder) holder).mWeightOne.addTextChangedListener(new CustomTextWatcher(((StartWorkoutSuperExerciseViewHolder) holder).mWeightOne,position,VIEW_TYPE_SUPER,CustomTextWatcher.WEIGHT_ONE));
                ((StartWorkoutSuperExerciseViewHolder) holder).mSetTwo.addTextChangedListener(new CustomTextWatcher(((StartWorkoutSuperExerciseViewHolder) holder).mSetTwo,position,VIEW_TYPE_SUPER,CustomTextWatcher.REPS_TWO));
                ((StartWorkoutSuperExerciseViewHolder) holder).mWeightTwo.addTextChangedListener(new CustomTextWatcher(((StartWorkoutSuperExerciseViewHolder) holder).mWeightTwo,position,VIEW_TYPE_SUPER,CustomTextWatcher.WEIGHT_TWO));

            } else if (holder instanceof StartWorkoutTripleExerciseViewHolder) {
                TripleSet triple = (TripleSet) mExercise;
                ((StartWorkoutTripleExerciseViewHolder) holder).mSetNumber.setText("Set #" + pos);
                ((StartWorkoutTripleExerciseViewHolder) holder).mSetOne.setHint("Target: "+triple.getRepsFirstExercise());
                ((StartWorkoutTripleExerciseViewHolder) holder).mSetTwo.setHint("Target: "+triple.getRepsSecondExercise());
                ((StartWorkoutTripleExerciseViewHolder) holder).mSetThree.setHint("Target: "+triple.getRepsThirdExercise());

                ((StartWorkoutTripleExerciseViewHolder) holder).mSetOne.addTextChangedListener(new CustomTextWatcher(((StartWorkoutTripleExerciseViewHolder) holder).mSetOne,position,VIEW_TYPE_TRIPLE,CustomTextWatcher.REPS_ONE));
                ((StartWorkoutTripleExerciseViewHolder) holder).mWeightOne.addTextChangedListener(new CustomTextWatcher(((StartWorkoutTripleExerciseViewHolder) holder).mWeightOne,position,VIEW_TYPE_TRIPLE,CustomTextWatcher.WEIGHT_ONE));
                ((StartWorkoutTripleExerciseViewHolder) holder).mSetTwo.addTextChangedListener(new CustomTextWatcher(((StartWorkoutTripleExerciseViewHolder) holder).mSetTwo,position,VIEW_TYPE_TRIPLE,CustomTextWatcher.REPS_TWO));
                ((StartWorkoutTripleExerciseViewHolder) holder).mWeightTwo.addTextChangedListener(new CustomTextWatcher(((StartWorkoutTripleExerciseViewHolder) holder).mWeightTwo,position,VIEW_TYPE_TRIPLE,CustomTextWatcher.WEIGHT_TWO));
                ((StartWorkoutTripleExerciseViewHolder) holder).mSetThree.addTextChangedListener(new CustomTextWatcher(((StartWorkoutTripleExerciseViewHolder) holder).mSetThree,position,VIEW_TYPE_TRIPLE,CustomTextWatcher.REPS_THREE));
                ((StartWorkoutTripleExerciseViewHolder) holder).mWeightThree.addTextChangedListener(new CustomTextWatcher(((StartWorkoutTripleExerciseViewHolder) holder).mWeightThree,position,VIEW_TYPE_TRIPLE,CustomTextWatcher.WEIGHT_THREE));
            }
        }
    }


    @Override
    public int getItemCount() {
        if(mExercise instanceof SingleExercise)
            return ((SingleExercise) mExercise).getSets();
        if(mExercise instanceof SuperSet)
            return ((SuperSet) mExercise).getSets();
        if(mExercise instanceof TripleSet)
            return ((TripleSet) mExercise).getSets();
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if(mExercise instanceof SingleExercise)
            return VIEW_TYPE_SINGLE;
        if(mExercise instanceof SuperSet)
            return VIEW_TYPE_SUPER;
        if(mExercise instanceof TripleSet)
            return VIEW_TYPE_TRIPLE;
        return 0;
    }

    public class StartWorkoutSingleExerciseViewHolder extends RecyclerView.ViewHolder{

        EditText mSetOne, mWeightOne;
        TextView mSetNumber;

        public StartWorkoutSingleExerciseViewHolder(View itemView) {
            super(itemView);
            mSetOne = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.number_of_reps_one);
            mWeightOne = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.weight_one);
            mSetNumber = (TextView) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.set);
        }
    }

    public class StartWorkoutSuperExerciseViewHolder extends RecyclerView.ViewHolder{
        EditText mSetOne, mWeightOne;
        EditText mSetTwo, mWeightTwo;
        TextView mSetNumber;

        public StartWorkoutSuperExerciseViewHolder(View itemView) {
            super(itemView);
            mSetOne = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.number_of_reps_one);
            mWeightOne = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.weight_one);
            mSetTwo = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.number_of_reps_two);
            mWeightTwo = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.weight_two);
            mSetNumber = (TextView) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.set);


        }
    }

    public class StartWorkoutTripleExerciseViewHolder extends RecyclerView.ViewHolder{
        EditText mSetOne, mWeightOne;
        EditText mSetTwo, mWeightTwo;
        EditText mSetThree, mWeightThree;
        TextView mSetNumber;

        public StartWorkoutTripleExerciseViewHolder(View itemView) {
            super(itemView);
            mSetOne = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.number_of_reps_one);
            mWeightOne = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.weight_one);
            mSetTwo = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.number_of_reps_two);
            mWeightTwo = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.weight_two);
            mSetThree = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.number_of_reps_three);
            mWeightThree = (EditText) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.weight_three);
            mSetNumber = (TextView) itemView.findViewById(my.app.ivonneortega.myfitnessapp.R.id.set);
        }
    }





    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;
        private int mPosition;
        private int mTypeOfHolder;
        private int mTypeOfEditText;
        public static final int REPS_ONE = 2;
        public static final int WEIGHT_ONE = 8;
        public static final int WEIGHT_TWO = 9;
        public static final int WEIGHT_THREE = 10;
        public static final int REPS_TWO = 5;
        public static final int REPS_THREE = 7;

        public CustomTextWatcher(EditText e, int position, int type_of_holder, int type_of_editText) {
            mEditText = e;
            mPosition = position;
            mTypeOfHolder = type_of_holder;
            mTypeOfEditText = type_of_editText;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            List<Sets> mList;
            mList = mExercise.getSetsList();
            Sets set = mList.get(mPosition);
            switch (mTypeOfHolder) {
                case VIEW_TYPE_SINGLE:
                    switch (mTypeOfEditText) {
                        case REPS_ONE:
                            if (mEditText.getText().toString().trim().isEmpty())
                                set.setRepOne(0);
                            else
                               set.setRepOne(Integer.valueOf(mEditText.getText().toString()));
                            break;
                        case WEIGHT_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                               set.setWeightOne(0);
                            } else {
                                set.setRepOne(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                    }
                    break;
                case VIEW_TYPE_SUPER:
                    switch (mTypeOfEditText) {
                        case REPS_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setRepOne(0);
                            } else {
                                set.setRepOne(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                        case REPS_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                               set.setRepTwo(0);
                            } else {
                                set.setRepTwo(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;

                        case WEIGHT_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setWeightOne(0);
                            } else {
                                set.setWeightOne(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                        case WEIGHT_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setWeightTwo(0);
                            } else {
                                set.setWeightTwo(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;


                    }
                    break;

                case VIEW_TYPE_TRIPLE:
                    switch (mTypeOfEditText) {
                        case REPS_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setRepOne(0);
                            } else {
                               set.setRepOne(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                        case REPS_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setRepTwo(0);
                            } else {
                                set.setRepTwo(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;

                        case WEIGHT_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setWeightOne(0);
                            } else {
                                set.setWeightOne(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                        case WEIGHT_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setWeightTwo(0);
                            } else {
                                set.setWeightTwo(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                        case REPS_THREE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setRepThree(0);
                            } else {
                                set.setRepThree(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                        case WEIGHT_THREE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                set.setWeightThree(0);
                            } else {
                                set.setWeightThree(Integer.valueOf(mEditText.getText().toString()));
                            }
                            break;
                    }
            }
                mList.set(mPosition,set);
                mListener.updateExerciseSetList(mList,mParentAdapterPosition);


        }

        public void afterTextChanged(Editable s) {
        }
    }

    public interface updateExercise
    {
        void updateExerciseSetList(List<Sets> mList, int position);
    }


}
