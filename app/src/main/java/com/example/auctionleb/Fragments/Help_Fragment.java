package com.example.auctionleb.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.testdatabaseimport.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class Help_Fragment extends Fragment {
    private MaterialButton help;
    private MaterialToolbar toolbar_help;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        toolbar_help = view.findViewById(R.id.toolbar_help);
        toolbar_help.setTitle("Help");

        //TODO
        //ask how to set support ActionBar in Fragment to set DisplayHomeAsUpEnabled
        //Sample
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        return view;
    }
}
