package com.example.testdatabaseimport.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testdatabaseimport.R;
import com.example.testdatabaseimport.adapters.VerticalRecyclerViewAdapter;
import com.example.testdatabaseimport.models.HorizontalModel;
import com.example.testdatabaseimport.models.VerticalModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class Home_fragment extends Fragment {
    private String[] listitem;


    private RecyclerView rvVertical;
    private FloatingActionButton mFloatingActionButton;

    private ArrayList<VerticalModel> mArrayList = new ArrayList<>();
    private VerticalRecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //to view and inflate the style of the page
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvVertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VerticalRecyclerViewAdapter recyclerViewAdapter = new VerticalRecyclerViewAdapter(getActivity(), mArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
        setDataOnVerticalRecyclerView();

// to hide the floating action button
        mFloatingActionButton = view.findViewById(R.id.fab);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // to make the text appear on the second line and change the size of it
        final String bidtitle = "Add Bid \nChoose your category";
        final SpannableString spannableString = new SpannableString(bidtitle);
        spannableString.setSpan(new RelativeSizeSpan(0.8f), 8, bidtitle.length(), 0);

        listitem = new String[]{"Cars", "mobile", "buildings", "computers", "screens", "Cars", "mobile", "buildings", "computers", "screens", "Cars", "mobile", "buildings", "computers", "screens"};

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Category");
                builder.setItems(listitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment selectedFragment = new Fragment();
                        Bundle bundle = new Bundle();
                        switch (listitem[which]) {
                            case "Cars":
                                selectedFragment = new Car_choice_Fragment();
                                break;
                            default:
                                selectedFragment= new Placebid_fragment();
                                break;
                        }
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


//        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // to go from home fragment to help place a bid fragment
//                Fragment fragment = new Placebid_fragment();
//                FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }
//        });
    }


    private void setDataOnVerticalRecyclerView() {
        for (int i = 1; i <= 20; i++) {

            VerticalModel mVerticalModel = new VerticalModel();

            mVerticalModel.setTitle("Title " + i);

            ArrayList<HorizontalModel> arrayList = new ArrayList<>();

            for (int j = 0; j <= 30; j++) {
                HorizontalModel mHorizontalModel = new HorizontalModel();
                mHorizontalModel.setDescription("Description : " + j);
                mHorizontalModel.setName("Name : " + j);
                arrayList.add(mHorizontalModel);
            }

            mVerticalModel.setArrayList(arrayList);

            mArrayList.add(mVerticalModel);
        }
//        mAdapter.notifyDataSetChanged();
    }


}
