package com.check.corona_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.check.corona_prototype.Manger.ManagerActivity;
import com.check.corona_prototype.Problem.FindActivity;
import com.check.corona_prototype.Request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

// 로그인화면
public class LoginActivity extends AppCompatActivity {
    private EditText editId, editPw;
    private Button btn_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editId = findViewById(R.id.et_id);
        editPw = findViewById(R.id.et_pw);
        btn_signIn = findViewById(R.id.btn_sign_in);

        editId.addTextChangedListener(loginTextWatcher);
        editPw.addTextChangedListener(loginTextWatcher);

        // 로그인 버튼
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editId.getText().toString();
                String pwd = editPw.getText().toString();


                // 관리자 모드
                if(id.equals("root")&& pwd.equals("1q2w3e4r"))
                {
                    // 현재 텍스트 초기화
                    editId.setText("");
                    editPw.setText("");

//                    Toast.makeText(getApplicationContext(), "관리자 모드 진입", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", "테스터");
                    intent.putExtra("id", id);
                    intent.putExtra("pwd", pwd);
                    intent.putExtra("manager", "N");
                    startActivity(intent);
               }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인 성공
                                // 현재 텍스트 초기화
                                editId.setText("");
                                editPw.setText("");

                                String name = jsonObject.getString("name");
                                String id = jsonObject.getString("id");
                                String pwd = jsonObject.getString("pwd");
                                String manager = jsonObject.getString("manager");

//                                Toast.makeText(getApplicationContext(), "매장관리자 :" + manager, Toast.LENGTH_SHORT).show();

                                // 매장 관리자일 경우
                                Intent intent;
                                if (manager.equals("Y")) {
                                    // 매니저 액티비티로
                                    intent = new Intent(LoginActivity.this, ManagerActivity.class);
                                    // 사용자 메인 액티비티로
                                } else {
                                    intent = new Intent(LoginActivity.this, MainActivity.class);
                                }
                                intent.putExtra("name", name);
                                intent.putExtra("id", id);
                                intent.putExtra("pwd", pwd);
                                intent.putExtra("manager", manager);
                                startActivity(intent);


                            } else { // 로그인 실패
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "로그인 실패, 통신이상", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
            LoginRequest loginRequest = new LoginRequest(id, pwd, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);

            }
        });

        // 회원정보 분실
        findViewById(R.id.btn_forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindActivity.class);
                startActivity(intent);
            }
        });

        // 회원가입 버튼
        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String idInput = editId.getText().toString().trim();
            String pwInput = editPw.getText().toString().trim();

            btn_signIn.setEnabled(!idInput.isEmpty() && !pwInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}