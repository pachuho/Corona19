package com.check.corona_prototype.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.check.corona_prototype.LoginActivity;
import com.check.corona_prototype.MainActivity;
import com.check.corona_prototype.R;
import com.check.corona_prototype.Request.LoginRequest;
import com.check.corona_prototype.Request.PwdChangeRequest;
import com.check.corona_prototype.Request.WithdrawalRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class Setting extends Fragment {
    ViewGroup viewGroup;
    String id, pwd;
    LinearLayout ll_pwchange, ll_idleave;
    TextView tv_logout;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);
        context = container.getContext();

        Bundle bundle = getArguments();
        id = bundle.getString("id");
        pwd = bundle.getString("pwd");

        ll_pwchange = viewGroup.findViewById(R.id.ll_pwchange);
        ll_idleave = viewGroup.findViewById(R.id.ll_idleave);
        tv_logout = viewGroup.findViewById(R.id.tv_logout);

        // 로그아웃
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        // 비밀번호 변경
        ll_pwchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 현재 비밀번호 확인 다이얼로그
                final AlertDialog.Builder nowpwd = new AlertDialog.Builder(context);
                nowpwd.setTitle("현재 비밀번호를 입력해주세요");
                nowpwd.setMessage("");
                final EditText et_nowpwd = new EditText(context);
                nowpwd.setView(et_nowpwd);

                nowpwd.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputpwd = et_nowpwd.getText().toString();
                        if (pwd.equals(inputpwd)){
//                            Toast.makeText(context, "비밀번호 일치", Toast.LENGTH_SHORT).show();

                            // 바꿀 비밀번호 다이얼로그
                            final AlertDialog.Builder afterpwd = new AlertDialog.Builder(context);
                            afterpwd.setTitle("변경할 비밀번호를 입력해주세요");
                            afterpwd.setMessage("");
                            final EditText et_afterpwd = new EditText(context);
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
                                                        Toast.makeText(context, "비밀번호 변경 성공\n다시 로그인을 해주세요", Toast.LENGTH_SHORT).show();
                                                        getActivity().finish();

                                                    } else { // 비밀번호 변경 실패
                                                        Toast.makeText(context, "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    Toast.makeText(context, "비밀번호 변경 실패, 통신이상", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        };
                                        PwdChangeRequest pwdChangeRequest = new PwdChangeRequest(newpwd, id, responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(context);
                                        queue.add(pwdChangeRequest);

                                    }catch(Exception e){
                                        Toast.makeText(context, "변경할 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
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
        });

        // 회원 탈퇴
        ll_idleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "회원탈퇴", Toast.LENGTH_SHORT).show();
                // 바꿀 비밀번호 다이얼로그
                final AlertDialog.Builder idDelete = new AlertDialog.Builder(context);
                idDelete.setTitle("정말 탈퇴하시겠습니까?");
                idDelete.setMessage("");

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
                                            Toast.makeText(context, "회원 탈퇴 완료", Toast.LENGTH_SHORT).show();
                                            getActivity().finish();

                                        } else { // 회원 탈퇴 실패
                                            Toast.makeText(context, "회원 탈퇴 실패", Toast.LENGTH_SHORT).show();                                                    }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "회원 탈퇴 실패, 통신이상", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            WithdrawalRequest withdrawalRequest = new WithdrawalRequest(id, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
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
        });

        return viewGroup;
    }
}