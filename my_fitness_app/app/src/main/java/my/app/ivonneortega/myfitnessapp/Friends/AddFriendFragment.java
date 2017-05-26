package my.app.ivonneortega.myfitnessapp.Friends;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import my.app.ivonneortega.myfitnessapp.Data.Friend;
import my.app.ivonneortega.myfitnessapp.Data.User;
import my.app.ivonneortega.myfitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddFriendFragment extends Fragment {

    private AddFriendInterface mListener;
    private SearchView mSearch;
    private TextView mFriendName;
    private Friend mFriend;
    private View mFriendLayout;

    public AddFriendFragment() {
        // Required empty public constructor
    }



    public static AddFriendFragment newInstance() {
        AddFriendFragment fragment = new AddFriendFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false);
    }


    public void onButtonPressed(Friend friend) {
        if (mListener != null) {
            mListener.addFriend(friend);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearch = (SearchView) view.findViewById(R.id.seatch);
        mFriendName = (TextView) view.findViewById(R.id.friend_name);
        mFriendName.setVisibility(View.GONE);
        mFriendLayout = view.findViewById(R.id.friends_layout);
        mFriendLayout.setVisibility(View.GONE);

        mSearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFriendLayout.setVisibility(View.GONE);
                mFriendName.setVisibility(View.GONE);
            }
        });

        mFriendName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addFriend(mFriend);
            }
        });

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                InputMethodManager imm = (InputMethodManager)mSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);


                getFriendId(query);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    public void getFriendId(String query)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        final User[] user = {new User()};


        myRef.orderByChild("email").equalTo(query).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    boolean exit=true;
                    int counter = 1;
                    String key = dataSnapshot.getValue().toString();
                    while (exit)
                    {
                        if(key.charAt(counter)=='=')
                            exit = false;
                        else
                            counter++;
                    }

                    key = key.substring(1,counter);

                    user[0] = dataSnapshot.child(key).getValue(User.class);
                    mFriend = new Friend();
                    mFriend.setName(user[0].getName());
                    mFriend.setFriendId(user[0].getId());
                    mFriend.setEmail(user[0].getEmail());

                    mFriendName.setVisibility(View.VISIBLE);
                    mFriendLayout.setVisibility(View.VISIBLE);
                    mFriendName.setText(mFriend.getName());
                    mSearch.onActionViewCollapsed();

                }
                else
                {
                    mFriendName.setVisibility(View.VISIBLE);
                    mFriendName.setText("No user found with that email");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddFriendInterface) {
            mListener = (AddFriendInterface) context;
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


    public interface AddFriendInterface {
        // TODO: Update argument type and name
        void addFriend(Friend friend);
    }
}
