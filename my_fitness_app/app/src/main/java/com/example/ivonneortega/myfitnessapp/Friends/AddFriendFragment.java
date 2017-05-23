package com.example.ivonneortega.myfitnessapp.Friends;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.ivonneortega.myfitnessapp.AddUserInformation.AddUserInformationActivity;
import com.example.ivonneortega.myfitnessapp.Data.User;
import com.example.ivonneortega.myfitnessapp.LoginActivity;
import com.example.ivonneortega.myfitnessapp.MainActivity;
import com.example.ivonneortega.myfitnessapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class AddFriendFragment extends Fragment {

    private AddFriendInterface mListener;
    private SearchView mSearch;
    private TextView mFriendName;

    public AddFriendFragment() {
        // Required empty public constructor
    }



    public static AddFriendFragment newInstance(String param1, String param2) {
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


    public void onButtonPressed(String id) {
        if (mListener != null) {
            mListener.addFriend(id);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearch = (SearchView) view.findViewById(R.id.seatch);
        mFriendName = (TextView) view.findViewById(R.id.friend_name);

        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)mSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearch.getWindowToken(), 0);


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("users");

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

                            User friendUser = dataSnapshot.child(key).getValue(User.class);



                    }
                ]}

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    });


                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
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
        void addFriend(String id);
    }
}
