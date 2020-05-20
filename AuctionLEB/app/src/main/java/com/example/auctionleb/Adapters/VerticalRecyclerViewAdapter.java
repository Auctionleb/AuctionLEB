package com.example.auctionleb.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auctionleb.Models.HorizontalModel;
import com.example.auctionleb.Models.VerticalModel;
import com.example.auctionleb.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.VerticalRecyclerViewHolder> {

    private Context mContext;
    private ArrayList<VerticalModel> mArrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public VerticalRecyclerViewAdapter(Context mContext, ArrayList<VerticalModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public VerticalRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_vertical,parent,false);
        // View view = LayoutInflater.from(mContext).inflate(R.layout.item_vertical, parent, false);
        return new VerticalRecyclerViewHolder(view);
    }

    public void setList(ArrayList<VerticalModel> mArrayList){
        this.mArrayList.addAll(mArrayList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VerticalRecyclerViewHolder holder, int position) {

        final VerticalModel current = mArrayList.get(position);

        final String strTitle = current.getTitle();

        ArrayList<HorizontalModel> singleSectionItems = current.getArrayList();

        holder.tvTitle.setText(strTitle);

        HorizontalRecyclerViewAdapter itemListDataAdapter =
                new HorizontalRecyclerViewAdapter(mContext, singleSectionItems);

        holder.rvHorizontal.setHasFixedSize(true);
        holder.rvHorizontal.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));
        holder.rvHorizontal.setAdapter(itemListDataAdapter);

        holder.rvHorizontal.setNestedScrollingEnabled(false);

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, current.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class VerticalRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        RecyclerView rvHorizontal;
        Button btnMore;
        public VerticalRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            rvHorizontal = itemView.findViewById(R.id.rvHorizontal);
            btnMore = itemView.findViewById(R.id.btnMore);
        }
    }
}