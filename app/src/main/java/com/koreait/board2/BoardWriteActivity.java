package com.koreait.board2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardWriteActivity extends AppCompatActivity {

    private EditText ettitle;
    private EditText etctnt;
    private EditText etwriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        ettitle = findViewById(R.id.ettitle);
        etctnt = findViewById(R.id.etctnt);
        etwriter = findViewById(R.id.etwriter);
    }

    public  void clkSave(View v){

        BoardVO vo = new BoardVO();

        String title = ettitle.getText().toString();
        String ctnt = etctnt.getText().toString();
        String writer = etwriter.getText().toString();

        if("".equals(title)||"".equals(ctnt)||"".equals(writer)) {
            Snackbar.make(v, "실패", Snackbar.LENGTH_SHORT).show();
        }else{
            vo.setTitle(title);
            vo.setCtnt(ctnt);
            vo.setWriter(writer);

            BoardService service = Network.getService();
            Call<Map<String, Integer>> call = service.insBoard(vo);
            call.enqueue(new Callback<Map<String, Integer>>() {
                @Override
                public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> res) {
                    if (res.isSuccessful()) {
                        Map<String, Integer> map = res.body();
                        int result = map.get("result");
                        Log.i("myLog", "result : " + result);

                        switch (result) {
                            case 1:
                                finish();
                                break;
                            default:
                                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                Snackbar.make(v, R.string.msg_fail, Snackbar.LENGTH_SHORT).show();
                                break;
                        }
                        Log.i("myLog", "result : " + result);
                    }
                    finish();
                }

                @Override
                public void onFailure(Call<Map<String, Integer>> call, Throwable t) {

                }
            });
        }

    }

    public void clkCancel(View v){
        finish();
    }
}