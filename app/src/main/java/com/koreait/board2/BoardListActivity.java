package com.koreait.board2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BoardListActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private BoardListAdater adater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);

        adater = new BoardListAdater();

        rvList = findViewById(R.id.rvList);
        rvList.setAdapter(adater);
        getBoardList();
    }

    protected void onStart(){
        super.onStart();
        getBoardList();
    }

    private void getBoardList(){
        BoardService service = Network.getService();
        Call<List<BoardVO>> call = service.selBoardList();
        call.enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> res) {
                if(res.isSuccessful()){
                    List<BoardVO> result = res.body();
                    adater.setList(result);
                    adater.notifyDataSetChanged();
                }else{
                    Log.e("myLog", "통신오류");
                }
            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                Log.e("myLog","통신 자체 실패");
            }
        });

    }

    public void clkwrite(View v){
        Intent intent = new Intent(this, BoardWriteActivity.class);
        startActivity(intent);
    }
}

class BoardListAdater extends RecyclerView.Adapter<BoardListAdater.MyViewHolder>{

    private List<BoardVO> list;

    public void setList(List<BoardVO> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.item_board, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BoardVO vo = list.get(position);
        holder.setItem(vo);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),BoardDetailActivity.class);
                intent.putExtra("iboard", vo.getIboard());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list ==null? 0 : list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tviboard;
        private TextView tvtitle;
        private TextView tvctnt;
        private TextView tvrdt;

        public MyViewHolder (View v){
            super(v);
            tviboard = v.findViewById(R.id.tviboard);
            tvtitle = v.findViewById(R.id.tvtitle);
            tvctnt = v.findViewById(R.id.tvctnt);
            tvrdt = v.findViewById(R.id.tvrdt);
        }

        public void setItem(BoardVO vo){
            tviboard.setText(String.valueOf(vo.getIboard()));
            tvtitle.setText(vo.getTitle());
            tvctnt.setText(vo.getCtnt());
            tvrdt.setText(vo.getRdt());
        }

    }
}
