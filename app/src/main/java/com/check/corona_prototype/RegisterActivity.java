package com.check.corona_prototype;

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
import org.json.JSONException;
import org.json.JSONObject;

// 회원가입 화면
public class RegisterActivity extends AppCompatActivity {
    private EditText editName,editId,editPw,editAddress,editAge;
    private Button btn_register;
    private RadioGroup r_Sex;
    private RadioButton r_man, r_woman;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.et_signup_name);
        editId = findViewById(R.id.et_signup_id);
        editPw = findViewById(R.id.et_signup_pw);
        editAddress = findViewById(R.id.et_signup_address);
        editAge = findViewById(R.id.et_signup_age);
        r_man = findViewById(R.id.r_man);
        r_woman = findViewById(R.id.r_woman);

        r_Sex = (RadioGroup) findViewById(R.id.r_sex);

        r_Sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            //라디오 버튼 상태 변경값을 감지한다.
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i){
                if(i == R.id.r_man){
                    sex = r_man.getText().toString();//라디오 버튼의 텍스트 값을 string에 담아준것
                } else if(i == R.id.r_woman){
                    sex = r_woman.getText().toString();//라디오 버튼의 텍스트 값을 string에 담아준것
                }
            }
        });

        btn_register = findViewById(R.id.btn_sign_up_finish);

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