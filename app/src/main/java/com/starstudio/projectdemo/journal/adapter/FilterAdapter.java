package com.starstudio.projectdemo.journal.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.starstudio.projectdemo.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {

    private OnFilterTypeClickListener listener;
    private final String[] data;
    public FilterAdapter(String[] data) {
        this.data = data;
    }

    public void setListener(OnFilterTypeClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public FilterHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        FilterHolder holder = new FilterHolder(view);
        holder.setListener(this.listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FilterAdapter.FilterHolder holder, int position) {
        holder.loadData(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    static class FilterHolder extends RecyclerView.ViewHolder {
        private OnFilterTypeClickListener listener;
        private TextView txt;
        public FilterHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.filter_item_txt);
        }

        protected void setListener(OnFilterTypeClickListener listener) {
            this.listener = listener;
        }

        protected void loadData(String data) {
            txt.setText(data);
            txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        txt.setTextColor(Color.WHITE);
                        txt.setTextSize(19);
                    } else {
                        txt.setTextColor(Color.GRAY);
                        txt.setTextSize(18);
                    }
                }
            });

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        listener.onFilterTypeClick(view, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static interface OnFilterTypeClickListener {
        void onFilterTypeClick(View v, String type) throws JSONException;
    }
}
