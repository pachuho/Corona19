package com.check.corona_prototype.Problem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.check.corona_prototype.R;
import com.check.corona_prototype.Request.FindIdRequest;
import com.check.corona_prototype.Request.FindPwdRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProblemPwdActivity extends AppCompatActivity {
    EditText et_id, et_name, et_age;
    Button btn_find_next;
    String id, name, sex = "남성";
    Integer age;

    // 성별에 필요한 변수들
    private RadioGroup r_Sex;
    private RadioButton r_man, r_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_pwd);

        et_id = findViewById(R.id.et_find_id);
        et_name = findViewById(R.id.et_find_name);
        et_age = findViewById(R.id.et_find_age);
        r_Sex = findViewById(R.id.r_sex);
        r_man = findViewById(R.id.r_man);
        r_woman = findViewById(R.id.r_woman);
        btn_find_next = findViewById(R.id.btn_find_next);

        // 라디오버튼 클릭 시
        r_Sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            //라디오 버튼 상태 변경값을 감지한다.
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i){
                if(i == R.id.r_man){
                    sex = r_man.getText().toString();
                } else if(i == R.id.r_woman){
                    sex = r_woman.getText().toString();
                }
            }
        });

        // 다음 클릭 시
        btn_find_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 입력값 가져오기
                    id = et_id.getText().toString();
                    name = et_name.getText().toString();
                    age = Integer.parseInt(et_age.getText().toString());

                    if (id.equals("") || name.equals("") || age.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "회원정보를 입력하세요", Toast.LENGTH_SHORT).show();
                    }

                    // 통신
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 회원정보 찾기 성공
                                    String pwd = jsonObject.getString("pwd");
//                                    Toast.makeText(getApplicationContext(), "회원정보 찾기 성공!\n" + id, Toast.LENGTH_SHORT).show();

                                    // 비밀번호 찾기 다이얼로그
                                    final AlertDialog.Builder ad_findId = new AlertDialog.Builder(ProblemPwdActivity.this);
                                    ad_findId.setTitle("회원님의 비밀번호는 " + pwd + " 입니다.");
                                    ad_findId.setMessage("");
                                    ad_findId.setCancelable(false);

                                    ad_findId.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                                    ad_findId.show();


                                } else { // 회원정보 찾기 실패
                                    Toast.makeText(getApplicationContext(), "회원정보 찾기 실패", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "회원정보 찾기 실패, 통신 이상", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    // 서버로 요청
                    FindPwdRequest findPwdRequest = new FindPwdRequest(id, name, age, sex, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProblemPwdActivity.this);
                    queue.add(findPwdRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

    }
}