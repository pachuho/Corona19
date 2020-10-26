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
                    Toast.makeText(getApplicationContext(), "관리자 모드 진입", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", "관리자");
                    intent.putExtra("pwd", pwd);
                    startActivity(intent);
                    return;
               }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 로그인 성공

                                String name = jsonObject.getString("name");
                                String id = jsonObject.getString("id");
                                String pwd = jsonObject.getString("pwd");

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                intent.putExtra("id", id);
                                intent.putExtra("name", name);
                                intent.putExtra("pwd", pwd);


                                startActivity(intent);
                            } else { // 로그인 실패
                                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                Toast.makeText(LoginActivity.this, "업데이트 예정", Toast.LENGTH_SHORT).show();
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