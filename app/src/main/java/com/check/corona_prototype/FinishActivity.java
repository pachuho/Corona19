package com.check.corona_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.check.corona_prototype.Fragment.Authentication;

import java.util.Timer;
import java.util.TimerTask;

public class FinishActivity extends AppCompatActivity{
    TextView TextView_move, Textview_timer, Textview_map;
    String store;
    int time = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Textview_timer = findViewById(R.id.TextView_timer);
        TextView_move = findViewById(R.id.TextView_move);
        TextView_move.setSelected(true);
        Textview_map = findViewById(R.id.TextView_map);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        store = bundle.getString("store");
        Textview_map.setText("매장명 : " + store);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Textview_timer.setText(time + "초 뒤 창이 닫힙니다.  ");
                time--;
                if(time == -1){
                    finish();
                    onDestroy();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);

    }
}