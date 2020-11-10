package com.check.corona_prototype.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 비밀번호 찾기 성공

                                pwd = jsonObject.getString("pwd");
//                                Toast.makeText(context, "비밀번호 : " + pwd,  Toast.LENGTH_SHORT).show();

                                // 다이얼로그 띄우기
                                final LinearLayout linearLayout = (LinearLayout) View.inflate(context, R.layout.diaglog_pwd, null);
                                new AlertDialog.Builder(context)
                                        .setView(linearLayout)
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .show();


                            } else { // 비밀번호 찾기 실패
                                Toast.makeText(context, "비밀번호, 통신이상", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "비밀번호, 통신이상", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                PwdChangeRequest pwdChangeRequest = new PwdChangeRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(pwdChangeRequest);
            }
        });

        // 회원 탈퇴
        ll_idleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "회원탈퇴", Toast.LENGTH_SHORT).show();
            }
        });

        return viewGroup;
    }
}