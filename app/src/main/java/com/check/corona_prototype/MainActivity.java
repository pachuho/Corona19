package com.check.corona_prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.check.corona_prototype.FaceDetection.FaceActivity;
import com.check.corona_prototype.QR.ScanQR;

import static com.check.corona_prototype.R.drawable.check_ok;

// 로그인 완료, 메인
public class MainActivity extends AppCompatActivity {
    TextView get_id;
    Button btn_picture, btn_qrcode;
    String id;
    ImageView imageView1 = null, imageView2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get_id = findViewById(R.id.get_id);
        btn_picture = findViewById(R.id.btn_picture);
        btn_qrcode = findViewById(R.id.btn_qrcode);
        imageView1 = findViewById(R.id.iv_check1);
        imageView2 = findViewById(R.id.iv_check2);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        id = bundle.getString("id");

        get_id.setText(name + "님 안녕하세요.");

        // 사진버튼
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FaceActivity.class);
                startActivity(intent);
            }
        });

        // QR코드 버튼
        btn_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanQR.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 2);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                imageView1.setImageResource(check_ok);
            }
        } else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                imageView2.setImageResource(check_ok);
            }
        }
    }

}