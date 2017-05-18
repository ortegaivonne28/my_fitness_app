package com.example.ivonneortega.myfitnessapp.AddUserInformation;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.myfitnessapp.Data.Exercise;
import com.example.ivonneortega.myfitnessapp.Data.SingleExercise;
import com.example.ivonneortega.myfitnessapp.Data.SuperSet;
import com.example.ivonneortega.myfitnessapp.Data.TripleSet;
import com.example.ivonneortega.myfitnessapp.Data.Week;
import com.example.ivonneortega.myfitnessapp.R;

import java.util.List;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by ivonneortega on 5/17/17.
 */

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter
implements AddWeekWorkoutFragment.getList{

    private List<Exercise> mList;
    public static final int VIEW_TYPE_SINGLE = 1;
    public static final int VIEW_TYPE_SUPER = 2;
    public static final int VIEW_TYPE_TRIPLE = 3;
    public static final int VIEW_TYPE_ADD = 4;
    private RecyclerView myRecyclerView;

    public ExerciseRecyclerViewAdapter(List<Exercise> list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)
        {
            case VIEW_TYPE_SINGLE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.single_exercise, parent, false);
                return new SingleExerciseViewHolder(view);
            case VIEW_TYPE_ADD:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.add_more, parent, false);
                return new SingleExerciseViewHolder(view1);
            case VIEW_TYPE_SUPER:
                View view2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.superset, parent, false);
                return new SuperSetViewHolder(view2);
            case VIEW_TYPE_TRIPLE:
                View view3 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.tripleset, parent, false);
                return new TripleSetViewHolder(view3);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(position == mList.size())
        {

            if(holder instanceof SingleExerciseViewHolder)
            {
                ((SingleExerciseViewHolder) holder).mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mList.size()<=0 ||( mList.size()>0 && isExerciseNotEmpty(mList.get(mList.size()-1)))) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(((SingleExerciseViewHolder) holder).mTextView.getContext());
                            builder.setTitle("Type of exercise")
                                    .setItems(R.array.alertDialogExercises, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case 0:
                                                    mList.add(new SingleExercise(0, 0));
                                                    notifyItemInserted(mList.size() - 1);
                                                    break;
                                                case 1:
                                                    mList.add(new SuperSet());
                                                    notifyItemInserted(mList.size() - 1);
                                                    break;
                                                case 2:
                                                    mList.add(new TripleSet());
                                                    notifyItemInserted(mList.size() - 1);
                                            }
                                        }
                                    });
                            builder.create();
                            builder.show();
                        }
                        else
                            Toast.makeText(((SingleExerciseViewHolder) holder).mTextView.getContext(), "Complete the previous exercise", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else
        {
            if(holder instanceof SingleExerciseViewHolder)
            {
                ((SingleExerciseViewHolder) holder).mExerciseName.addTextChangedListener(new CustomTextWatcher(((SingleExerciseViewHolder) holder).mExerciseName,holder.getAdapterPosition(),VIEW_TYPE_SINGLE,CustomTextWatcher.EXERCISE_ONE));
                ((SingleExerciseViewHolder) holder).mReps.addTextChangedListener(new CustomTextWatcher(((SingleExerciseViewHolder) holder).mReps,holder.getAdapterPosition(),VIEW_TYPE_SINGLE,CustomTextWatcher.REPS_ONE));
                ((SingleExerciseViewHolder) holder).mSets.addTextChangedListener(new CustomTextWatcher(((SingleExerciseViewHolder) holder).mSets,holder.getAdapterPosition(),VIEW_TYPE_SINGLE,CustomTextWatcher.SETS));

                SingleExercise singleExercise = (SingleExercise) mList.get(position);

                if (singleExercise.getName()!=null)
                    ((SingleExerciseViewHolder) holder).mExerciseName.setText(singleExercise.getName());
                if(singleExercise.getReps()!=0)
                    ((SingleExerciseViewHolder) holder).mReps.setText(String.valueOf(singleExercise.getReps()));
                if(singleExercise.getSets()!=0)
                    ((SingleExerciseViewHolder) holder).mSets.setText(String.valueOf(singleExercise.getSets()));
            }
            if(holder instanceof SuperSetViewHolder)
            {
                ((SuperSetViewHolder) holder).mExerciseOneName.addTextChangedListener(new CustomTextWatcher(((SuperSetViewHolder) holder).mExerciseOneName,holder.getAdapterPosition(),VIEW_TYPE_SUPER,CustomTextWatcher.EXERCISE_ONE));
                ((SuperSetViewHolder) holder).mExerciseTwoName.addTextChangedListener(new CustomTextWatcher(((SuperSetViewHolder) holder).mExerciseTwoName,holder.getAdapterPosition(),VIEW_TYPE_SUPER,CustomTextWatcher.EXERCISE_TWO));
                ((SuperSetViewHolder) holder).mSets.addTextChangedListener(new CustomTextWatcher(((SuperSetViewHolder) holder).mSets,holder.getAdapterPosition(),VIEW_TYPE_SUPER,CustomTextWatcher.SETS));
                ((SuperSetViewHolder) holder).mRepsOne.addTextChangedListener(new CustomTextWatcher(((SuperSetViewHolder) holder).mRepsOne,holder.getAdapterPosition(),VIEW_TYPE_SUPER,CustomTextWatcher.REPS_ONE));
                ((SuperSetViewHolder) holder).mRepsTwo.addTextChangedListener(new CustomTextWatcher(((SuperSetViewHolder) holder).mRepsTwo,holder.getAdapterPosition(),VIEW_TYPE_SUPER,CustomTextWatcher.REPS_TWO));

                SuperSet superSet = (SuperSet) mList.get(position);

                if(superSet.getNameOne()!=null)
                    ((SuperSetViewHolder) holder).mExerciseOneName.setText(superSet.getNameOne());
                if(superSet.getNameTwo()!=null)
                    ((SuperSetViewHolder) holder).mExerciseTwoName.setText(superSet.getNameTwo());
                if(superSet.getRepsForFirst()!=0)
                    ((SuperSetViewHolder) holder).mRepsOne.setText(String.valueOf(superSet.getRepsForFirst()));
                if(superSet.getRepsForSecond()!=0)
                    ((SuperSetViewHolder) holder).mRepsTwo.setText(String.valueOf(superSet.getRepsForSecond()));
                if(superSet.getSets()!=0)
                    ((SuperSetViewHolder) holder).mSets.setText(String.valueOf(superSet.getSets()));

            }
            if(holder instanceof TripleSetViewHolder)
            {
                ((TripleSetViewHolder) holder).mExerciseOneName.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mExerciseOneName,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.EXERCISE_ONE));
                ((TripleSetViewHolder) holder).mExerciseTwoName.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mExerciseTwoName,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.EXERCISE_TWO));
                ((TripleSetViewHolder) holder).mExerciseThreeName.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mExerciseThreeName,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.EXERCISE_THREE));
                ((TripleSetViewHolder) holder).mRepsOne.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mRepsOne,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.REPS_ONE));
                ((TripleSetViewHolder) holder).mRepsTwo.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mRepsTwo,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.REPS_TWO));
                ((TripleSetViewHolder) holder).mRepsThree.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mRepsThree,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.REPS_THREE));
                ((TripleSetViewHolder) holder).mSets.addTextChangedListener(new CustomTextWatcher(((TripleSetViewHolder) holder).mSets,holder.getAdapterPosition(),VIEW_TYPE_TRIPLE,CustomTextWatcher.SETS));

                TripleSet tripleSet = (TripleSet) mList.get(position);

                if(tripleSet.getNameOne()!=null)
                    ((TripleSetViewHolder) holder).mExerciseOneName.setText(tripleSet.getNameOne());
                if(tripleSet.getNameTwo()!=null)
                    ((TripleSetViewHolder) holder).mExerciseTwoName.setText(tripleSet.getNameTwo());
                if(tripleSet.getNameThree()!=null)
                    ((TripleSetViewHolder) holder).mExerciseThreeName.setText(tripleSet.getNameTwo());

                if(tripleSet.getRepsFirstExercise()!=0)
                    ((TripleSetViewHolder) holder).mRepsOne.setText(String.valueOf(tripleSet.getRepsFirstExercise()));
                if(tripleSet.getRepsSecondExercise()!=0)
                    ((TripleSetViewHolder) holder).mRepsTwo.setText(String.valueOf(tripleSet.getRepsSecondExercise()));
                if(tripleSet.getRepsThirdExercise()!=0)
                    ((TripleSetViewHolder) holder).mRepsThree.setText(String.valueOf(tripleSet.getRepsSecondExercise()));
                if(tripleSet.getSets()!=0)
                    ((TripleSetViewHolder) holder).mSets.setText(String.valueOf(tripleSet.getSets()));

            }
        }

    }


    public boolean isExerciseNotEmpty(Exercise exercise)
    {
        if(exercise!=null)
        {
            if(exercise instanceof SingleExercise)
            {
                if(exercise.getName()!=null && ((SingleExercise) exercise).getReps()!=0 && ((SingleExercise) exercise).getSets()!=0)
                    return true;
            }
            if(exercise instanceof SuperSet)
            {
                Log.d(TAG, "isExerciseNotEmpty: "+((SuperSet) exercise).getNameOne()+" "+((SuperSet) exercise).getNameTwo()+" "+((SuperSet) exercise).getRepsForFirst()+" "+((SuperSet) exercise).getRepsForSecond()+" "+((SuperSet) exercise).getSets());
                if(((SuperSet) exercise).getNameOne()!=null && ((SuperSet) exercise).getNameTwo()!=null &&
                        ((SuperSet) exercise).getSets()!=0 && ((SuperSet) exercise).getRepsForFirst()!=0 && ((SuperSet) exercise).getRepsForSecond()!=0)
                    return true;
            }
            if(exercise instanceof TripleSet)
            {
                if(((TripleSet) exercise).getNameOne()!=null && ((TripleSet) exercise).getNameTwo()!=null && ((TripleSet) exercise).getNameThree()!=null &&
                        ((TripleSet) exercise).getSets()!=0 && ((TripleSet) exercise).getRepsFirstExercise()!=0 && ((TripleSet) exercise).getRepsSecondExercise()!=0
                        && ((TripleSet) exercise).getRepsThirdExercise()!=0)
                    return true;

            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public List<Exercise> getList() {
        return mList;
    }

    public class SingleExerciseViewHolder extends RecyclerView.ViewHolder{

        EditText mExerciseName,mSets,mReps;
        TextView mTextView;


        public SingleExerciseViewHolder(View itemView) {
            super(itemView);
            mExerciseName = (EditText) itemView.findViewById(R.id.exercise_name);
            mSets = (EditText) itemView.findViewById(R.id.number_of_sets);
            mReps = (EditText) itemView.findViewById(R.id.number_of_reps);
            mTextView = (TextView) itemView.findViewById(R.id.add_exercise);

        }
    }

    public class SuperSetViewHolder extends RecyclerView.ViewHolder{

        EditText mExerciseOneName, mExerciseTwoName, mSets,mRepsOne,mRepsTwo;


        public SuperSetViewHolder(View itemView) {
            super(itemView);
            mExerciseOneName = (EditText) itemView.findViewById(R.id.exercise_name_one);
            mExerciseTwoName = (EditText) itemView.findViewById(R.id.exercise_name_two);
            mSets = (EditText) itemView.findViewById(R.id.number_of_sets);
            mRepsOne = (EditText) itemView.findViewById(R.id.number_of_reps_one);
            mRepsTwo = (EditText) itemView.findViewById(R.id.number_of_reps_two);

        }
    }

    public class TripleSetViewHolder extends RecyclerView.ViewHolder{

        EditText mExerciseOneName, mExerciseTwoName,mExerciseThreeName, mSets,mRepsOne,mRepsTwo, mRepsThree;


        public TripleSetViewHolder(View itemView) {
            super(itemView);
            mExerciseOneName = (EditText) itemView.findViewById(R.id.exercise_name_one);
            mExerciseTwoName = (EditText) itemView.findViewById(R.id.exercise_name_two);
            mSets = (EditText) itemView.findViewById(R.id.number_of_sets);
            mRepsOne = (EditText) itemView.findViewById(R.id.number_of_reps_one);
            mRepsTwo = (EditText) itemView.findViewById(R.id.number_of_reps_two);
            mExerciseThreeName = (EditText) itemView.findViewById(R.id.exercise_name_three);
            mRepsThree = (EditText) itemView.findViewById(R.id.number_of_reps_three);

        }
    }



    @Override
    public int getItemViewType(int position) {
        if(position==mList.size())
            return VIEW_TYPE_ADD;
        if(mList.get(position) instanceof SingleExercise)
            return VIEW_TYPE_SINGLE;
        if(mList.get(position) instanceof SuperSet)
            return VIEW_TYPE_SUPER;
        if(mList.get(position) instanceof TripleSet)
            return VIEW_TYPE_TRIPLE;
        return 0;
    }

    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;
        private int mPosition;
        private int mTypeOfHolder;
        private int mTypeOfEditText;
        public static final int EXERCISE_ONE = 1;
        public static final int REPS_ONE = 2;
        public static final int SETS = 3;
        public static final int EXERCISE_TWO = 4;
        public static final int REPS_TWO = 5;
        public static final int EXERCISE_THREE = 6;
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
            switch (mTypeOfHolder) {
                case VIEW_TYPE_SINGLE:
                    switch (mTypeOfEditText) {
                        case EXERCISE_ONE:
                            if (mEditText.getText().toString().trim().isEmpty())
                                mList.get(mPosition).setName(null);
                            else
                                mList.get(mPosition).setName(s.toString());
                            break;
                        case REPS_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SingleExercise)
                                    ((SingleExercise) mList.get(mPosition)).setReps(0);
                            } else {
                                if (mList.get(mPosition) instanceof SingleExercise)
                                    ((SingleExercise) mList.get(mPosition)).setReps(Integer.valueOf(s.toString()));
                            }
                            break;
                        case SETS:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SingleExercise)
                                    ((SingleExercise) mList.get(mPosition)).setSets(0);
                            } else {
                                if (mList.get(mPosition) instanceof SingleExercise)
                                    ((SingleExercise) mList.get(mPosition)).setSets(Integer.valueOf(s.toString()));
                            }
                            break;
                    }
                    break;
                case VIEW_TYPE_SUPER:
                    switch (mTypeOfEditText) {
                        case EXERCISE_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setNameOne(null);
                            } else {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setNameOne(s.toString());
                            }
                            break;
                        case REPS_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setRepsForFirst(0);
                            } else {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setRepsForFirst(Integer.valueOf(s.toString()));
                            }
                            break;

                        case SETS:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setSets(0);
                            } else {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setSets(Integer.valueOf(s.toString()));
                            }
                            break;

                        case EXERCISE_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setNameTwo(null);
                            } else {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setNameTwo(s.toString());
                            }
                            break;

                        case REPS_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setRepsForSecond(0);
                            } else {
                                if (mList.get(mPosition) instanceof SuperSet)
                                    ((SuperSet) mList.get(mPosition)).setRepsForSecond(Integer.valueOf(s.toString()));
                            }
                            break;
                    }
                    break;

                case VIEW_TYPE_TRIPLE:
                    switch (mTypeOfEditText) {
                        case EXERCISE_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setNameOne(null);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setNameOne(s.toString());
                            }
                            break;
                        case REPS_ONE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setRepsFirstExercise(0);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setRepsFirstExercise(Integer.valueOf(s.toString()));
                            }
                            break;

                        case SETS:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setSets(0);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setSets(Integer.valueOf(s.toString()));
                            }
                            break;

                        case EXERCISE_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setNameTwo(null);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setNameTwo(s.toString());
                            }
                            break;

                        case REPS_TWO:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setRepsSecondExercise(0);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setRepsSecondExercise(Integer.valueOf(s.toString()));
                            }

                            break;

                        case REPS_THREE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setRepsThirdExercise(0);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setRepsThirdExercise(Integer.valueOf(s.toString()));
                            }
                            break;

                        case EXERCISE_THREE:
                            if (mEditText.getText().toString().trim().isEmpty()) {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setNameThree(null);
                            } else {
                                if (mList.get(mPosition) instanceof TripleSet)
                                    ((TripleSet) mList.get(mPosition)).setNameThree(s.toString());
                            }
                            break;
                    }
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

}
