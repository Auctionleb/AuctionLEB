package com.example.auctionleb.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.auctionleb.Payment;
import com.example.auctionleb.R;
import com.example.auctionleb.SignInRegister.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_fragment extends Fragment {
    private static final String TAG = "Profile";
    Button addFunds, Logout;
    ImageView profilePicture;
    TextView dataName, dataEmail, dataPhone, dataBalance;
    DatabaseReference myRef;
    FirebaseUser user;
    String userID;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        addFunds = v.findViewById(R.id.addFunds);
        dataName = v.findViewById(R.id.tv_name);
        dataEmail = v.findViewById(R.id.tv_email);
        dataPhone = v.findViewById(R.id.tv_phone);
        profilePicture = v.findViewById(R.id.tv_icon);
        Logout = v.findViewById(R.id.exit);
        dataBalance=v.findViewById(R.id.amount);

/**
 * Function to retrieve the database from firebase for each user
 */
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        userID = user.getUid();

        myRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("Users").child(userID).child("FirstName").getValue();
                String email = (String) dataSnapshot.child("Users").child(userID).child("Email").getValue();
                String phone = (String) dataSnapshot.child("Users").child(userID).child("PhNumber").getValue();
                String balance = (String) dataSnapshot.child("Users").child(userID).child("Balance").getValue();
                dataName.setText(name);
                dataEmail.setText(email);
                dataPhone.setText(phone);
                dataBalance.setText(balance + "$");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

/**
 * To Add Funds On My Account
 */
        addFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Payment.class);
                startActivity(intent);

            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);

            }
        });
        return v;
    }// end of OnCreateView
}






