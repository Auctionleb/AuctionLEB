package com.example.auctionleb.Fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.auctionleb.R;
import com.example.auctionleb.adapters.CarsViewholder;
import com.example.auctionleb.models.CarsInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home_fragment extends Fragment {
    private String[] listitem;
    private FloatingActionButton mFloatingActionButton;
    private ListView listView;
    private RecyclerView Items_recyclerview;
    private DatabaseReference databaseReference;
    private TextView clock;
    private View homeview;
    private FragmentActivity fragmentActivity;

    public Home_fragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        homeview = inflater.inflate(R.layout.fragment_home, container, false);


//        clock = homeview.findViewById(R.id.Item_timeleft);

        mFloatingActionButton = homeview.findViewById(R.id.fab);
//        listView = view.findViewById(R.id.listview);
        Items_recyclerview = homeview.findViewById(R.id.recyclerView);
        Items_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ads").child("cars");

        //////////////////////////////////////
        // to hide the floating action button
        /////////////////////////////////////
        Items_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        return homeview;
    }


    /////////////////////////////////////////////////////
    // Add Items to the recycler view in the homepage ///
    /////////////////////////////////////////////////////

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<CarsInfo>()
                .setQuery(databaseReference, CarsInfo.class).build();

        FirebaseRecyclerAdapter<CarsInfo, CarsViewholder> adapter =
                new FirebaseRecyclerAdapter<CarsInfo, CarsViewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final CarsViewholder carsViewholder, final int i, @NonNull CarsInfo carsInfo) {
                        String itemsID = getRef(i).getKey();
                        databaseReference.child(itemsID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String title = dataSnapshot.child("title").getValue().toString();
                                    String model = dataSnapshot.child("model").getValue().toString();
                                    String price = dataSnapshot.child("price").getValue().toString();
                                    String year = dataSnapshot.child("year").getValue().toString();
                                    String image = dataSnapshot.child("imageURL").getValue().toString();

                                    carsViewholder.title.setText(title);
                                    carsViewholder.model.setText(model);
                                    carsViewholder.price.setText("$" + price);
                                    carsViewholder.year.setText(year);
                                    Picasso.get().load(image).into(carsViewholder.imageURL);

                                    carsViewholder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String view_item_id = getRef(i).getKey();
                                            // to send the item id from one frgament to another
                                            Bundle bundle = new Bundle();
                                            bundle.putString("ID",view_item_id);


                                            // to go to the item fragment
                                            Fragment fragment = new Pressed_item_fragment();
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragment.setArguments(bundle);
                                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                                            fragmentTransaction.commit();
                                        }
                                    });
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CarsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
                        CarsViewholder carsViewholder = new CarsViewholder(view);

                        return carsViewholder;

                    }
                };
        Items_recyclerview.setAdapter(adapter);
        adapter.startListening();
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
                Fragment selectedFragment = new Fragment();
                selectedFragment = new Placebid_fragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();

            }
        });


//        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context;
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Category");
//                builder.setItems(listitem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Fragment selectedFragment = new Fragment();
//                        Bundle bundle = new Bundle();
//                        switch (listitem[which]) {
//                            case "Cars":
//                                selectedFragment = new Car_choice_Fragment();
//                                break;
//                            default:
//                                selectedFragment= new Placebid_fragment();
//                                break;
//                        }
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });


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

}
