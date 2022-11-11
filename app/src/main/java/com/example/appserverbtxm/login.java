package com.example.appserverbtxm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lib.Repository.Methods;
import com.example.lib.Repository.RetrofitClient;
import com.example.lib.model.CuaHangController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    Button btnLogin,btnDK;
    EditText acc,pas;
    TextView thongbao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        acc = findViewById(R.id.edtUsername);
        pas = findViewById(R.id.edtPassword);
        thongbao = findViewById(R.id.txtThongBao);
        btnDK = findViewById(R.id.btnDK);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ábdaksbdbas","đã vào");
                Methods methods = RetrofitClient.getRetrofit().create(Methods.class);
                Call<CuaHangController[]> call = methods.getUser();
                call.enqueue(new Callback<CuaHangController[]>() {
                    @Override
                    public void onResponse(Call<CuaHangController[]> call, Response<CuaHangController[]> response) {
                        Log.v("ábdaksbdbas","đúng");
                        CuaHangController[] data = response.body();
                        int id = -1;
                        int count=0;
                        for (CuaHangController dt:data
                        ) {
                            Log.v("ábdaksbdbas",dt.getTaikhoan());
                            if(dt.getTaikhoan().equals(acc.getText().toString()) && dt.getMatkhau().equals(pas.getText().toString())){
                                count=1;
                                id = dt.getIdcuahang();
                            }
                        }
                        if(count==1){
                            Intent intent = new Intent(login.this,MainActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }else {
                            thongbao.setText("Tài khoản hoặc mật khẩu không chính xác");
                        }
                    }

                    @Override
                    public void onFailure(Call<CuaHangController[]> call, Throwable t) {
                        Log.v("ábdaksbdbas","sai");
                    }
                });
            }
        });
    }
}