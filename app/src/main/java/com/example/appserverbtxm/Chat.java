package com.example.appserverbtxm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appserverbtxm.adapter.adapterMessage;
import com.example.lib.Repository.Methods;
import com.example.lib.Repository.RetrofitClient;
import com.example.lib.model.MessageModel;
import com.example.lib.request.rqChat;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chat extends AppCompatActivity {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.128.128:3000");
        } catch (URISyntaxException e) {}
    }
    EditText editMessage;
    ImageView imgSend;
    RecyclerView recyclerView;
    adapterMessage adapterMessege1;
    ArrayList<MessageModel> temp1 = new ArrayList<>();
    int idcuahang,iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSocket.connect();
        mSocket.on("server-gui-tinnhan",listMembers);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        recyclerView = findViewById(R.id.listMS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        editMessage = findViewById(R.id.editMessage);
        imgSend = findViewById(R.id.imgSend);
        idcuahang = intent.getIntExtra("key_1",-1);
        iduser = intent.getIntExtra("key_2",-1);
        Log.v("iduser",String.valueOf(iduser));
        Log.v("idcuahang",String.valueOf(idcuahang));
        Methods methods = RetrofitClient.getRetrofit().create(Methods.class);
        rqChat temp = new rqChat();
        temp.setIduser(iduser);
        temp.setIdcuahang(idcuahang);
        Call<MessageModel[]> call = methods.getChat(temp);
        Log.v("aaaaaa",new Gson().toJson(temp));
        call.enqueue(new Callback<MessageModel[]>() {
            @Override
            public void onResponse(Call<MessageModel[]> call, Response<MessageModel[]> response) {
                MessageModel[] data = response.body();
                for (MessageModel dt:data
                ) {
                    Log.v("aaaaaaaaaaaaaaaaa",dt.getNoidung());
                    temp1.add(dt);
                }
                adapterMessege1 = new adapterMessage(Chat.this,temp1);
                recyclerView.setAdapter(adapterMessege1);
                recyclerView.setVisibility(View.VISIBLE);
                adapterMessege1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MessageModel[]> call, Throwable t) {
            }
        });
        mSocket.emit("client-gui-id", "cuahang");
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocket.emit("client-gui-tinnhan", editMessage.getText().toString());
            }
        });


    }

    public Emitter.Listener listMembers = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String nguoigui;
                    String tinnhan;
                    try {
                        nguoigui = data.getString("nguoigui");
                        tinnhan = data.getString("tinnhan");
                        MessageModel temp = new MessageModel();
                        temp.setNguoigui(nguoigui);
                        temp.setNoidung(tinnhan);
                        temp1.add(0,temp);
                        adapterMessege1.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // add the message to view
                }
            });
        }
    };
}