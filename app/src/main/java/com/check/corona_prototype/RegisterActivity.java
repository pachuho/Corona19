package com.check.corona_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

// 회원가입 화면
public class RegisterActivity extends AppCompatActivity {
    private EditText editName,editId,editPw,editAddress,editAge,editSex;
    private Button btn_register, btn_idcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.et_signup_name);
        editId = findViewById(R.id.et_signup_id);
        editPw = findViewById(R.id.et_signup_pw);
        editAddress = findViewById(R.id.et_signup_address);
        editAge = findViewById(R.id.et_signup_age);
        editSex = findViewById(R.id.et_signup_sex);
        btn_register = findViewById(R.id.btn_sign_up_finish);
//        btn_idcheck = (Button) findViewById(R.id.btn_sign_up_idcheck);
//
//        // 중복확인 버튼
//        btn_idcheck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(RegisterActivity.this, "업데이트 예정", Toast.LENGTH_LONG).show();
//            }
//        });


        // 회원가입완료 버튼
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력값 가져오기
                String name = editName.getText().toString();
                String id = editId.getText().toString();
                String pwd = editPw.getText().toString();
                String address = editAddress.getText().toString();
                int age = Integer.parseInt(editAge.getText().toString());
                String sex = editSex.getText().toString();


                if (id.equals("") && pwd.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "회원정보를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원가입 성공
                                Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else { // 회원가입 실패
                                Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 요청
                RegisterRequest registerRequest = new RegisterRequest(name, id, pwd, address, age, sex, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }

}