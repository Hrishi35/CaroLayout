package com.github.airsaid.carousellayoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<ImageList> mLists;
    public MyAdapter(Context context, List<ImageList> mLists) {
        this.context=context;
        this.mLists=mLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("create","qq");
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_main, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("bind","qq"+position);
        if (mLists.get(position).getImageurl().equalsIgnoreCase("null")){

        }
        else {
        Glide.with(context).load(mLists.get(position).getImageurl()).into(holder.imgCover);
        Log.d("qq","qq"+mLists.get(position).getImageurl());
    }}

    @Override
    public int getItemCount() {
        return mLists.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imgCover;

    public MyViewHolder(View itemView) {
        super(itemView);
        imgCover = (ImageView) itemView.findViewById(R.id.img_cover);
    }
}
