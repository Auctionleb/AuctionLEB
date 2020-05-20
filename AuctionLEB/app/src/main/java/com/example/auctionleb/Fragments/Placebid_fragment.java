package com.example.auctionleb.Fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.auctionleb.Bids;
import com.example.auctionleb.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class Placebid_fragment extends Fragment{

    private int getyear = Calendar.getInstance().get(Calendar.YEAR);
    private TextInputEditText mTitle, mCondition, mDecription, mModel, mBidStartingPrice, mNowPrice; // in case we need them
    private TextInputLayout mTitle_layout, mCondition_layout, mDecription_layout, mModel_layout, mBidStartingPrice_layout, mNowPrice_layout, mError;

    //for images
    private ImageButton mImageButton;
    private LinearLayout mLinearLayout;
    private ImageView mImageView;
    private int PICK_IMAGE_MULTIPLE = 1;
    private String imageEncoded;
    private List<String> imagesEncodedList;

    //  private Spinner spinnerDialog is button;
    private MaterialButton mButton, mSubmit, mYear;
    //category
    //TODO remove when done
    private String[] listitem;
    // years
    private String[] years = new String[41];

    //Firebase
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placebid, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Bidssss");

        ////////// buttons//////////////
        {
            mTitle = view.findViewById(R.id.title);
            mSubmit = view.findViewById(R.id.submit);
            mYear = view.findViewById(R.id.year);
            mImageButton = view.findViewById(R.id.addimage);
            mButton = view.findViewById(R.id.test);
            mLinearLayout = view.findViewById(R.id.linearlayout);
            mImageView = view.findViewById(R.id.imageView);
            mCondition = view.findViewById(R.id.condition);
            mModel = view.findViewById(R.id.model);
            mDecription = view.findViewById(R.id.decription);
            mBidStartingPrice = view.findViewById(R.id.bidStartingPrice);
            mNowPrice = view.findViewById(R.id.nowprice);
        }

        ////////////layouts//////////////
        {
        mTitle_layout = view.findViewById(R.id.title_layout);
        mCondition_layout = view.findViewById(R.id.condition_layout);
        mModel_layout = view.findViewById(R.id.model_layout);
        mDecription_layout = view.findViewById(R.id.description_layout);
        mBidStartingPrice_layout = view.findViewById(R.id.bidStartingPrice_layout);
        mNowPrice_layout = view.findViewById(R.id.pricenow_layout);

        mError = view.findViewById(R.id.error);}

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBid();
            }
        });

        //TODO remove this category and add it to FAB
        //category
        listitem = new String[]{"Cars", "mobile", "buildings", "computers", "screens", "Cars", "mobile", "buildings", "computers", "screens", "Cars", "mobile", "buildings", "computers", "screens"};

        // put years in years String
        int lastYear; // last year is the last year in the list
        for (int i = 0; i < years.length; i++) {
            lastYear = getyear - i;
            years[i] = Integer.toString(lastYear);

            Log.e("This is the first year", String.valueOf(i));
        }

        ////////////////////////////////////
        // to open the dialog with options//
        ////////////////////////////////////
        //TODO also add to FAB
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Category");
                builder.setItems(listitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mButton.setText(listitem[which]);
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


        ////////////////////////////////////
        // to open the dialog with options//
        ////////////////////////////////////
        mYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Category");
                builder.setItems(years, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mYear.setText(years[which]);
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

        ////////////////////////////
        // to open image selector //
        ////////////////////////////
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        mTitle.addTextChangedListener(checktext);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        /////////////////////
        // To pick Images ///
        ////////////////////
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                // Get the Image from data of the phone

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                ////////////////////////////////////////////////////
                //to handle one photo if picked and dont accept it//
                ///////////////////////////////////////////////////

                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(mImageUri, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);

                    if (filePathColumn.length <= 1) {
                        Toast.makeText(getActivity(), "Please pick more than one photo", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                }

                //////////////////////////////////
                // to handle more than one photo//
                /////////////////////////////////

                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    if (mClipData.getItemCount() > 5) {
                        Toast.makeText(getActivity(), "Please pick 5 or less photos", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            Context context;
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);

                            //to create images view in groups and place them in linear layout
                            ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            lparams.height = 200;
                            lparams.width = 200;
                            ImageView btn = new ImageView(getActivity());
                            btn.setLayoutParams(lparams);
                            btn.setImageURI(mArrayUri.get(i));
                            mLinearLayout.addView(btn);

                            cursor.close();

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
        /////////////////////////
        // End of picking image//
        /////////////////////////

        super.onActivityResult(requestCode, resultCode, data);
    }

    //////////////////////////
    // Method to add Bids ///
    ////////////////////////
    private void addBid() {
        String title, condition, decription, model, bidStartingPrice, nowPrice, year;
        title = mTitle_layout.getEditText().getText().toString().trim();
        condition = mCondition_layout.getEditText().getText().toString().trim();
        decription = mDecription_layout.getEditText().getText().toString().trim();
        model = mModel_layout.getEditText().getText().toString().trim();
        bidStartingPrice = mBidStartingPrice_layout.getEditText().getText().toString().trim();
        nowPrice = mNowPrice_layout.getEditText().getText().toString().trim();
        year = mYear.getText().toString().trim();
        String id = databaseReference.push().getKey();
        if (year.equals("year")) {
            Toasty.warning(getActivity(), "select a years", Toast.LENGTH_SHORT).show();

            mError.setError("select a year");
            mTitle_layout.setError("hey");
        }
//        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(condition) || TextUtils.isEmpty(decription)|| TextUtils.isEmpty(model)|| TextUtils.isEmpty(bidStartingPrice)|| TextUtils.isEmpty(nowPrice)){
//            Toast.makeText(getActivity(),"fill all the feilds",Toast.LENGTH_SHORT).show();
//        }
        else {
            Bids bid = new Bids(id, title, condition, decription, model, bidStartingPrice, nowPrice, year);
            databaseReference.child(id).setValue(bid);
            Toasty.success(getActivity(), "Bid Added", Toast.LENGTH_SHORT, true).show();

        }
    }
    ///////////////////////////
    // End of Add Bids Method//
    ///////////////////////////



    ///////////////////////////////
    // Text watcher for edit text//
    //////////////////////////////
    private TextWatcher checktext = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String userinput = mTitle_layout.getEditText().getText().toString().trim();
            if (!(userinput.isEmpty()))
                mTitle_layout.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String userinput = mTitle_layout.getEditText().getText().toString().trim();
            if (userinput.isEmpty())
                mTitle_layout.setError("FFFF");
        }
    };
    /////////////////////////
    // End of Text watcher //
    /////////////////////////

}