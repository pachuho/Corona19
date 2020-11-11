package com.check.corona_prototype.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.check.corona_prototype.FaceDetection.FaceActivity;
import com.check.corona_prototype.FinishActivity;
import com.check.corona_prototype.ScanQR;
import com.check.corona_prototype.R;

import static android.app.Activity.RESULT_OK;


public class Authentication extends Fragment{
    ViewGroup viewGroup;
    TextView name;
    String get_name, id, store;
    Button btn_picture, btn_qrcode;
    int check_camera = 0, check_qr = 0;
    ImageView imageView1 = null, imageView2 = null;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_authentication, container, false);
        name = viewGroup.findViewById(R.id.get_name);
        context = container.getContext();

        Bundle bundle = getArguments();
        get_name = bundle.getString("name");
        id = bundle.getString("id");
        name.setText(get_name + "님, 안녕하세요.");

        btn_picture = viewGroup.findViewById(R.id.btn_picture);
        btn_qrcode = viewGroup.findViewById(R.id.btn_qrcode);
        imageView1 = viewGroup.findViewById(R.id.iv_check1);
        imageView2 = viewGroup.findViewById(R.id.iv_check2);

        // 사진버튼
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FaceActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
            }
        });

        // QR코드 버튼
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanQR.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 2);
            }
        });
        return viewGroup;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1){
            if (resultCode == RESULT_OK){
                btn_picture.setEnabled(false);
                check_camera = 1;
                imageView1.setImageResource(R.drawable.check_ok);
            }
        } else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                btn_qrcode.setEnabled(false);
                check_qr = 1;
                imageView2.setImageResource(R.drawable.check_ok);


////                해결 포인트 -> store 가져올 것
//                Intent intent = getIntent();
//                Bundle bundle = intent.getExtras();
//                store = bundle.getString("store");
//                Toast.makeText(getApplicationContext(), "Store : " + store, Toast.LENGTH_SHORT).show();
            }
        }
        if (check_camera == 1 && check_qr == 1){
            Toast.makeText(context, "인증 완료", Toast.LENGTH_SHORT).show();
            Handler delayHandler = new Handler();
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {}
            }, 1500);
            Intent intent = new Intent(getActivity(), FinishActivity.class);
            intent.putExtra("store", store);
            startActivity(intent);
            onDestroy();
        }
    }
}