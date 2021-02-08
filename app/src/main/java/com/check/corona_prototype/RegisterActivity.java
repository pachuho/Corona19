package com.check.corona_prototype;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.check.corona_prototype.Problem.ProblemIdActivity;
import com.check.corona_prototype.Problem.ProblemPwdActivity;
import com.check.corona_prototype.Request.IdCheckRequest;
import com.check.corona_prototype.Request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

// 회원가입 화면
public class RegisterActivity extends AppCompatActivity {
    private EditText editName,editId,editPw,editAddress,editAge;
    private Button btn_register, btn_idCheck;

    // 성별, 매니저에 필요한 변수들
    private RadioGroup r_Sex, r_Manager;
    private RadioButton r_man, r_woman, r_no, r_yes;

    private String id, sex = "남성", manager = "N", corona = "N", idChecking = "N";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.et_signup_name);
        editId = findViewById(R.id.et_signup_id);
        editPw = findViewById(R.id.et_signup_pw);
        editAddress = findViewById(R.id.et_signup_address);
        editAge = findViewById(R.id.et_signup_age);
        r_Sex = (RadioGroup) findViewById(R.id.r_sex);
        r_man = findViewById(R.id.r_man);
        r_woman = findViewById(R.id.r_woman);
        r_Manager = findViewById(R.id.r_manager);
        r_no = findViewById(R.id.r_no);
        r_yes = findViewById(R.id.r_yes);
        btn_register = findViewById(R.id.btn_sign_up_finish);
        btn_idCheck = findViewById(R.id.btn_idCheck);

        // 버튼 활성화 변수
        editName.addTextChangedListener(registerTextWatcher);
        editId.addTextChangedListener(registerTextWatcher);
        editPw.addTextChangedListener(registerTextWatcher);
        editAddress.addTextChangedListener(registerTextWatcher);
        editAge.addTextChangedListener(registerTextWatcher);

        // 아이디 중복 클릭 시
        btn_idCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = editId.getText().toString();
                if (id.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                checkId(id);
            }
        });

        // 성별 클릭 시
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

        // 매장 관리자 클릭 시
        r_Manager.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            //라디오 버튼 상태 변경값을 감지한다.
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i){
                if(i == R.id.r_no){
                    manager = "N";
                } else if(i == R.id.r_yes){
                    manager = "Y";
                }
            }
        });

        // 회원가입완료 버튼
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력값 가져오기
                String name = editName.getText().toString();
                id = editId.getText().toString();
                String pwd = editPw.getText().toString();
                String address = editAddress.getText().toString();
                int age = Integer.parseInt(editAge.getText().toString());

                if (id.equals("") && pwd.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "회원정보를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                signUp(name, id, pwd, address, age, sex);

            }
        });
    }
    private void checkId(String id) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) { // 중복 아이디가 있을 경우
//                                Toast.makeText(getApplicationContext(), "아이디가 있습니다.", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder ad_idExistence = new AlertDialog.Builder(RegisterActivity.this);
                        ad_idExistence.setTitle("이미 사용중인 아이디입니다.");
                        ad_idExistence.setMessage("");
                        ad_idExistence.setCancelable(false);

                        ad_idExistence.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad_idExistence.show();

                    } else { // 중복 아이디가 없을 경우
                        // 아이디 중복 다이얼로그
                        final AlertDialog.Builder ad_idCheck = new AlertDialog.Builder(RegisterActivity.this);
                        ad_idCheck.setTitle("사용 가능한 아이디입니다.");
                        ad_idCheck.setMessage("사용하시겠습니까?");
                        ad_idCheck.setCancelable(false);

                        ad_idCheck.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btn_idCheck.setEnabled(false);
                                editId.setEnabled(false);
                                idChecking = "Y";
                            }
                        });
                        ad_idCheck.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        ad_idCheck.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "중복확인 실패, 통신 이상", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // 서버로 요청
        IdCheckRequest idCheckRequest = new IdCheckRequest(id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(idCheckRequest);
    }

    private void signUp(String name, String id, String pwd, String address, Integer age, String sex) {
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "회원가입 실패, 통신 이상", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // 서버로 요청
        RegisterRequest registerRequest = new RegisterRequest(name, id, pwd, address, age, sex, manager, corona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }

    private TextWatcher registerTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = editName.getText().toString().trim();
            String idInput = editId.getText().toString().trim();
            String pwInput = editPw.getText().toString().trim();
            String addressInput = editAddress.getText().toString().trim();
            String ageInput = editAge.getText().toString().trim();

            btn_register.setEnabled(!nameInput.isEmpty() && !idInput.isEmpty() && !pwInput.isEmpty()
                    && !addressInput.isEmpty() && !ageInput.isEmpty() && idChecking.equals("Y"));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}