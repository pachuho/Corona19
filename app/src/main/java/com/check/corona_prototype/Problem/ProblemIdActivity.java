package com.check.corona_prototype.Problem;

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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.check.corona_prototype.R;
import com.check.corona_prototype.Request.FindIdRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProblemIdActivity extends AppCompatActivity {
    EditText et_name, et_age;
    Button btn_find_next;
    String name, sex = "남성";
    Integer age;

    // 성별에 필요한 변수들
    private RadioGroup r_Sex;
    private RadioButton r_man, r_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_id);

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
                    name = et_name.getText().toString();
                    age = Integer.parseInt(et_age.getText().toString());

                    if (name.equals("") || age.equals(""))
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
                                    String id = jsonObject.getString("id");
//                                    Toast.makeText(getApplicationContext(), "회원정보 찾기 성공!\n" + id, Toast.LENGTH_SHORT).show();

                                    // 아이디 찾기 다이얼로그
                                    final AlertDialog.Builder ad_findId = new AlertDialog.Builder(ProblemIdActivity.this);
                                    ad_findId.setTitle("회원님의 아이디는 " + id + " 입니다.");
                                    ad_findId.setMessage("비밀번호도 찾으시겠습니까?");
                                    ad_findId.setCancelable(false);

                                    ad_findId.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            Intent intent = new Intent(ProblemIdActivity.this, ProblemPwdActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    ad_findId.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
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
                    FindIdRequest findIdRequest = new FindIdRequest(name, age, sex, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ProblemIdActivity.this);
                    queue.add(findIdRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });

    }
}