package com.check.corona_prototype.Manger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.check.corona_prototype.R;
import com.check.corona_prototype.Request.PwdChangeRequest;
import com.check.corona_prototype.Request.WithdrawalRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ManagerActivity extends AppCompatActivity {
    String id, name, pwd;
    TextView tv_name, tv_logout;
    LinearLayout ll_qrcodeProduce, ll_pwchange, ll_idleave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        // 로그인 -> 메인 값 받기(아이디, 이름, 패스워드)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        String id = bundle.getString("id");
        pwd = bundle.getString("pwd");
        getIntent().getExtras().clear();

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(name + " 관리자님, 안녕하세요");

        tv_logout = findViewById(R.id.tv_logout);
        ll_qrcodeProduce = findViewById(R.id.ll_qrcodeProduce);
        ll_pwchange = findViewById(R.id.ll_pwchange);
        ll_idleave = findViewById(R.id.ll_idleave);

        // 로그아웃
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // qr코드 생성
        ll_qrcodeProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // QR코드 생성
                createQR();
            }
        });

        // 비밀번호 변경
        ll_pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePwd();

            }
        });

        // 회원 탈퇴
        ll_idleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawal();
            }
        });


    }
    private void createQR(){
        // QR 생성 다이얼로그
        final AlertDialog.Builder qrMake = new AlertDialog.Builder(ManagerActivity.this);
        qrMake.setTitle("매장명을 입력해주세요");
        qrMake.setMessage("");
        qrMake.setCancelable(false);
        final EditText et_store = new EditText(ManagerActivity.this);
        qrMake.setView(et_store);

        qrMake.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String store = et_store.getText().toString();
                Intent intent = new Intent(ManagerActivity.this, CreateQR.class);
                intent.putExtra("store", store);
                startActivity(intent);
            }
        });
        qrMake.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        qrMake.show();
    }

    private void changePwd(){
        // 현재 비밀번호 확인 다이얼로그
        final AlertDialog.Builder nowpwd = new AlertDialog.Builder(ManagerActivity.this);
        nowpwd.setTitle("현재 비밀번호를 입력해주세요");
        nowpwd.setMessage("");
        final EditText et_nowpwd = new EditText(ManagerActivity.this);
        nowpwd.setView(et_nowpwd);

        nowpwd.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputpwd = et_nowpwd.getText().toString();
                if (pwd.equals(inputpwd)){
//                            Toast.makeText(context, "비밀번호 일치", Toast.LENGTH_SHORT).show();

                    // 바꿀 비밀번호 다이얼로그
                    final AlertDialog.Builder afterpwd = new AlertDialog.Builder(ManagerActivity.this);
                    afterpwd.setTitle("변경할 비밀번호를 입력해주세요");
                    afterpwd.setMessage("");
                    afterpwd.setCancelable(false);
                    final EditText et_afterpwd = new EditText(ManagerActivity.this);
                    afterpwd.setView(et_afterpwd);

                    afterpwd.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String newpwd = et_afterpwd.getText().toString();
//                                    Toast.makeText(context, "입력비번 : " + newpwd, Toast.LENGTH_SHORT).show();
                            try {
                                // 통신
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");
                                            if (success) { // 비밀번호 변경 성공
                                                Toast.makeText(ManagerActivity.this, "비밀번호 변경 성공\n다시 로그인을 해주세요", Toast.LENGTH_SHORT).show();
                                                finish();

                                            } else { // 비밀번호 변경 실패
                                                Toast.makeText(ManagerActivity.this, "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();                                                    }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(ManagerActivity.this, "비밀번호 변경 실패, 통신이상", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                PwdChangeRequest pwdChangeRequest = new PwdChangeRequest(newpwd, id, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ManagerActivity.this);
                                queue.add(pwdChangeRequest);

                            }catch(Exception e){
                                Toast.makeText(ManagerActivity.this, "변경할 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                throw e;
                            }
                        }
                    });
                    afterpwd.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    afterpwd.show();

                }else {
                    Toast.makeText(ManagerActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nowpwd.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        nowpwd.show();
    }

    void withdrawal(){
        //                Toast.makeText(context, "회원탈퇴", Toast.LENGTH_SHORT).show();
        // 바꿀 비밀번호 다이얼로그
        final AlertDialog.Builder idDelete = new AlertDialog.Builder(ManagerActivity.this);
        idDelete.setTitle("정말 탈퇴하시겠습니까?");
        idDelete.setCancelable(false);

        idDelete.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    // 통신
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // 회원 탈퇴 성공
                                    Toast.makeText(ManagerActivity.this, "회원 탈퇴 완료", Toast.LENGTH_SHORT).show();
                                    finish();

                                } else { // 회원 탈퇴 실패
                                    Toast.makeText(ManagerActivity.this, "회원 탈퇴 실패", Toast.LENGTH_SHORT).show();                                                    }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ManagerActivity.this, "회원 탈퇴 실패, 통신이상", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    WithdrawalRequest withdrawalRequest = new WithdrawalRequest(id, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ManagerActivity.this);
                    queue.add(withdrawalRequest);

                }catch(Exception e){
                    e.printStackTrace();
                    throw e;
                }
            }
        });
        idDelete.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        idDelete.show();
    }
}