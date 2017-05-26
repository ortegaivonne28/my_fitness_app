package my.app.ivonneortega.myfitnessapp.Friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import my.app.ivonneortega.myfitnessapp.Data.Workout;
import my.app.ivonneortega.myfitnessapp.FitnessDBHelper;

import java.util.List;


public class WorkoutToChallenge extends Fragment implements FriendsRecyclerViewAdapter.WorkoutChallengeInRecyclerViewInterface {


    private WorkoutToChallengeInterface mListener;
    private RecyclerView mRecyclerView;
    private FriendsRecyclerViewAdapter mAdapter;
    private String mFriendId;

    public static final String FRIEND_ID = "friend";

    public WorkoutToChallenge() {
        // Required empty public constructor
    }


    public static WorkoutToChallenge newInstance(String friendId) {
        WorkoutToChallenge fragment = new WorkoutToChallenge();
        Bundle args = new Bundle();
        args.putString(FRIEND_ID,friendId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFriendId = getArguments().getString(FRIEND_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(my.app.ivonneortega.myfitnessapp.R.layout.fragment_workout_to_challenge, container, false);
    }

    public void onButtonPressed(Workout workut) {
        if (mListener != null) {
            mListener.insertChallenge(workut,mFriendId);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(my.app.ivonneortega.myfitnessapp.R.id.recycler_view);
        List<Workout> workouts = FitnessDBHelper.getInstance(mRecyclerView.getContext()).getWorkoutsThatDontBelogToRoutine();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));

        mAdapter = new FriendsRecyclerViewAdapter(null,null,workouts,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WorkoutToChallengeInterface) {
            mListener = (WorkoutToChallengeInterface) context;
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
    public void clickedOnWorkoutToChallengeFriend(Workout workout) {
        mListener.insertChallenge(workout,mFriendId);
    }


    public interface WorkoutToChallengeInterface {
        // TODO: Update argument type and name
        void insertChallenge(Workout workout, String friendId);
    }
}
