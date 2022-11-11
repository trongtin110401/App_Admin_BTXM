package com.example.appserverbtxm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appserverbtxm.Chat;
import com.example.appserverbtxm.R;
import com.example.lib.model.GroupChatModel;

import java.util.ArrayList;

public class adapterGroupChat extends
        RecyclerView.Adapter<adapterGroupChat.ViewHolder>{
    private Context mContext;
    private ArrayList<GroupChatModel> mHeros;

    public adapterGroupChat(Context mContext, ArrayList<GroupChatModel> mHeros) {
        this.mContext = mContext;
        this.mHeros = mHeros;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.itemchat, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupChatModel hero = mHeros.get(position);
        holder.Name.setText(hero.getUsername());
        Glide.with(mContext)
                .load(hero.getAvatar())
                .into(holder.mImageHero);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("key_1",hero.getIdcuahang());
                intent.putExtra("key_2",hero.getUser_iduser());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mHeros.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageHero;
        private TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageHero = itemView.findViewById(R.id.imgUser);
            Name = itemView.findViewById(R.id.Username);

        }
    }
}
