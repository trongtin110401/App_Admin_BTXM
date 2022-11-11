package com.example.appserverbtxm.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appserverbtxm.R;
import com.example.appserverbtxm.adapter.adapterGroupChat;
import com.example.appserverbtxm.databinding.FragmentDashboardBinding;
import com.example.lib.Repository.Methods;
import com.example.lib.Repository.RetrofitClient;
import com.example.lib.model.GroupChatModel;
import com.example.lib.request.RqGroupChat;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    EditText txtTimkiem;

    RecyclerView recyclerView;
    ArrayList<GroupChatModel> t;
    adapterGroupChat adtGroupChat;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        int id = intent.getIntExtra("id",-1);
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        txtTimkiem = view.findViewById(R.id.txtTimkiem);
        txtTimkiem.setText(String.valueOf(id));
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView =view.findViewById(R.id.listChat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        RqGroupChat rqGroupChat = new RqGroupChat();
        rqGroupChat.setIdcuahang(id);
        Methods methods = RetrofitClient.getRetrofit().create(Methods.class);
        Call<GroupChatModel[]> call = methods.getGroupChat(rqGroupChat);
        call.enqueue(new Callback<GroupChatModel[]>() {
            @Override
            public void onResponse(Call<GroupChatModel[]> call, Response<GroupChatModel[]> response) {
                GroupChatModel[] dta = response.body();
                ArrayList<GroupChatModel> temp = new ArrayList<>();
                for (GroupChatModel dt:dta
                ) {
                    temp.add(dt);
                    Log.v("............",dt.getAvatar());
                }
                adtGroupChat = new adapterGroupChat(getActivity(),temp);
                recyclerView.setAdapter(adtGroupChat);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<GroupChatModel[]> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}