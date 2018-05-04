package com.m.hossam.hossamgithub.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.m.hossam.hossamgithub.Items.Main_Activity_Item;
import com.m.hossam.hossamgithub.R;

import java.util.ArrayList;


public class Main_Activity_Adapter extends RecyclerView.Adapter<Main_Activity_Adapter.ViewHolder> {
    private View view;

    private ArrayList<Main_Activity_Item> items;
    private Context context;
    private LayoutInflater mInflater;

    public Main_Activity_Adapter(ArrayList<Main_Activity_Item> list_items, Context context) {
        this.items = list_items;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.album_id.setText(items.get(position).getAlbum_id());
        Glide.with(context).load(items.get(position).getPhoto()).crossFade().into(holder.photo);


        if (items.get(position).getFiltered().equals("hide")) {
            holder.check_relativelayout.setVisibility(View.GONE); /// to hide check box in filtered activity
        } else {
            holder.check_relativelayout.setVisibility(View.VISIBLE); /// to show check box in filtered activity
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!items.get(position).isChecked()) {
                    holder.checked_layout.setVisibility(View.VISIBLE);
                    items.get(position).setChecked(true);
                } else {
                    holder.checked_layout.setVisibility(View.GONE);
                    items.get(position).setChecked(false);
                }

            }
        });


    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, album_id;
        ImageView photo;
        LinearLayout checked_layout;
        RelativeLayout check_relativelayout;

        private ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            album_id = itemView.findViewById(R.id.album_id);
            photo = itemView.findViewById(R.id.photo);
            checked_layout = itemView.findViewById(R.id.checked_layout);
            check_relativelayout = itemView.findViewById(R.id.check_relativelayout);

        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

