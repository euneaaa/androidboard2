package com.koreait.board2;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BoardService {
    @GET("list")
    Call<List<BoardVO>> selBoardList();

    @POST("ins")
    Call<Map<String,Integer>> insBoard(@Body BoardVO param);

    @GET("one")
    Call<BoardVO> selDetailBoard(@Query ("iboard") int iboard);
}
