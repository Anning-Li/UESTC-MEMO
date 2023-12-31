package com.starstudio.projectdemo.journal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starstudio.projectdemo.R;
import com.starstudio.projectdemo.journal.data.JournalEditActivityData;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * created by sgh
 * 2021-8-1
 * 写日记板块中添加图片对应的适配器
 */
public class AddPictureAdapter extends RecyclerView.Adapter<AddPictureAdapter.AddHolder> {

    private ArrayList<JournalEditActivityData.PictureWithCategory> data;
    private OnItemClickListener clickListener;

    public AddPictureAdapter(ArrayList<JournalEditActivityData.PictureWithCategory> data, OnItemClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    public void append(List<JournalEditActivityData.PictureWithCategory> append) {
        data.addAll(append);
        notifyDataSetChanged();
    }


    public ArrayList<JournalEditActivityData.PictureWithCategory> getData(){
        return data;
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull AddPictureAdapter.AddHolder holder, int position) {
        if (position == data.size())
            holder.loadData(null);
        else
            holder.loadData(this.data.get(data.size() - 1 - position));
        holder.setClickListener(clickListener, position);
    }

    @NonNull
    @NotNull
    @Override
    public AddHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_picture_item, parent, false);
        return new AddHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    protected static class AddHolder extends RecyclerView.ViewHolder {

        private final ImageView imgView;
        public AddHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.imgView = (ImageView) itemView.findViewById(R.id.add_img_video);
        }

        protected void loadData(JournalEditActivityData.PictureWithCategory data) {
            if (data == null) {
                imgView.setImageResource(R.drawable.add_big);
                imgView.setTag(ItemType.FIRST);
            } else {
                File pic = new File(data.getPicturePath());
                Glide.with(imgView).load(pic).override(200,200).centerCrop().into(imgView);
                imgView.setTag(ItemType.OTHER);
            }
        }

        protected void setClickListener(OnItemClickListener clickListener, int position) {
            this.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, position);
                }
            });
        }
    }

    /**
     * 枚举类型
     * 表示item类型
     */
    public static enum ItemType {
        FIRST(), OTHER();
    }

    /**
     * RecyclerView中item的点击事件
     */
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}




