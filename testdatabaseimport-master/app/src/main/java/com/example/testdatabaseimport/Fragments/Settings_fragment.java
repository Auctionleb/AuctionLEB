package com.example.testdatabaseimport.Fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.testdatabaseimport.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class Settings_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        MaterialButton help = view.findViewById(R.id.help);

        // to make the text appear on the second line and change the size of it
        String text = "Help \nFAQ, Contact us, Privacy policy";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new RelativeSizeSpan(0.9f), 5, text.length(), 0);
        help.setText(spannableString);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to go from setting fragment to help fragment
                Fragment fragment = new Help_Fragment();
                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });


        return view;

    }


}
