package com.example.appserverbtxm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appserverbtxm.R;
import com.example.lib.model.MessageModel;

import java.util.ArrayList;

public class adapterMessage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<MessageModel> mHeros;
    int idcuahang,iduser;

    public adapterMessage(Context mContext, ArrayList<MessageModel> mHeros) {
        this.mContext = mContext;
        this.mHeros = mHeros;
        this.idcuahang = idcuahang;
        this.iduser= iduser;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mstxt, parent, false);
            return new ViewHolder(view);}
        if(viewType == 2) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.txtmsright, parent, false);
            return new ViewHolder1(view);}
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel hero = mHeros.get(position);
        if (holder.getItemViewType()==1) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mTextName.setText(hero.getNoidung());
        }
        if (holder.getItemViewType()==2) {
            ViewHolder1 viewHolder = (ViewHolder1) holder;
            viewHolder.mTextName1.setText(hero.getNoidung());
        }
    }
    @Override
    public int getItemViewType(int position) {
        MessageModel hero = mHeros.get(position);
        if(hero.getNguoigui().equals("user") ) return 1;
        else return 2;
    }
    @Override
    public int getItemCount() {
        return mHeros.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.txtMs);
        }
    }
    public class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView mTextName1;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            mTextName1 = itemView.findViewById(R.id.txtMsRight);
        }
    }
}
