package com.check.corona_prototype.Problem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.check.corona_prototype.R;

public class FindActivity extends AppCompatActivity {

    private Button btn_find;
    private RadioGroup r_problemCheck;
    private RadioButton r_problemId, r_ProblemPwd, r_ProblemEtc;

    private int problem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        btn_find = findViewById(R.id.btn_find);
        r_problemCheck = findViewById(R.id.r_problemCheck);
        r_problemId = findViewById(R.id.r_problemId);
        r_ProblemPwd = findViewById(R.id.r_ProblemPwd);
        r_ProblemEtc = findViewById(R.id.r_ProblemEtc);

        // 라디오박스 클릭 시
        r_problemCheck.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i){
                if(i == R.id.r_problemId){
                    problem = 0;
                } else if(i == R.id.r_ProblemPwd){
                    problem = 1;
                } else if(i == R.id.r_ProblemEtc){
                    problem = 2;
                }
            }
        });

        // 버튼 클릭 시
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (problem) {
                    case 0: // 아이디 문제
                        Intent intent1 = new Intent(FindActivity.this, ProblemIdActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case 1: // 비밀번호 문제
                        Intent intent2 = new Intent(FindActivity.this, ProblemPwdActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case 2: // 그외 문제
                        Intent intent3 = new Intent(FindActivity.this, ProblemEtcActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}