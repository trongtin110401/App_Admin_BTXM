package com.example.lib.Repository;

import com.example.lib.model.CuaHangController;
import com.example.lib.model.GroupChatModel;
import com.example.lib.model.MessageModel;
import com.example.lib.request.RqGroupChat;
import com.example.lib.request.rqChat;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Methods {
    @POST("api/doanchat")
    Call<GroupChatModel[]> getGroupChat(@Body RqGroupChat data);
    @GET("api/cuahang")
    Call<CuaHangController[]> getUser();
    @POST("api/chat")
    Call<MessageModel[]> getChat(@Body rqChat data);

}
