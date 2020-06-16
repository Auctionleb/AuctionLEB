package com.example.auctionleb.Fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.testdatabaseimport.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

public class Pressed_item_fragment extends Fragment {

    private DatabaseReference databaseReference;
    private TextView mTitle, mModel, mPrice, mYear, mCondition, mMilage, mTransmition, mEndDate, mDescription;
    private ImageView mImage;
    private Button mItem_bid;
    private EditText mBid, Bidamount;
    private String currentprice;
    private LottieAnimationView lottieAnimationView;

    public Pressed_item_fragment() {
    }

    private String itemID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pressed_item, container, false);
        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mTitle = view.findViewById(R.id.Pressed_Item_title);
        mModel = view.findViewById(R.id.Pressed_Item_model);
        mPrice = view.findViewById(R.id.Pressed_Item_price);
        mYear = view.findViewById(R.id.Pressed_Item_year);
        mImage = view.findViewById(R.id.pressed_Item_image);
        mCondition = view.findViewById(R.id.Pressed_Item_condition);
        mMilage = view.findViewById(R.id.Pressed_Item_milage);
        mTransmition = view.findViewById(R.id.Pressed_Item_transmition);
        mEndDate = view.findViewById(R.id.time_left);
        mDescription = view.findViewById(R.id.Pressed_Item_description);
        mItem_bid = view.findViewById(R.id.Pressed_Item_Addbid);
        lottieAnimationView = view.findViewById(R.id.done_SplashScreen);

        Bundle bundle = getArguments();
        itemID = (String.valueOf(bundle.getString("ID")));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ads").child("cars").child(itemID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String title = dataSnapshot.child("title").getValue().toString();
                    String model = dataSnapshot.child("model").getValue().toString();
                    String price = dataSnapshot.child("price").getValue().toString();
                    String year = dataSnapshot.child("year").getValue().toString();
                    String image = dataSnapshot.child("imageURL").getValue().toString();
                    String condition = dataSnapshot.child("condition").getValue().toString();
                    String milage = dataSnapshot.child("milage").getValue().toString();
                    String transmition = dataSnapshot.child("transmition").getValue().toString();
                    String endDate = dataSnapshot.child("endDate").getValue().toString();
                    String description = dataSnapshot.child("description").getValue().toString();
                    currentprice = price;

                    mTitle.setText(title);
                    mModel.setText(model);
                    mPrice.setText("$" + price);
                    mYear.setText(year);
                    mCondition.setText(condition);
                    mMilage.setText(milage);
                    mTransmition.setText(transmition);
                    mEndDate.setText(endDate);
                    mDescription.setText(description);
                    Picasso.get().load(image).into(mImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mItem_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View view = inflater.inflate(R.layout.layout_dialog, null);
                new MaterialAlertDialogBuilder(getContext())
                        .setView(view)
                        .setTitle("Add your price")
                        .setMessage("Notice: Once you submit the price you can't change it to a lower price")
                        .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bidamount = view.findViewById(R.id.bid_amount);
                                String amount = (String) Bidamount.getText().toString().trim();

                                if (amount.isEmpty()) {
                                    Toasty.error(getActivity(), "Enter a price before you submit", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (Integer.parseInt(amount) <= Integer.parseInt(currentprice)) {
                                    Toasty.error(getActivity(), Html.fromHtml("You must enter a <b>Higher Price</b> than the current"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String BidAmount = Bidamount.getText().toString();
                                FirebaseDatabase.getInstance().getReference().child("ads").child("cars").child(itemID).child("price").setValue(BidAmount);
                                FirebaseDatabase.getInstance().getReference().child("ads").child("cars").child(itemID).child("higestBider").setValue(currentuser);

                                new MaterialAlertDialogBuilder(getContext())
                                        .setMessage("The price is updated\nYou are now the highest bidder\nIf you are the highest bidder until the time run down you'll win the bid")
                                        .setPositiveButton("Ok", null)
                                        .show();

                            }
                        }).show();
            }
        });

        return view;
    }

}