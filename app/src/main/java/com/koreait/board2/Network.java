package com.koreait.board2;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    private static Retrofit retrofit;
    private static BoardService service;

    public static BoardService getService(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("http://192.168.2.27:8090/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(BoardService.class);
        }
        return service;
    }
}
