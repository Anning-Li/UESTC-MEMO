package com.starstudio.projectdemo.journal.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.starstudio.projectdemo.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * created by sgh
 * 2021-7-31
 * “心情日记”页面中“日记”板块展示图片的RecyclerView对应的适配器
 */
public class ImgsAdapter extends RecyclerView.Adapter<ImgsAdapter.ImgHolder> {

    private List<String> data;

    public ImgsAdapter (List<String> data) {
        this.data = data;
    }
    @NonNull
    @NotNull
    @Override
    public ImgHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        return new ImgHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ImgsAdapter.ImgHolder holder, int position) {
        if (data.size() > 9 && position == 8)
            holder.loadData(data.get(position), data.size() - 9);
        else
            holder.loadData(data.get(position), 0);
    }

    @Override
    public int getItemCount() {
        return Math.min(data.size(), 9);
    }

    public void clear() {
        this.data = null;
    }


    public static class ImgHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txt;
        public ImgHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txt = (TextView) itemView.findViewById(R.id.last_count_txt);
        }


        protected void loadData(String data, int last) {
            File pic = new File(data);
            Glide.with(img).load(pic).override(200,200).centerCrop().into(img);
            // 强制item的大小，而不是复用缓存的recycler的尺寸
            // https://blog.csdn.net/qq_36419317/article/details/54836142?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-3.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromMachineLearnPai2%7Edefault-3.control
            img.setAdjustViewBounds(true);
            if (last > 0) {
                img.setForeground(img.getContext().getDrawable(R.drawable.img_mask));
                txt.setVisibility(View.VISIBLE);
                txt.setText("+" + last);
            }
        }
    }
}
